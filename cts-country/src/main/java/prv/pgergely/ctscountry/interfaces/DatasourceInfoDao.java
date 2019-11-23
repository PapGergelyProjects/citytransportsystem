package prv.pgergely.ctscountry.interfaces;

import java.util.List;

import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.ctscountry.model.DatasourceInfo;

public interface DatasourceInfoDao {

	public void insert(DatasourceInfo value);
	public void update(DatasourceInfo value);
	public DatasourceInfo getDatasourceInfoById(long id);
	public List<DatasourceInfo> getDatasourceInfos();
	public void deleteDatasourceInfo(long feedId) throws HttpClientErrorException;
	
}
