package sk.posam.rabbitMQ.consumer;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sk.posam.rabbitMQ.AppkaApplication;
import sk.posam.rabbitMQ.repository.rabbitMQ.IRecordSaverRepo;

@Component
public class QueueFSService implements IRabbitService {

    @Autowired
    @Qualifier("fsSaverRepo")
    private IRecordSaverRepo IRecordSaverRepo;

    /**
     * Create encoded base64 string from message and
     * decode it to HTML string
     * @param message
     */
    @Override
    @RabbitListener( queues = AppkaApplication.QUEUE_FS)
    public void saveRecord(Message message) {
        //Get encoded string from message
        String record = new String(message.getBody());
        //Decode it to readable HTML
        byte[] decodedRecord = Base64.decodeBase64(record);
        //Parse birthnumber
        String identifier = parseBnumber(message.getMessageProperties().getHeader("identifier"));
        //Save it
        IRecordSaverRepo.save(new String(decodedRecord), identifier);
    }

    private String parseBnumber(String birthnumber) {
        return birthnumber.replace("/","");
    }
}
