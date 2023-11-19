package prv.pgergely.ctsdata.repo;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import prv.pgergely.cts.common.domain.Coordinate;
import prv.pgergely.ctsdata.interfaces.CoordinateRepo;

@Repository
public class CoordinateRepoImpl extends JdbcDaoSupport implements CoordinateRepo {
	
	@Autowired
	private DataSource ds;
	
	@PostConstruct
	public void init() {
		super.setDataSource(ds);
	}
	
	@Override
	public Coordinate getCoordinate(long id) {
		final String sql = "SELECT stop_lat, stop_lon FROM stops WHERE id=?;";
		RowMapper<Coordinate> mapper = (rs, rows) -> new Coordinate(rs.getDouble("stop_lat"), rs.getDouble("stop_lon"));
		
		return this.getJdbcTemplate().queryForObject(sql, mapper, new Object[]{id});
	}

	@Override
	public List<Coordinate> getRadiusCoordinates(double lat, double lon, int radius) {
		final String sql = "SELECT * FROM stops_within_radius(?, ?, ?)";
		RowMapper<Coordinate> mapper = (rs, rows) -> new Coordinate(rs.getDouble("stop_lat"), rs.getDouble("stop_lon"));
		
		return this.getJdbcTemplate().query(sql, mapper, new Object[] {lat, lon, radius});
	}

}
