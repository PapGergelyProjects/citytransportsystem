DO
$body$
BEGIN
	IF NOT EXISTS(SELECT * FROM information_schema.schemata WHERE schema_name='cts_transit_feed') THEN
		CREATE SCHEMA cts_transit_feed
		CREATE TABLE feed_version(
		    id SERIAL PRIMARY KEY,
		    feed_id INT,
		    title TEXT,
		    latest_version DATE,
		    recent BOOLEAN,
		    new_version BOOLEAN DEFAULT FALSE
		);
		CREATE TABLE datasource_info(
		    id SERIAL PRIMARY KEY,
		    feed_id INT,
		    source_name TEXT,
		    source_url TEXT,
		    schema_name TEXT
		);
	END IF;
END;
$body$ ^;
