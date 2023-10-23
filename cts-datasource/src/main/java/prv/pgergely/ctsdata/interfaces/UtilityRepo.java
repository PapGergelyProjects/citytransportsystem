package prv.pgergely.ctsdata.interfaces;

import prv.pgergely.cts.common.domain.DataSourceState;

public interface UtilityRepo {

	public void updateSourceState(DataSourceState state);
	
}
