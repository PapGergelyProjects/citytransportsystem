package prv.pgergely.ctsdata.interfaces;

public interface GtfsTableDao {
	
	public void insert(String insertValues);
	public void clearTables();
	public void refreshMateralizedView();
}
