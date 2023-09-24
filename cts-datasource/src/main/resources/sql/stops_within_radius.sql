CREATE OR REPLACE FUNCTION stops_within_radius(center_lat DOUBLE PRECISION, center_lot DOUBLE PRECISION, radius DOUBLE PRECISION)
RETURNS TABLE(
    route_name VARCHAR(5),
    stop_name TEXT,
    stop_lat DOUBLE PRECISION,
    stop_lon DOUBLE PRECISION,
    stop_distance DOUBLE PRECISION,
    stop_color VARCHAR(6),
    text_color VARCHAR(6)
)
AS $body$
DECLARE
    center_latitude ALIAS FOR $1;
    center_longitude ALIAS FOR $2;
    radius ALIAS FOR $3;
    num CONSTANT DOUBLE PRECISION := 0.0175;
    earth_r CONSTANT DOUBLE PRECISION := 6371000;
BEGIN
    FOR route_name, stop_name, stop_lat, stop_lon, stop_color, text_color, stop_distance
    IN
    (
        SELECT s.route_short_name, s.stop_name, s.stop_lat, s.stop_lon, s.route_color, s.route_text_color,
        point_in_range(center_lat, center_lot, s.stop_lat, s.stop_lon) AS distance
        FROM static_stops s
        WHERE point_in_range(center_lat, center_lot, s.stop_lat, s.stop_lon) <= radius
    )
    LOOP
        RETURN NEXT;
    END LOOP;
END
$body$ LANGUAGE plpgsql^;