package sk.posam.rabbitMQ.repository;

import org.springframework.stereotype.Repository;

@Repository( value = "fsSaverRepo")
public class FSSaverRecordRepo implements RecordSaverRepo {
    @Override
    public void save() {

    }
}
