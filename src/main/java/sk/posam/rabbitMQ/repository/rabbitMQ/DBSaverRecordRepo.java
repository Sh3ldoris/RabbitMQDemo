package sk.posam.rabbitMQ.repository.rabbitMQ;

import org.springframework.stereotype.Repository;

@Repository( value = "dbSaverRepo")
public class DBSaverRecordRepo implements RecordSaverRepo {

    @Override
    public void save(String record) {

    }
}
