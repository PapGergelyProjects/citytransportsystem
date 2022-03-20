CREATE MATERIALIZED VIEW IF NOT EXISTS static_stops_with_times AS
SELECT r.route_short_name,
    s.stop_name,
    s.stop_lat,
    s.stop_lon,
    r.route_color,
    r.route_text_color,
    c."date",
    st.departure_time
FROM trips t
INNER JOIN calendar_dates c USING (service_id)
INNER JOIN routes r USING (route_id)
INNER JOIN stop_times st USING(trip_id)
INNER JOIN stops s using(stop_id)
GROUP BY r.route_short_name, st.stop_sequence, s.stop_name, s.stop_lat, s.stop_lon, r.route_color, r.route_text_color, t.trip_id, st.departure_time, c."date"
ORDER BY r.route_short_name, st.departure_time;


CREATE INDEX IF NOT EXISTS lat_lon_idx ON static_stops_with_times (stop_lat, stop_lon);
CREATE INDEX IF NOT EXISTS times ON static_stops_with_times ("date", departure_time);
CREATE INDEX IF NOT EXISTS st_names ON static_stops_with_times (route_short_name, stop_name)^;