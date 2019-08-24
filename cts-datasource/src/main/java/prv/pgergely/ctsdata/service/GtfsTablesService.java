package prv.pgergely.ctsdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.ctsdata.interfaces.GtfsTableDao;

@Service
public class GtfsTablesService {
	
	@Autowired
	private GtfsTableDao tablesSrv;
	
	public void insertValues(String inserts) {
		tablesSrv.insert(inserts);
	}
	
	public void refreshMateralizedViews() {
		tablesSrv.refreshMateralizedView();
	}
	
	public void clearTables() {
		tablesSrv.clearTables();
	}
}
