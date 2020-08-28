package sk.posam.rabbitMQ.consumer;

import org.springframework.amqp.core.Message;

public interface IRabbitService {

    void saveRecord(Message message);
}
