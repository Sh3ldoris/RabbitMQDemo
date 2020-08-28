package sk.posam.rabbitMQ.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import sk.posam.rabbitMQ.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository("postGresPersonRepository")
public class PostGresPersonRepository implements IPersonRepository{

    private static final Logger LOGGER = LogManager.getLogger(PostGresPersonRepository.class);

    private RowMapper<Person> personMapper = new PersonMapper();
    private NamedParameterJdbcTemplate template;

    public PostGresPersonRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Person findPerson(String birthNumber) {
        LOGGER.info("Start looking in database");
        List<Person> o = template.query("SELECT * FROM person", personMapper);
        //TODO:crete
        return null;
    }

    private Person mapPerson(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setBirthnumber(rs.getString("birthnumber"));
        person.setFirstname(rs.getString("firstname"));
        person.setLastname(rs.getString("lastname"));
        person.setAddress(rs.getString("address"));
        person.setCity(rs.getString("city"));
        person.setAge(rs.getInt("age"));

        return person;

    }

    private class PersonMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            return mapPerson(rs);
        }
    }

}
