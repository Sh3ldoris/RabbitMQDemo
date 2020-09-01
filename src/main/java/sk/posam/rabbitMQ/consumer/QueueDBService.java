package sk.posam.rabbitMQ.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sk.posam.rabbitMQ.AppkaApplication;
import sk.posam.rabbitMQ.repository.rabbitMQ.RecordSaverRepo;

@Component
public class QueueDBService implements IRabbitService {

    @Autowired
    @Qualifier("dbSaverRepo")
    private RecordSaverRepo recordSaverRepo;

    @Override
    @RabbitListener( queues = AppkaApplication.QUEUE_DB)
    public void saveRecord(Message message) {
        System.out.println("DB message is " + new String(message.getBody()));
        //TODO:Create string from message and save it(base 64)
    }
}
