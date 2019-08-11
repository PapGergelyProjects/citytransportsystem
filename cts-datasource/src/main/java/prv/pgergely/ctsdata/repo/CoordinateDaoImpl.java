package prv.pgergely.ctsdata.repo;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import prv.pgergely.ctsdata.interfaces.CoordinateDao;
import prv.pgergely.ctsdata.model.Coordinate;

@Repository
public class CoordinateDaoImpl extends JdbcDaoSupport implements CoordinateDao {
	
	@Autowired
	private DataSource ds;
	
	@PostConstruct
	public void init() {
		super.setDataSource(ds);
	}
	
	@Override
	public Coordinate getCoordinate(long id) {
		final String sql = "SELECT stop_lat, stop_lon FROM stops WHERE id=?;";
		RowMapper<Coordinate> mapper = (rs, rows) -> {
			Coordinate coor = new Coordinate();
			coor.setLatitude(rs.getDouble("stop_lat"));
			coor.setLongitude(rs.getDouble("stop_lon"));
			return coor;
		};
		
		return this.getJdbcTemplate().queryForObject(sql, new Object[]{id}, mapper);
	}

	@Override
	public List<Coordinate> getRadiusCoordinates(double lat, double lon, int radius) {
		final String sql = "SELECT * FROM stops_within_radius(?, ?, ?)";
		RowMapper<Coordinate> mapper = (rs, rows) -> {
			Coordinate coor = new Coordinate();
			coor.setLatitude(rs.getDouble("stop_lat"));
			coor.setLongitude(rs.getDouble("stop_lon"));
			return coor;
		};
		
		return this.getJdbcTemplate().query(sql, new Object[] {lat, lon, radius}, mapper);
	}

}
