package prv.pgergely.ctsdata.repo;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import prv.pgergely.ctsdata.interfaces.GtfsTableDao;

@Repository
public class GtfsTableDaoImpl extends JdbcDaoSupport implements GtfsTableDao {
	
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
	public void refreshMateralizedView() {
		this.getJdbcTemplate().execute("REFRESH MATERIALIZED VIEW static_stops;");
		this.getJdbcTemplate().execute("REFRESH MATERIALIZED VIEW static_stops_with_times;");
	}

	@Override
	public void clearTables() {
		this.getJdbcTemplate().execute("SELECT clear_tables();");
	}

}