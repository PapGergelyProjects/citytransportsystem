DO
$body$
BEGIN
	IF NOT EXISTS(SELECT * FROM information_schema.schemata WHERE schema_name='cts_transit_feed') THEN
		CREATE SCHEMA cts_transit_feed;
		CREATE TYPE state_enum AS ENUM('ONLINE','OFFLINE', 'UPDATING', 'UNREGISTERED');
		CREATE CAST (varchar AS state_enum) WITH INOUT AS IMPLICIT;
		CREATE TABLE feed_version(
		    id SERIAL PRIMARY KEY,
		    feed_id INT,
		    title TEXT,
		    latest_version TIMESTAMP,
		    recent BOOLEAN,
		    new_version BOOLEAN DEFAULT FALSE
		);
		CREATE TABLE datasource_info(
		    id SERIAL PRIMARY KEY,
		    feed_id INT,
		    source_name TEXT,
		    source_url TEXT,
		    schema_name TEXT,
		    state state_enum,
		    active BOOLEAN DEFAULT FALSE
		);
	END IF;
END;
$body$ ^;
