CREATE OR REPLACE FUNCTION arcpoints(latitude DOUBLE PRECISION, longitude DOUBLE PRECISION, distance INTEGER)
RETURNS TABLE(
	degree INTEGER, 
	lat DOUBLE PRECISION, 
	lon DOUBLE PRECISION
) AS
$BODY$
DECLARE
    latitude ALIAS FOR $1;
    longitude ALIAS FOR $2;
    distance ALIAS FOR $3;

    dist DOUBLE PRECISION := (distance / 6378137.0);
    bear DOUBLE PRECISION := 0.0;
    lat DOUBLE PRECISION := radians(latitude);
    lon DOUBLE PRECISION := radians(longitude);
    lat1 DOUBLE PRECISION := 0.0;
    lon1 DOUBLE PRECISION := 0.0;
    idx INT := 0;
BEGIN
    while idx<360
    loop
        idx := idx+1;
        bear := radians(idx);
        lat1 := asin(sin(lat) * cos(dist) + cos(lat) * sin(dist) * cos(bear));
        lon1 := lon + atan2(sin(bear) * sin(dist) * cos(lat), cos(dist) - sin(lat) * sin(lat1));
        
        RETURN QUERY SELECT idx, degrees(lat1), degrees(lon1);
    END loop;
END
$BODY$
LANGUAGE plpgsql^;