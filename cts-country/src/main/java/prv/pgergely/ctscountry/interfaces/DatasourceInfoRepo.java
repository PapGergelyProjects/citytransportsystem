package prv.pgergely.ctscountry.interfaces;

import java.util.List;

import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.ctscountry.model.DatasourceInfo;

public interface DatasourceInfoRepo {

	public void insert(DatasourceInfo value);
	public void update(DatasourceInfo value);
	public void updateState(Long id, DataSourceState state);
	public void setActive(Long feedId);
	public DatasourceInfo getDatasourceInfoById(long id);
	public List<DatasourceInfo> getAllDatasourceInfo();
	public void deleteDatasourceInfo(long feedId) throws HttpClientErrorException;
	
}
