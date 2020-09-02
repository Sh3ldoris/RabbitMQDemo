package sk.posam.rabbitMQ.repository.rabbitMQ;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sk.posam.rabbitMQ.model.Person;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("dbSaverRepo")
public class DBSaverRecordRepo implements IRecordSaverRepo {

    private static final Logger LOGGER = LogManager.getLogger(DBSaverRecordRepo.class);
    private JdbcTemplate jdbcTemplate;
    private RowMapper<String> recordMapper = new RecordMapper();

    public DBSaverRecordRepo(DataSource dataSource) {
        super();
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Save given record to PostgreSQL database
     * @param record
     */
    @Override
    public void save(String record, String identifier) {
        if (findRecord(identifier) == null) {
            insertRecord(record, identifier);
        } else {
            updateRecord(record, identifier);
        }
    }

    private void insertRecord(String record, String identifier) {
        String sqlInsert = "INSERT INTO html VALUES (?, ?)";
        jdbcTemplate.update(sqlInsert,  identifier, record);
    }

    private void updateRecord(String record, String identifier) {
        String sqlUpdate = "UPDATE html SET encodedHtml=? WHERE birthnumber=?";
        jdbcTemplate.update(sqlUpdate, record,  identifier);
    }

    private String findRecord(String identifier) {
        String sqlSave = "SELECT * FROM html WHERE birthnumber=?";
        List<String> records = jdbcTemplate.query(sqlSave, recordMapper, identifier);
        if (records.size() > 0)
            return records.get(0);
        return null;
    }

    private String mapRecord(ResultSet rs) throws SQLException {
        return rs.getString("encodedhtml");
    }

    private class RecordMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return mapRecord(rs);
        }
    }

}
