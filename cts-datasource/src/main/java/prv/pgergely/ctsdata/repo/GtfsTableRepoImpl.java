package prv.pgergely.ctsdata.repo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import prv.pgergely.ctsdata.interfaces.GtfsTableRepo;

@Repository
public class GtfsTableRepoImpl extends JdbcDaoSupport implements GtfsTableRepo {
	
	@Autowired
	private DataSource source;
	
	@PostConstruct
	public void init() {
		super.setDataSource(source);
	}
	
	@Override
	public void insert(String insertValues) {
		int[] array = this.getJdbcTemplate().batchUpdate(insertValues);
		System.out.println(Arrays.toString(array));//DELETE
	}
	
	@Override
	public void copy(String copyQuery, InputStream copyValue) throws CannotGetJdbcConnectionException, SQLException, IOException {
		CopyManager copy = this.getConnection().unwrap(PGConnection.class).getCopyAPI();
		copy.copyIn(copyQuery, copyValue);
	}

	@Override
	public void refreshMateralizedView() {
		this.getJdbcTemplate().execute("REFRESH MATERIALIZED VIEW static_stops;");
		this.getJdbcTemplate().execute("REFRESH MATERIALIZED VIEW static_stops_with_times;");
	}

	@Override
	public void clearTables() {
		this.getJdbcTemplate().execute("SELECT clear_tables();");
	}

	@Override
	public void vacuumTable(String tableName) throws CannotGetJdbcConnectionException, SQLException {
		this.getJdbcTemplate().execute("VACUUM VERBOSE ANALYZE "+tableName);
	}

}
