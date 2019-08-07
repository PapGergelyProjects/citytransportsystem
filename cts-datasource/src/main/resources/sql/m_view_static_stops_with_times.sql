CREATE MATERIALIZED VIEW IF NOT EXISTS static_stops_with_times AS
SELECT route_short_name, stop_name, stop_lat, stop_lon, 
route_color, route_text_color, "date", departure_time
FROM trips t
INNER JOIN calendar_dates USING(service_id)
INNER JOIN routes USING(route_id)
INNER JOIN (
    select trip_id, stop_sequence, 
    stop_name, departure_time, stop_lat, stop_lon
    FROM stop_times
    INNER JOIN stops USING(stop_id)
) s USING(trip_id)
GROUP BY route_short_name, stop_sequence, stop_name, stop_lat, stop_lon, route_color, route_text_color, trip_id, departure_time, "date"
ORDER BY route_short_name, departure_time;

CREATE INDEX IF NOT EXISTS lat_lon_idx ON static_stops_with_times (stop_lat, stop_lon);
CREATE INDEX IF NOT EXISTS times ON static_stops_with_times ("date", departure_time);
CREATE INDEX IF NOT EXISTS st_names ON static_stops_with_times (route_short_name, stop_name)^;