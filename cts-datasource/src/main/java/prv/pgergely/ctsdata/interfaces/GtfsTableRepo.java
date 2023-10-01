package prv.pgergely.ctsdata.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.springframework.jdbc.CannotGetJdbcConnectionException;

public interface GtfsTableRepo {
	
	public void insert(String insertValues);
	public void copy(String copyQuery, InputStream copyValue) throws CannotGetJdbcConnectionException, SQLException, IOException;
	public void clearTables();
	public void refreshMateralizedView();
	public void vacuumTable(String tableName) throws CannotGetJdbcConnectionException, SQLException;
}
