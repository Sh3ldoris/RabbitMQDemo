package sk.posam.rabbitMQ.repository.rabbitMQ;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import sk.posam.rabbitMQ.Service.ClientServiceImp;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@Repository( value = "fsSaverRepo")
public class FSSaverRecordRepo implements RecordSaverRepo {

    private static final Logger LOGGER = LogManager.getLogger(FSSaverRecordRepo.class);

    /**
     * Save given record as local HTML file
     * @param record
     */
    @Override
    public void save(String record) {
        LOGGER.info("Trying to save as local HTML file.");
        try {
            PrintWriter out = new PrintWriter("filename.html");
            out.print(record);
            out.close();
            LOGGER.info("Local HTML file saved successfully");
        } catch (FileNotFoundException e) {
            LOGGER.error("Error in saving as local HTML file");
            e.printStackTrace();
        }
    }
}
