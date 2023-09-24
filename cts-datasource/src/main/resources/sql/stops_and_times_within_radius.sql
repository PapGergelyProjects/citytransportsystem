CREATE OR REPLACE FUNCTION stop_and_times_within_range(center_lat DOUBLE PRECISION, center_lot DOUBLE PRECISION, radius DOUBLE PRECISION)
RETURNS TABLE(
    route_name CHARACTER VARYING, 
    stop_names TEXT, 
    stop_latitude DOUBLE PRECISION, 
    stop_longitude DOUBLE PRECISION, 
    stop_distance DOUBLE PRECISION, 
    stop_color character varying, 
    text_color character varying, 
    depart_time TIME[]
)
AS $body$
DECLARE
    center_lat ALIAS FOR $1;
    center_lot ALIAS FOR $2;
    radius ALIAS FOR $3;
    depa_time TIME;
    depa_times TIME[];
BEGIN
    FOR route_name, stop_names, stop_latitude, stop_longitude, stop_color, text_color, stop_distance
    IN
    (
        SELECT s.route_short_name, s.stop_name, s.stop_lat, s.stop_lon, s.route_color, s.route_text_color,
        point_in_range(center_lat, center_lot, s.stop_lat, s.stop_lon) AS distance
        FROM static_stops s
        WHERE point_in_range(center_lat, center_lot, s.stop_lat, s.stop_lon) <= radius
    )
    LOOP
        depa_times := '{}';
        FOR depa_time
        IN(
            WITH stopTimesByCoords AS(
                SELECT st.route_short_name, st.stop_name, st.stop_lat, st.stop_lon,
                point_in_range(center_lat, center_lot, st.stop_lat, st.stop_lon) AS distance,
                st.route_color, st.route_text_color, st."date", st.departure_time,
                ROW_NUMBER() OVER (PARTITION BY st.route_short_name, st.stop_name, st.stop_lat, st.stop_lon ORDER BY st.departure_time) AS dep_times
                FROM static_stops_with_times st
                WHERE st.departure_time >= CURRENT_TIME 
                AND st."date" = CURRENT_DATE 
                AND route_short_name = route_name
                AND st.stop_lat = stop_latitude
                AND st.stop_lon = stop_longitude
            )SELECT st.departure_time 
            FROM stopTimesByCoords st
            WHERE st.dep_times <=3
        )
        LOOP
            depa_times := array_append(depa_times, depa_time);
        END LOOP;
        
        depart_time := depa_times;
        
        RETURN NEXT;
    END LOOP;
END
$body$ LANGUAGE plpgsql^;