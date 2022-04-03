package prv.pgergely.ctscountry.interfaces;

import java.util.List;

import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.ctscountry.model.DatasourceInfo;

public interface DatasourceInfoDao {

	public void insert(DatasourceInfo value);
	public void update(DatasourceInfo value);
	public void setActive(Long feedId);
	public DatasourceInfo getDatasourceInfoById(long id);
	public List<DatasourceInfo> getAllDatasourceInfo();
	public void deleteDatasourceInfo(long feedId) throws HttpClientErrorException;
	
}
