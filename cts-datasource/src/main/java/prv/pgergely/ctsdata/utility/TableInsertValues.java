package prv.pgergely.ctsdata.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * <p>
 * This enum contains the whole table column structure, with proper data type representation.
 * </p>
 * @author Pap Gergely
 *
 */
public enum TableInsertValues {
    AGENCY("agency"){
    	
    	private final List<String> agencyList = Arrays.asList("agency_id", "agency_name", "agency_url", "agency_timezone", "agency_lang", "agency_phone");
    	
        @Override
        public String getInsertValue(List<String> columns, Map<String, String> records) {
            StringJoiner insertValues = new StringJoiner(",", "(",")");
            Map<String, String> valueMap = new HashMap<>();
            agencyList.forEach(e -> valueMap.put(e, nullValues(records.get(e), true)));

            return getFilteredValues(columns, insertValues, valueMap);
        }

		@Override
		public List<String> getColNames(List<String> rawCols) {
			rawCols.retainAll(new ArrayList<>(agencyList));
			return rawCols;
		}
    },
    FEED_INFO("feed_info"){
    	
    	private final List<String> feedInfoList = Arrays.asList("feed_publisher_name", "feed_publisher_url", "feed_lang", "feed_start_date", "feed_end_date", "feed_version", "feed_ext_version");
    	
        @Override
        public String getInsertValue(List<String> columns, Map<String, String> records) {
            StringJoiner insertValues = new StringJoiner(",", "(",")");
            Map<String, String> valueMap = new HashMap<>();
            Arrays.asList("feed_publisher_name", "feed_publisher_url", "feed_lang").forEach(e -> valueMap.put(e, nullValues(records.get(e), true)));
            valueMap.put("feed_start_date", strToDateFormat(records.get("feed_start_date")));
            valueMap.put("feed_end_date", strToDateFormat(records.get("feed_end_date")));
            valueMap.put("feed_version", nullValues(records.get("feed_version"), true));
            valueMap.put("feed_ext_version", nullValues(records.get("feed_ext_version"), false));

            return getFilteredValues(columns, insertValues, valueMap);
        }
        
		@Override
		public List<String> getColNames(List<String> rawCols) {
			rawCols.retainAll(feedInfoList);
			return rawCols;
		}
    },
    CALENDAR_DATES("calendar_dates"){
    	
    	private final List<String> calendarDates = Arrays.asList("service_id", "date", "exception_type");
    	
        @Override
        public String getInsertValue(List<String> columns, Map<String, String> records) {
            StringJoiner insertValues = new StringJoiner(",", "(",")");
            Map<String, String> valueMap = new HashMap<>();
            valueMap.put("service_id", nullValues(records.get("service_id"), true));
            valueMap.put("date", strToDateFormat(records.get("date")));
            valueMap.put("exception_type", nullValues(records.get("exception_type"), false));

            return getFilteredValues(columns, insertValues, valueMap);
        }
        
		@Override
		public List<String> getColNames(List<String> rawCols) {
			rawCols.retainAll(calendarDates);
			return rawCols;
		}
    },
    PATHWAYS("pathways"){
    	
    	private final List<String> pathwaysList = Arrays.asList("pathway_id", "pathway_type", "from_stop_id", "to_stop_id", "traversal_time", "wheelchair_traversal_time");
    	
        @Override
        public String getInsertValue(List<String> columns, Map<String, String> records) {
            StringJoiner insertValues = new StringJoiner(",", "(",")");
            Map<String, String> valueMap = new HashMap<>();
            valueMap.put("pathway_id", nullValues(records.get("pathway_id"), true));
            valueMap.put("pathway_type", nullValues(records.get("pathway_type"), false));
            valueMap.put("from_stop_id", nullValues(records.get("from_stop_id"), true));
            valueMap.put("to_stop_id", nullValues(records.get("to_stop_id"), true));
            valueMap.put("traversal_time", nullValues(records.get("traversal_time"), false));
            valueMap.put("wheelchair_traversal_time", nullValues(records.get("wheelchair_traversal_time"), false));
            
            return getFilteredValues(columns, insertValues, valueMap);
        }
        
		@Override
		public List<String> getColNames(List<String> rawCols) {
			rawCols.retainAll(pathwaysList);
			return rawCols;
		}
    },
    ROUTES("routes"){
    	
    	private final List<String> routesList = Arrays.asList("agency_id", "route_id", "route_short_name", "route_long_name", "route_type", "route_desc", "route_url", "route_color", "route_text_color", "route_sort_order");
    	
        @Override
        public String getInsertValue(List<String> columns, Map<String, String> records) {
            StringJoiner insertValues = new StringJoiner(",", "(",")");
            Map<String, String> valueMap = new HashMap<>();
            valueMap.put("agency_id", nullValues(records.get("agency_id"), true));
            valueMap.put("route_id", nullValues(records.get("route_id"), true));
            valueMap.put("route_short_name", nullValues(records.get("route_short_name"), true));
            valueMap.put("route_long_name", nullValues(records.get("route_long_name"), true));
            valueMap.put("route_type", nullValues(records.get("route_type"), false));
            valueMap.put("route_desc", nullValues(records.get("route_desc"), true));
            valueMap.put("route_url", nullValues(records.get("route_url"), true));
            valueMap.put("route_color", nullValues(records.get("route_color"), true));
            valueMap.put("route_text_color", nullValues(records.get("route_text_color"), true));
            valueMap.put("route_sort_order", nullValues(records.get("route_sort_order"), false));
            
            return getFilteredValues(columns, insertValues, valueMap);
        }
        
		@Override
		public List<String> getColNames(List<String> rawCols) {
			rawCols.retainAll(routesList);
			return rawCols;
		}
    },
    SHAPES("shapes"){
    	
    	private final List<String> shapes = Arrays.asList("shape_id", "shape_pt_sequence", "shape_pt_lat", "shape_pt_lon", "shape_dist_traveled");
    	
        @Override
        public String getInsertValue(List<String> columns, Map<String, String> records) {
            StringJoiner insertValues = new StringJoiner(",", "(",")");
            Map<String, String> valueMap = new HashMap<>();
            valueMap.put("shape_id", "'"+records.get("shape_id")+"'");
            valueMap.put("shape_pt_sequence", records.get("shape_pt_sequence"));
            valueMap.put("shape_pt_lat", records.get("shape_pt_lat"));
            valueMap.put("shape_pt_lon", records.get("shape_pt_lon"));
            valueMap.put("shape_dist_traveled", records.get("shape_dist_traveled"));
            
            return getFilteredValues(columns, insertValues, valueMap);
        }
        
		@Override
		public List<String> getColNames(List<String> rawCols) {
			rawCols.retainAll(shapes);
			return rawCols;
		}
    },
    STOP_TIMES("stop_times"){
    	
    	private final List<String> stopTimesList = Arrays.asList("trip_id", "stop_id", "arrival_time", "departure_time", "stop_sequence", "stop_headsign", "pickup_type", "drop_off_type", "shape_dist_traveled", "timepoint");
    	
        @Override
        public String getInsertValue(List<String> columns, Map<String, String> records) {
            StringJoiner insertValues = new StringJoiner(",", "(",")");
            Map<String, String> valueMap = new HashMap<>();
            valueMap.put("trip_id", "'"+records.get("trip_id")+"'");
            valueMap.put("stop_id", "'"+records.get("stop_id")+"'");
            valueMap.put("arrival_time", "'"+records.get("arrival_time")+"'::interval");
            valueMap.put("departure_time", "'"+records.get("departure_time")+"'::interval");
            valueMap.put("stop_sequence", nullValues(records.get("stop_sequence"), false));
            valueMap.put("stop_headsign", nullValues(records.get("stop_headsign"), true));
            valueMap.put("pickup_type", nullValues(records.get("pickup_type"), false));
            valueMap.put("drop_off_type", nullValues(records.get("drop_off_type"), false));
            valueMap.put("shape_dist_traveled", nullValues(records.get("shape_dist_traveled"), false));
            valueMap.put("timepoint", nullValues(records.get("timepoint"), false));
            
            return getFilteredValues(columns, insertValues, valueMap);
        }
        
		@Override
		public List<String> getColNames(List<String> rawCols) {
			rawCols.retainAll(stopTimesList);
			return rawCols;
		}
    },
    STOPS("stops"){
    	
    	private final List<String> stopsList = Arrays.asList("stop_id", "stop_name", "stop_desc", "stop_lat", "stop_lon", "stop_url", "stop_code", "location_type", "parent_station", "wheelchair_boarding", "stop_timezone");
    	
        @Override
        public String getInsertValue(List<String> columns, Map<String, String> records) {
            StringJoiner insertValues = new StringJoiner(",", "(",")");
            Map<String, String> valueMap = new HashMap<>();
            valueMap.put("stop_id", "'"+records.get("stop_id")+"'");
            valueMap.put("stop_name", "'"+records.get("stop_name")+"'");
            valueMap.put("stop_desc", nullValues(records.get("stop_desc"), true));
            valueMap.put("stop_lat", records.get("stop_lat"));
            valueMap.put("stop_lon", records.get("stop_lon"));
            valueMap.put("stop_url", nullValues(records.get("stop_url"), true));
            valueMap.put("stop_code", nullValues(records.get("stop_code"), true));
            valueMap.put("location_type", nullValues(records.get("location_type"), false));
            valueMap.put("parent_station", nullValues(records.get("parent_station"), true));
            valueMap.put("wheelchair_boarding", nullValues(records.get("wheelchair_boarding"), false));
            valueMap.put("stop_timezone", nullValues(records.get("stop_timezone"), true));
           
            return getFilteredValues(columns, insertValues, valueMap);
        }
        
		@Override
		public List<String> getColNames(List<String> rawCols) {
			rawCols.retainAll(stopsList);
			return rawCols;
		}
    },
    TRIPS("trips"){
    	
    	private final List<String> tripsList = Arrays.asList("route_id", "trip_id", "service_id", "trip_headsign", "trip_short_name", "direction_id", "block_id", "shape_id", "wheelchair_accessible", "bikes_allowed");
    	
        @Override
        public String getInsertValue(List<String> columns, Map<String, String> records) {
            StringJoiner insertValues = new StringJoiner(",", "(",")");
            Map<String, String> valueMap = new HashMap<>();
            valueMap.put("route_id", "'"+records.get("route_id")+"'");
            valueMap.put("trip_id", "'"+records.get("trip_id")+"'");
            valueMap.put("service_id", "'"+records.get("service_id")+"'");
            valueMap.put("trip_headsign", "'"+records.get("trip_headsign")+"'");
            valueMap.put("trip_short_name", nullValues(records.get("trip_short_name"), true));
            valueMap.put("direction_id", records.get("direction_id"));
            valueMap.put("block_id", "'"+records.get("block_id")+"'");
            valueMap.put("shape_id", "'"+records.get("shape_id")+"'");
            valueMap.put("wheelchair_accessible", records.get("wheelchair_accessible"));
            valueMap.put("bikes_allowed", records.get("bikes_allowed"));

            return getFilteredValues(columns, insertValues, valueMap);
        }
        
		@Override
		public List<String> getColNames(List<String> rawCols) {
			rawCols.retainAll(tripsList);
			return rawCols;
		}
    };
	
	/**
	 * This function responsible for the value transformation.
	 * 
	 * @param records The map which contains the actual values.
	 * @return An insert value group
	 */
    public abstract String getInsertValue(List<String> columns, Map<String, String> records);
    
    /**
     * <b>This function ensures that only those columns appears in the insert which originally exists in the GTFS's documentation.</b>
     * </br>
     * Sometimes the providers set custom columns for other function which is not part of the default column set.
     * @param rawCols
     * @return
     */
    public abstract List<String> getColNames(List<String> rawCols);
    
    private String tableName;

    private TableInsertValues(String tableName){
        this.tableName = tableName;
    }

    public String getTableName(){
        return tableName;
    }

    private static String strToDateFormat(String rawDate){
        return "'"+rawDate.substring(0,4)+"-"+rawDate.substring(4,6)+"-"+rawDate.substring(6,8)+"'";
    }

    private static String nullValues(String value, boolean isStr){
    	if(value == null){
    		return "skip";
    	}
        return "null".equals(value) ? "null" : isStr ? "'"+value+"'" : value;
    }
    
    private static String getFilteredValues(List<String> columns, StringJoiner insertValues, Map<String, String> valueMap){
        for (int i = 0; i < columns.size(); i++) {
        	String col = columns.get(i);
        	insertValues.add(valueMap.get(col));
		}
        
        String filteredValues = insertValues.toString().replaceAll("(skip\\,?)", "").replaceAll(",\\)", "\\)");
        
        return filteredValues;
    }
}
