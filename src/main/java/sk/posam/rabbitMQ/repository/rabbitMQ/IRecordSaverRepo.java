package sk.posam.rabbitMQ.repository.rabbitMQ;

public interface IRecordSaverRepo {

    void save(String record, String identifier);
}
