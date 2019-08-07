CREATE OR REPLACE FUNCTION clear_tables()
RETURNS VOID as
$funct$
BEGIN
    TRUNCATE TABLE agency;
    ALTER SEQUENCE agency_id_seq RESTART WITH 1;
    
    TRUNCATE TABLE feed_info;
    ALTER SEQUENCE feed_info_id_seq RESTART WITH 1;
    
    TRUNCATE TABLE calendar_dates;
    ALTER SEQUENCE calendar_dates_id_seq RESTART WITH 1;
    
    TRUNCATE TABLE pathways;
    ALTER SEQUENCE pathways_id_seq RESTART WITH 1;
    
    TRUNCATE TABLE routes;
    ALTER SEQUENCE routes_id_seq RESTART WITH 1;
    
    TRUNCATE TABLE shapes;
    ALTER SEQUENCE shapes_id_seq RESTART WITH 1;

    TRUNCATE TABLE stop_times;
    ALTER SEQUENCE stop_times_id_seq RESTART WITH 1;
    
    TRUNCATE TABLE stops;
    ALTER SEQUENCE stops_id_seq RESTART WITH 1;
    
    TRUNCATE TABLE trips;
    ALTER SEQUENCE trips_id_seq RESTART WITH 1;
END;
$funct$ 
LANGUAGE plpgsql^;