package main.java.models.objects;

import java.util.logging.Logger;

public class FileHandler {
    static Logger logger = Logger.getLogger(FileHandler.class.getName());

    public boolean saveState(String loc) {
        logger.info("->FileHandler.saveState(String loc)");
        logger.info("<-FileHandler.saveState(String loc): true");
        return true;
    }

    public boolean loadState(String loc) {
        logger.info("->FileHandler.loadState(String loc)");
        logger.info("<-FileHandler.loadState(String loc): true");
        return true;
    }
}
