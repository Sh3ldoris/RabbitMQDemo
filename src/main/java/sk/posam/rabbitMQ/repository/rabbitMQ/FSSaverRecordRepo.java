package sk.posam.rabbitMQ.repository.rabbitMQ;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Repository( value = "fsSaverRepo")
public class FSSaverRecordRepo implements IRecordSaverRepo {

    private static final Logger LOGGER = LogManager.getLogger(FSSaverRecordRepo.class);

    /**
     * Save given record as local HTML file
     * @param record
     */
    @Override
    public void save(String record, String identifier) {
        LOGGER.info("Trying to save as local HTML file.");
        try {
            String filename = String.format("obcan_%s.html", identifier);
            PrintWriter out = new PrintWriter(filename);
            out.print(record);
            out.close();
            LOGGER.info("Local HTML file saved successfully");
        } catch (FileNotFoundException e) {
            LOGGER.error("Error in saving as local HTML file");
            e.printStackTrace();
        }
    }
}
