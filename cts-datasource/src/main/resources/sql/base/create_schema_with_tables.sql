DO
$body$
BEGIN
    IF NOT EXISTS(SELECT * FROM information_schema.schemata WHERE schema_name='<schema_name>') THEN
    CREATE SCHEMA <schema_name>
		CREATE TABLE agency(
		    id SERIAL PRIMARY KEY,
		    agency_id CHARACTER VARYING(10),
		    agency_name CHARACTER VARYING(255),
		    agency_url TEXT,
		    agency_timezone VARCHAR(65),
		    agency_lang CHARACTER VARYING(10),
		    agency_phone CHARACTER VARYING(255),
		    agency_fare_url TEXT,
		    agency_email CHARACTER VARYING(255)
		)
		CREATE TABLE calendar_dates(
		    id SERIAL PRIMARY KEY,
		    service_id CHARACTER VARYING(255),
		    "date" DATE,
		    exception_type INTEGER
		)
		CREATE TABLE feed_info(
		    id SERIAL PRIMARY KEY,
		    feed_publisher_name CHARACTER VARYING(100),
		    feed_publisher_url TEXT,
		    feed_lang CHARACTER VARYING(10),
		    feed_start_date DATE,
		    feed_end_date DATE,
		    feed_version CHARACTER VARYING(255),
		    feed_contact_email CHARACTER VARYING(255),
		    feed_contact_url CHARACTER VARYING(255)
		)
		CREATE TABLE pathways(
		    id SERIAL PRIMARY KEY,
		    pathway_id CHARACTER VARYING(255),
		    pathway_type INTEGER,
		    from_stop_id CHARACTER VARYING(100),
		    to_stop_id CHARACTER VARYING(100),
		    traversal_time INTEGER,
		    wheelchair_traversal_time INTEGER
		)
		CREATE TABLE routes(
		    id SERIAL PRIMARY KEY,
		    route_id CHARACTER VARYING(60),
		    agency_id CHARACTER VARYING(10),
		    route_short_name CHARACTER VARYING(10),
		    route_long_name CHARACTER VARYING(255),
		    route_desc TEXT,
		    route_type INTEGER,
		    route_url TEXT,
		    route_color CHARACTER VARYING(10),
		    route_text_color CHARACTER VARYING(10),
		    route_sort_order INTEGER
		)
		CREATE TABLE shapes(
		    id SERIAL PRIMARY KEY,
		    shape_id CHARACTER VARYING(10),
		    shape_pt_lat DOUBLE PRECISION,
		    shape_pt_lon DOUBLE PRECISION,
		    shape_pt_sequence INTEGER,
		    shape_dist_traveled DOUBLE PRECISION
		)
		CREATE TABLE stop_times(
		    id SERIAL PRIMARY KEY,
		    trip_id CHARACTER VARYING(100),
		    stop_id CHARACTER VARYING(100),
		    arrival_time INTERVAL,
		    departure_time INTERVAL,
		    stop_sequence INTEGER,
		    stop_headsign CHARACTER VARYING(100),
			pickup_type INTEGER,
			drop_off_type INTEGER,
		    shape_dist_traveled DOUBLE PRECISION,
		    timepoint INTEGER
		)
		CREATE TABLE stops(
		    id SERIAL PRIMARY KEY,
		    stop_id CHARACTER VARYING(100),
		    stop_code CHARACTER VARYING(20),
		    stop_name TEXT,
		    stop_desc TEXT,
		    stop_lat DOUBLE PRECISION,
		    stop_lon DOUBLE PRECISION,
		    zone_id CHARACTER VARYING(20),
		    stop_url TEXT,
		    location_type INTEGER,
		    parent_station CHARACTER VARYING(20),
		    stop_timezone CHARACTER VARYING(100),
		    wheelchair_boarding INTEGER,
		    level_id INTEGER,
		    platform_code CHARACTER VARYING(10)
		)
		CREATE TABLE trips(
		    id SERIAL PRIMARY KEY,
		    route_id CHARACTER VARYING(60),
		    service_id CHARACTER VARYING(255),
		    trip_id CHARACTER VARYING(100),
		    trip_headsign TEXT,
		    trip_short_name CHARACTER VARYING(100),
		    direction_id INTEGER,
		    block_id CHARACTER VARYING(100),
		    shape_id CHARACTER VARYING(10),
		    wheelchair_accessible INTEGER,
		    bikes_allowed INTEGER
		);
		SET SCHEMA '<schema_name>';
    END IF;
END;
$body$ ^;