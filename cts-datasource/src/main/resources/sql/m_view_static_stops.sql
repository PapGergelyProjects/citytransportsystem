CREATE MATERIALIZED VIEW IF NOT EXISTS static_stops AS 
SELECT r.route_short_name, s.stop_name, s.stop_lat, s.stop_lon, r.route_color, r.route_text_color
FROM trips t
INNER JOIN routes r USING(route_id)
INNER JOIN stop_times st USING(trip_id)
INNER JOIN stops s USING (stop_id)
GROUP BY r.route_short_name, st.stop_sequence, s.stop_name, s.stop_lat, s.stop_lon, r.route_color, r.route_text_color
ORDER BY r.route_short_name;

CREATE INDEX IF NOT EXISTS lat_lng_idx ON static_stops (stop_lat, stop_lon);
CREATE INDEX IF NOT EXISTS stop_names ON static_stops (stop_name)^;