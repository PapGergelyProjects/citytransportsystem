package prv.pgergely.ctsdata.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prv.pgergely.ctsdata.interfaces.GtfsTableRepo;

@Service
public class GtfsTablesService {
	
	@Autowired
	private GtfsTableRepo tablesSrv;
	
	@Transactional
	public void insertValues(String inserts) {
		tablesSrv.insert(inserts);
	}
	
	@Transactional
	public void copy(String copyQuery, InputStream copyValue) throws CannotGetJdbcConnectionException, SQLException, IOException {
		tablesSrv.copy(copyQuery, copyValue);
	}
	
	public void refreshMateralizedViews() {
		tablesSrv.refreshMateralizedView();
	}
	
	public void clearTables() {
		tablesSrv.clearTables();
	}
	
	public void vacuumTable(String tableName) throws CannotGetJdbcConnectionException, SQLException {
		tablesSrv.vacuumTable(tableName);
	}
}
