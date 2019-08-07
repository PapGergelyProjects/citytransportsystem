CREATE MATERIALIZED VIEW IF NOT EXISTS static_stops AS 
SELECT route_short_name, stop_name, stop_lat, stop_lon, route_color, route_text_color
FROM trips t
INNER JOIN calendar_dates USING(service_id)
INNER JOIN routes USING(route_id)
INNER JOIN (
    select trip_id, stop_sequence, 
    stop_name, departure_time, stop_lat, stop_lon
    FROM stop_times
    INNER JOIN stops USING(stop_id)
) s USING(trip_id)
GROUP BY route_short_name, stop_sequence, stop_name, stop_lat, stop_lon, route_color, route_text_color
ORDER BY route_short_name;

CREATE INDEX IF NOT EXISTS lat_lng_idx ON static_stops (stop_lat, stop_lon);
CREATE INDEX IF NOT EXISTS stop_names ON static_stops (stop_name)^;