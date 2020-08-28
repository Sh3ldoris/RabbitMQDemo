package sk.posam.rabbitMQ.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import sk.posam.rabbitMQ.AppkaApplication;

@Component
public class QueueFSService implements IRabbitService {

    @Override
    @RabbitListener( queues = AppkaApplication.QUEUE_FS)
    public void saveRecord(Message message) {
        System.out.println("FS message is " );
        //TODO:Save record to local file system as XML/HTML file
    }
}
