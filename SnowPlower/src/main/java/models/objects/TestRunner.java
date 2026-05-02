package main.java.models.objects;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestRunner {
    //Constants
    private static final String TEST_DIR = "tests";    
    //TODO expand list with added tests
    public static final String TEST_MENU = """
            (0) - load from save.txt 

            (x) - exit
            """;

    public boolean runTests(int id){
        boolean res = false;
        switch (id) {
            case 0 -> res = test0(id);
            default -> {break;}
        }
        Console.print("TEST RESULT: (" + id + ")" + res);
        return res;
    }
    private boolean fileAssert(String current, String expected) {
        Set<String> currentBlocks = parseAndNormalize(current);
        Set<String> expectedBlocks = parseAndNormalize(expected);

        return currentBlocks.equals(expectedBlocks);
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
        String key = parts[0].trim();
        String value = parts[1].trim();
        
        if (key.equalsIgnoreCase("route")) {
            return key + "=" + value;
        }
        
        if (value.contains(";")) {
            boolean endsWithSemicolon = value.endsWith(";");
            
            String sortedList = Arrays.stream(value.split(";"))
                                      .map(String::trim)
                                      .filter(s -> !s.isEmpty())
                                      .sorted()
                                      .collect(Collectors.joining(";"));
            
            if (endsWithSemicolon && !sortedList.isEmpty()) {
                sortedList += ";";
            }
            return key + "=" + sortedList;
        }

        return key + "=" + value;
    }

    //-------------------TESTS------------------------//
    public boolean test0(int id){
        try {
            String contentIn = Files.readString(FileSystems.getDefault().getPath(TEST_DIR, Integer.toString(id), "save.txt"));
            String expected = Files.readString(FileSystems.getDefault().getPath(TEST_DIR, Integer.toString(id), "expected.txt"));
            return fileAssert(contentIn, expected);
        } catch (Exception e) {
            Console.print(e.getMessage());
            return false;
        }
    }
}
