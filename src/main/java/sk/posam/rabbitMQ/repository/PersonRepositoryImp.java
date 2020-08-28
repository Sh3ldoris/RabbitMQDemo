package sk.posam.rabbitMQ.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import sk.posam.rabbitMQ.model.Person;

@Repository
public class PersonRepositoryImp implements IPersonRepository{

    private static final Logger LOGGER = LogManager.getLogger(PersonRepositoryImp.class);

    @Override
    public Person findPerson(String birthNumber) {
        LOGGER.info("Start looking in database");
        //TODO:crete
        return null;
    }
}
