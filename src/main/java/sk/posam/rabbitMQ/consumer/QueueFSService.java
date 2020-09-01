package sk.posam.rabbitMQ.consumer;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sk.posam.rabbitMQ.AppkaApplication;
import sk.posam.rabbitMQ.repository.rabbitMQ.RecordSaverRepo;

@Component
public class QueueFSService implements IRabbitService {

    @Autowired
    @Qualifier("fsSaverRepo")
    private RecordSaverRepo recordSaverRepo;

    /**
     * Create encoded base64 string from message and
     * decode it to HTML string
     * @param message
     */
    @Override
    @RabbitListener( queues = AppkaApplication.QUEUE_FS)
    public void saveRecord(Message message) {
        //Create string from message and decode it BASE64
        String record = new String(message.getBody());
        byte[] decodedRecord = Base64.decodeBase64(record);
        recordSaverRepo.save(new String(decodedRecord));
    }
}
