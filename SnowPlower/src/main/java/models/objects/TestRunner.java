package main.java.models.objects;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Automatikusan futtatja a tests/ könyvtár összes tesztjét.
 * Minden tesztes alkönyvtárhoz (pl. tests/tc01/) egy input.txt (bemeneti parancsok)
 * és egy expected.txt (elvárt állapot részhalmaza) fájl tartozik.
 * Eredmény: [TCXX] OK | FAIL | MISSING
 */
public class TestRunner {

    private static final String TEST_DIR = "tests";

    // -----------------------------------------------------------------------
    // Publikus API
    // -----------------------------------------------------------------------

    /**
     * Megkeresi a tests/ alatt az összes alkönyvtárat, és sorban lefuttatja azokat.
     */
    public void runAll() {
        File testsDir = new File(TEST_DIR);
        if (!testsDir.exists() || !testsDir.isDirectory()) {
            Console.print("[TestRunner] Teszt könyvtár nem található: " + TEST_DIR);
            return;
        }

        File[] testDirs = testsDir.listFiles(File::isDirectory);
        if (testDirs == null || testDirs.length == 0) {
            Console.print("[TestRunner] Nincsenek tesztek a " + TEST_DIR + " könyvtárban.");
            return;
        }

        Arrays.sort(testDirs, Comparator.comparing(File::getName));

        int ok = 0, fail = 0, missing = 0;

        for (File tcDir : testDirs) {
            String label = "[" + tcDir.getName().toUpperCase() + "]";
            File inputFile   = new File(tcDir, "input.txt");
            File expectedFile = new File(tcDir, "expected.txt");

            if (!inputFile.exists() || !expectedFile.exists()) {
                Console.print(label + " MISSING");
                missing++;
                continue;
            }

            try {
                String inputContent = Files.readString(inputFile.toPath());
                String expected     = Files.readString(expectedFile.toPath());

                // Elindítja az alkönyvtár nevével meghatározott tesztet
                String actual = executeTest(inputContent);

                if (subsetAssert(actual, expected)) {
                    Console.print(label + " OK");
                    ok++;
                } else {
                    Console.print(label + " FAIL");
                    fail++;
                }
            } catch (Exception e) {
                Console.print(label + " FAIL (" + e.getMessage() + ")");
                fail++;
            }
        }

        Console.print("\nEredmény: " + ok + " OK, " + fail + " FAIL, " + missing + " MISSING");
    }

    // -----------------------------------------------------------------------
    // Teszt végrehajtás
    // -----------------------------------------------------------------------

    /**
     * Végrehajt egy tesztet:
     *  1. Átirányítja a Console stdin-jét az inputContent tartalmára.
     *  2. Létrehoz egy friss Console-t és meghívja loop()-ot (EOF-ig fut).
     *  3. Visszaadja a keletkező játékállapotot mint szöveget.
     */
    private String executeTest(String inputContent) {
        Console.setReader(new ByteArrayInputStream(inputContent.getBytes()));
        Console.setQuiet(true);

        Console testConsole = new Console();
        try {
            testConsole.reset();
            testConsole.loop();
            return testConsole.captureState();
        } finally {
            Console.setQuiet(false);
            Console.setReader(System.in);   // eredeti stdin visszaállítása
        }
    }

    // -----------------------------------------------------------------------
    // Összehasonlítás: az expected összes sora megjelenik-e az actual-ban?
    // -----------------------------------------------------------------------

    /**
     * Tartalom-alapú részhalmaz ellenőrzés:
     * OK, ha az expected fájl minden sora megtalálható az actual-ban
     * (sorrend mindegy, sor-szintű).
     */
    private boolean subsetAssert(String actual, String expected) {
        Set<String> actualLines   = toLineSet(actual);
        Set<String> expectedLines = toLineSet(expected);
        return actualLines.containsAll(expectedLines);
    }

    private Set<String> toLineSet(String content) {
        if (content == null || content.isEmpty()) return new HashSet<>();
        return Arrays.stream(content.split("\\r?\\n"))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.toSet());
    }

    // -----------------------------------------------------------------------
    // Block-alapú összehasonlítás (megtartva a korábbi fileAssert logikából)
    // -----------------------------------------------------------------------

    /**
     * Blokk-szintű részhalmaz összehasonlítás.
     * Ugyanazt ellenőrzi mint subsetAssert, de blokkonként normalizálva.
     * Jelenleg csak a régi teszteknél (tests/0/) használt kompatibilitásból.
     */
    public boolean fileAssert(String actual, String expected) {
        Set<String> actualBlocks   = parseAndNormalize(actual);
        Set<String> expectedBlocks = parseAndNormalize(expected);
        return actualBlocks.containsAll(expectedBlocks);
    }

    private Set<String> parseAndNormalize(String input) {
        if (input == null || input.isEmpty()) return new HashSet<>();

        Set<String> blocks = new HashSet<>();
        String[] lines = input.split("\\r?\\n");
        StringBuilder currentBlock = new StringBuilder();
        String currentType = "";

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.length() == 1 && Character.isUpperCase(line.charAt(0))) {
                if (!currentBlock.isEmpty()) {
                    blocks.add(finalizeBlock(currentType, currentBlock.toString()));
                }
                currentType = line;
                currentBlock = new StringBuilder();
            } else {
                currentBlock.append(line).append("\n");
            }
        }
        if (!currentBlock.isEmpty()) {
            blocks.add(finalizeBlock(currentType, currentBlock.toString()));
        }
        return blocks;
    }

    private String finalizeBlock(String type, String body) {
        List<String> lines = Arrays.stream(body.split("\n"))
                                   .map(String::trim)
                                   .filter(s -> !s.isEmpty())
                                   .map(this::normalizeContent)
                                   .sorted()
                                   .toList();
        return type + "\n" + String.join("\n", lines);
    }

    private String normalizeContent(String line) {
        if (!line.contains("=")) return line;
        String[] parts = line.split("=", 2);
        String key   = parts[0].trim();
        String value = parts[1].trim();

        if (key.equalsIgnoreCase("route")) return key + "=" + value;

        if (value.contains(";")) {
            boolean endsWithSemi = value.endsWith(";");
            String sorted = Arrays.stream(value.split(";"))
                                  .map(String::trim)
                                  .filter(s -> !s.isEmpty())
                                  .sorted()
                                  .collect(Collectors.joining(";"));
            if (endsWithSemi && !sorted.isEmpty()) sorted += ";";
            return key + "=" + sorted;
        }
        return key + "=" + value;
    }
}
