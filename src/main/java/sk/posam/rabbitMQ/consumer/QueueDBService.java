package sk.posam.rabbitMQ.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sk.posam.rabbitMQ.AppkaApplication;
import sk.posam.rabbitMQ.repository.rabbitMQ.IRecordSaverRepo;

@Component
public class QueueDBService implements IRabbitService {

    @Autowired
    @Qualifier("dbSaverRepo")
    private IRecordSaverRepo IRecordSaverRepo;

    /**
     * Create encoded base64 string from message
     * @param message
     */
    @Override
    @RabbitListener( queues = AppkaApplication.QUEUE_DB)
    public void saveRecord(Message message) {
        String record = new String(message.getBody());
        String identifier = message.getMessageProperties().getHeader("identifier");
        IRecordSaverRepo.save(record, identifier);
    }
}
