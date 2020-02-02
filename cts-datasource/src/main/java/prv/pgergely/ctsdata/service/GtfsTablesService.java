package prv.pgergely.ctsdata.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;

import prv.pgergely.ctsdata.interfaces.GtfsTableDao;

@Service
public class GtfsTablesService {
	
	@Autowired
	private GtfsTableDao tablesSrv;
	
	public void insertValues(String inserts) {
		tablesSrv.insert(inserts);
	}
	
	public void copy(String copyQuery, InputStream copyValue) throws CannotGetJdbcConnectionException, SQLException, IOException {
		tablesSrv.copy(copyQuery, copyValue);
	}
	
	public void refreshMateralizedViews() {
		tablesSrv.refreshMateralizedView();
	}
	
	public void clearTables() {
		tablesSrv.clearTables();
	}
}
