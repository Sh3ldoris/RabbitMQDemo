package sk.posam.rabbitMQ.repository.rabbitMQ;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("dbSaverRepo")
public class DBSaverRecordRepo implements IRecordSaverRepo {

    private static final Logger LOGGER = LogManager.getLogger(DBSaverRecordRepo.class);
    private JdbcTemplate jdbcTemplate;

    public DBSaverRecordRepo(DataSource dataSource) {
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Save given record to PostgreSQL database
     * @param record
     */
    @Override
    public void save(String record) {
        String sqlInsert = "INSERT INTO html (encodedHtml) VALUES (?)";
        jdbcTemplate.update(sqlInsert, record);
    }
}
