CREATE OR REPLACE FUNCTION point_in_range(o_lat DOUBLE PRECISION, o_lon DOUBLE PRECISION, p_lat DOUBLE PRECISION, p_lon DOUBLE PRECISION)
RETURNS DOUBLE PRECISION
AS $$
DECLARE
    o_lat ALIAS FOR $1;
    o_lon ALIAS FOR $2;
    p_lat ALIAS FOR $3;
    p_lon ALIAS FOR $4;
    num CONSTANT DOUBLE PRECISION := 0.0175;
    earth_r CONSTANT DOUBLE PRECISION := 6371000;
BEGIN
    RETURN acos(sin(p_lat * num) * sin(o_lat * num) + cos(p_lat * num) * cos(o_lat * num) * cos((o_lon * num)-(p_lon * num))) * earth_r;
END
$$ LANGUAGE plpgsql^;