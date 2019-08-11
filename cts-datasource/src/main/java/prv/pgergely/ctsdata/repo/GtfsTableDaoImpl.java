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
		PGSimpleDataSource dataSource = (PGSimpleDataSource)source;
		super.setDataSource(dataSource);
	}
	
	@Override
	public void insert(String insertValues) {
		int[] array = this.getJdbcTemplate().batchUpdate(insertValues);
		System.out.println(Arrays.toString(array));//DELETE
	}

}
