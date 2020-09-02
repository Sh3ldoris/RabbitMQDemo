package sk.posam.rabbitMQ.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import sk.posam.rabbitMQ.model.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@Repository("postGresPersonRepository")
public class PostGresPersonRepository implements IPersonRepository{

    private static final Logger LOGGER = LogManager.getLogger(PostGresPersonRepository.class);

    private RowMapper<Person> personMapper = new PersonMapper();
    //private NamedParameterJdbcTemplate template;
    private JdbcTemplate jdbcTemplate;
    private Connection connection = null;
    private Statement statement = null;

    public PostGresPersonRepository(DataSource dataSource) {
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
        } catch (Exception e) {
            LOGGER.error("Cannot create connection", e);
        }
    }

    @Override
    public Person findPerson(String birthNumber) {
        Person person = new Person();
        LOGGER.info("Start looking in database");
        try {
            String sql = String.format("SELECT * FROM person WHERE birthnumber='%s'", birthNumber);
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                //Nastavi person s danymi parametrami
                person.setBirthnumber(resultSet.getString("birthnumber"));
                person.setFirstname(resultSet.getString("firstname"));
                person.setLastname(resultSet.getString("lastname"));
                person.setAddress(resultSet.getString("address"));
                person.setCity(resultSet.getString("city"));
            }
        } catch (Exception e) {
            LOGGER.error("There is an exception", e);
        }
        return person;
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
