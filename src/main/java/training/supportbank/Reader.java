package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Reader {
    Logger LOGGER = LogManager.getLogger(Reader.class);

    void readFile(String fileName);
}
