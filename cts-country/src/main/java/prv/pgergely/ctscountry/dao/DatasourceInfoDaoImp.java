package prv.pgergely.ctscountry.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.ctscountry.interfaces.DatasourceInfoDao;
import prv.pgergely.ctscountry.model.DatasourceInfo;

@Repository
public class DatasourceInfoDaoImp extends JdbcDaoSupport implements DatasourceInfoDao {
	
	@Autowired
	private DataSource dataSrc;
	
	@PostConstruct
	public void init() {
		super.setDataSource(dataSrc);
	}
	
	@Override
	public void insert(DatasourceInfo value) {
		final String insert = "INSERT INTO datasource_info (feed_id, source_name, source_url, schema_name, active) VALUES (?,?,?,?,?)";
		this.getJdbcTemplate().update(insert, value.getFeedId(), value.getSource_name(), value.getSource_url(), value.getSchema_name(), true);
	}

	@Override
	public void update(DatasourceInfo value) {
		final String update = "UPDATE datasource_info SET feed_id=? source_name=?, source_url=?, schema_name=?, active=? WHERE id=?";
		this.getJdbcTemplate().update(update, value.getFeedId(), value.getSource_name(), value.getSource_url(), value.getSchema_name(), value.getId(), value.isActive());
	}
	
	@Override
	public void setActive(Long feedId) {
		this.getJdbcTemplate().update("UPDATE datasource_info SET active=TRUE WHERE feed_id="+feedId);
	}

	@Override
	public DatasourceInfo getDatasourceInfoById(long id) {
		final String select = "SELECT id, feed_id, source_name, source_url, schema_name, active FROM datasource_info WHERE feed_id=?";
		RowMapper<DatasourceInfo> mapper = (rs, rows) ->{
			DatasourceInfo info = new DatasourceInfo(
										rs.getLong("id"), 
										rs.getLong("feed_id"), 
										rs.getString("source_name"), 
										rs.getString("source_url"), 
										rs.getString("schema_name"), 
										rs.getBoolean("active")
										);
			return info;
		};
		
		return this.getJdbcTemplate().queryForObject(select, mapper, id);
	}

	@Override
	public List<DatasourceInfo> getAllDatasourceInfo() {
		final String selectAll = "SELECT id, feed_id, source_name, source_url, schema_name, active FROM datasource_info";
		List<Map<String, Object>> rawRes = this.getJdbcTemplate().queryForList(selectAll);
		List<DatasourceInfo> results = rawRes
										.stream()
										.map(mp -> new DatasourceInfo(
											Long.valueOf(mp.get("id").toString()), 
											Long.valueOf(mp.get("feed_id").toString()), 
											String.valueOf(mp.get("source_name")), 
											String.valueOf(mp.get("source_url")), 
											String.valueOf(mp.get("schema_name")), 
											Boolean.valueOf(mp.get("active").toString()
											)))
										.collect(Collectors.toList());
		
		return results;
	}

	@Override
	public void deleteDatasourceInfo(long feedId) throws HttpClientErrorException {
		int rows = this.getJdbcTemplate().update("UPDATE datasource_info SET active=FALSE WHERE feed_id=?", feedId);
		if(rows == 0) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

}
