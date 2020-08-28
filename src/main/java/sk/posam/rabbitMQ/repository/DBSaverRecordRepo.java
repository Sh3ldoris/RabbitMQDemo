package sk.posam.rabbitMQ.repository;

import org.springframework.stereotype.Repository;

@Repository( value = "dbSaverRepo")
public class DBSaverRecordRepo implements RecordSaverRepo {
    @Override
    public void save() {

    }
}
