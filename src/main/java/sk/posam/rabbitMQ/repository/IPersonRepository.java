package sk.posam.rabbitMQ.repository;

import sk.posam.rabbitMQ.model.Person;

public interface IPersonRepository {

    Person findPerson(String birthNumber);


}
