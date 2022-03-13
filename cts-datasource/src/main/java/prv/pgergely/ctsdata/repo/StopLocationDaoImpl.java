package prv.pgergely.ctsdata.repo;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import prv.pgergely.cts.common.domain.Coordinate;
import prv.pgergely.cts.common.domain.SearchLocation;
import prv.pgergely.ctsdata.interfaces.StopLocationDao;
import prv.pgergely.ctsdata.model.StopLocation;

@Repository
public class StopLocationDaoImpl extends JdbcDaoSupport implements StopLocationDao {
	
	@Autowired
	private DataSource ds;
	
	private DecimalFormat doubleFormat = new DecimalFormat(".#");
	
	@PostConstruct
	public void init() {
		super.setDataSource(ds);
	}
	
	@Override
	public List<StopLocation> getAllStopWithinRadius(SearchLocation location) {
		final String sql = "SELECT * FROM stops_within_radius(?, ?, ?) "
							+ "GROUP BY stop_name, stop_lat, stop_lon, route_name, stop_distance, stop_color, text_color "
							+ "ORDER BY route_name ";
		
		RowMapper<StopLocation> mapLocation = (rs, rows) -> {
			StopLocation stopLocation = new StopLocation();
			stopLocation.setStopName(rs.getString("stop_name"));
			stopLocation.setRouteName(rs.getString("route_name"));
			stopLocation.getStopCoordinate().setLatitude(rs.getDouble("stop_lat"));
			stopLocation.getStopCoordinate().setLongitude(rs.getDouble("stop_lon"));
			double dist = Double.valueOf(doubleFormat.format(rs.getDouble("stop_distance")).replace(",", "."));
			stopLocation.setStopDistance(dist);
			stopLocation.setStopColor(rs.getString("stop_color"));
			stopLocation.setStopTextColor(rs.getString("text_color"));

			return stopLocation;
		};
		Coordinate coordinate = location.getCoordinates();
		
		return this.getJdbcTemplate().query(sql, mapLocation, new Object[] {coordinate.getLatitude(), coordinate.getLongitude(), location.getRadius()});
	}

	@Override
	public List<StopLocation> getAllStopWithinRadiusWithTime(SearchLocation location) {
		final String sql = "SELECT route_name, stop_names, stop_latitude, stop_longitude, "
							+ "stop_color, text_color, stop_distance, depart_time "
							+ "FROM stop_and_times_within_range(?, ?, ?) "
							+ "GROUP BY route_name, stop_names, stop_latitude, stop_longitude, stop_color, text_color, stop_distance, depart_time "
							+ "ORDER BY route_name ";
		
		RowMapper<StopLocation> mapLocation = (rs, rows) -> {
			StopLocation stopLocation = new StopLocation();
			stopLocation.setStopName(rs.getString("stop_names"));
			stopLocation.setRouteName(rs.getString("route_name"));
			stopLocation.getStopCoordinate().setLatitude(rs.getDouble("stop_latitude"));
			stopLocation.getStopCoordinate().setLongitude(rs.getDouble("stop_longitude"));
			double dist = Double.valueOf(doubleFormat.format(rs.getDouble("stop_distance")).replace(",", "."));
			stopLocation.setStopDistance(dist);
			stopLocation.setStopColor(rs.getString("stop_color"));
			stopLocation.setStopTextColor(rs.getString("text_color"));
			stopLocation.setDepartureTime(getRefinedArray(rs.getString("depart_time")));
			
			return stopLocation; 
		};
		Coordinate coordinate = location.getCoordinates();
		
		return this.getJdbcTemplate().query(sql, mapLocation, new Object[] {coordinate.getLatitude(), coordinate.getLongitude(), location.getRadius()});
	}

	private List<LocalTime> getRefinedArray(String rawArr){
		String[] elements = rawArr.replace("{", "").replace("}", "").split(",");
		List<LocalTime> times = new ArrayList<>();
		for(String element : elements) {
			times.add(LocalTime.parse(element));
		}
		
		return times;
	}
	
}
