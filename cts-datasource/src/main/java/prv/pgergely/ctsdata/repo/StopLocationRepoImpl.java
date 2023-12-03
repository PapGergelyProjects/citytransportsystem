package prv.pgergely.ctsdata.repo;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import prv.pgergely.cts.common.domain.Coordinate;
import prv.pgergely.cts.common.domain.SearchLocation;
import prv.pgergely.ctsdata.interfaces.StopLocationRepo;
import prv.pgergely.ctsdata.model.StopLocation;
import prv.pgergely.ctsdata.utility.DataUtils;

@Repository
public class StopLocationRepoImpl extends NamedParameterJdbcTemplate implements StopLocationRepo {
	
	@Autowired
	public StopLocationRepoImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	private DecimalFormat doubleFormat = new DecimalFormat(".#");
	
	@Override
	public List<StopLocation> getAllStopWithinRadius(SearchLocation location) {
		final String sql = "SELECT * FROM stops_within_radius(:lat, :lon, :rad) "
							+ "GROUP BY stop_name, stop_lat, stop_lon, route_name, stop_distance, stop_color, text_color ";
							//+ "ORDER BY route_name ";
		
		RowMapper<StopLocation> mapLocation = (rs, rows) -> {
			StopLocation stopLocation = new StopLocation();
			stopLocation.setStopName(rs.getString("stop_name"));
			stopLocation.setRouteName(rs.getString("route_name"));
			stopLocation.setStopCoordinate(new Coordinate(rs.getDouble("stop_lat"), rs.getDouble("stop_lon")));
			double dist = Double.valueOf(doubleFormat.format(rs.getDouble("stop_distance")).replace(",", "."));
			stopLocation.setStopDistance(dist);
			stopLocation.setStopColor(DataUtils.refineColorCode(rs.getString("stop_color")));
			stopLocation.setStopTextColor(DataUtils.refineColorCode(rs.getString("text_color")));

			return stopLocation;
		};
		Coordinate coordinate = location.getCoordinates();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("lat", coordinate.getLatitude()); 
		params.addValue("lon", coordinate.getLongitude()); 
		params.addValue("rad", location.getRadius()); 
		
		return this.query(sql, params, mapLocation);
	}

	@Override
	public List<StopLocation> getAllStopWithinRadiusWithTime(SearchLocation location) {
		final String sql = """
				SELECT route_name, stop_names, stop_latitude, stop_longitude, 
					   stop_color, text_color, stop_distance, depart_time 
				FROM stop_and_times_within_range(:lat, :lon, :rad) 
				GROUP BY route_name, stop_names, stop_latitude, stop_longitude, stop_color, text_color, stop_distance, depart_time 
				""";
		
		RowMapper<StopLocation> mapLocation = (rs, rows) -> {
			StopLocation stopLocation = new StopLocation();
			stopLocation.setStopName(rs.getString("stop_names"));
			stopLocation.setRouteName(rs.getString("route_name"));
			stopLocation.setStopCoordinate(new Coordinate(rs.getDouble("stop_latitude"), rs.getDouble("stop_longitude")));
			double dist = Double.valueOf(doubleFormat.format(rs.getDouble("stop_distance")).replace(",", "."));
			stopLocation.setStopDistance(dist);
			stopLocation.setStopColor(DataUtils.refineColorCode(rs.getString("stop_color")));
			stopLocation.setStopTextColor(DataUtils.refineColorCode(rs.getString("text_color")));
			stopLocation.setDepartureTime(getRefinedArray(rs.getString("depart_time")));
			
			return stopLocation; 
		};
		Coordinate coordinate = location.getCoordinates();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("lat", coordinate.getLatitude()); 
		params.addValue("lon", coordinate.getLongitude()); 
		params.addValue("rad", location.getRadius()); 
		
		return this.query(sql, params, mapLocation);
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
