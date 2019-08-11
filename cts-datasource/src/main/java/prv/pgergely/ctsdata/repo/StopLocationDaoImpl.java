package prv.pgergely.ctsdata.repo;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

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
	public List<StopLocation> getAllStopWithinRadius(double centerLat, double centerLon, double radius) {
		final String sql = "SELECT * FROM stops_within_radius(?, ?, ?) "
							+ "GROUP BY stop_name, stop_lat, stop_lon, route_name, stop_distance, stop_color, text_color "
							+ "ORDER BY route_name ";
		
		RowMapper<StopLocation> mapLocation = (rs, rows) -> {
			StopLocation location = new StopLocation();
			location.setStopName(rs.getString("stop_name"));
			location.setRouteName(rs.getString("route_name"));
			location.getStopCoordinate().setLatitude(rs.getDouble("stop_lat"));
			location.getStopCoordinate().setLongitude(rs.getDouble("stop_lon"));
			double dist = Double.valueOf(doubleFormat.format(rs.getDouble("stop_distance")).replace(",", "."));
			location.setStopDistance(dist);
			location.setStopColor(rs.getString("stop_color"));
			location.setStopTextColor(rs.getString("text_color"));

			return location;
		};
		
		return this.getJdbcTemplate().query(sql, new Object[] {centerLat, centerLon, radius}, mapLocation);
	}

	@Override
	public List<StopLocation> getAllStopWithinRadiusWithTime(double centerLat, double centerLon, double radius) {
		final String sql = "SELECT route_name, stop_names, stop_latitude, stop_longitude, "
				+ "stop_color, text_color, stop_distance, depart_time "
				+ "FROM stop_and_times_within_range(?, ?, ?) "
				+ "GROUP BY route_name, stop_names, stop_latitude, stop_longitude, stop_color, text_color, stop_distance, depart_time "
				+ "ORDER BY route_name ";
		
		RowMapper<StopLocation> mapLocation = (rs, rows) -> {
			StopLocation location = new StopLocation();
			location.setStopName(rs.getString("stop_names"));
			location.setRouteName(rs.getString("route_name"));
			location.getStopCoordinate().setLatitude(rs.getDouble("stop_latitude"));
			location.getStopCoordinate().setLongitude(rs.getDouble("stop_longitude"));
			double dist = Double.valueOf(doubleFormat.format(rs.getDouble("stop_distance")).replace(",", "."));
			location.setStopDistance(dist);
			location.setStopColor(rs.getString("stop_color"));
			location.setStopTextColor(rs.getString("text_color"));
			location.setDepartureTime(getRefinedArray(rs.getString("depart_time")));
			
			return location; 
		};
		
		return this.getJdbcTemplate().query(sql, new Object[] {centerLat, centerLon, radius}, mapLocation);
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
