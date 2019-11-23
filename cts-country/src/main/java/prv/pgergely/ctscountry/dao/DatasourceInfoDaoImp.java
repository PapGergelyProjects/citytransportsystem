package prv.pgergely.ctscountry.dao;

import java.util.ArrayList;
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
		final String insert = "INSERT INTO datasource_info (feed_id, source_name, source_url, schema_name) VALUES (?,?,?,?)";
		this.getJdbcTemplate().update(insert, new Object[] {value.getFeedId(), value.getSource_name(), value.getSource_url(), value.getSchema_name()});
	}

	@Override
	public void update(DatasourceInfo value) {
		final String update = "UPDATE datasource_info SET feed_id=? source_name=?, source_url=?, schema_name=? WHERE id=?";
		this.getJdbcTemplate().update(update, new Object[] {value.getFeedId(), value.getSource_name(), value.getSource_url(), value.getSchema_name(), value.getId()});
	}

	@Override
	public DatasourceInfo getDatasourceInfoById(long id) {
		final String select = "SELECT id, feed_id, source_name, source_url, schema_name FROM datasource_info WHERE id=?";
		RowMapper<DatasourceInfo> mapper = (rs, rows) ->{
			DatasourceInfo info = new DatasourceInfo(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getString(4), rs.getString(5));
			return info;
		};
		
		return this.getJdbcTemplate().queryForObject(select, new Object[] {id}, mapper);
	}

	@Override
	public List<DatasourceInfo> getDatasourceInfos() {
		final String selectAll = "SELECT id, feed_id, source_name, source_url, schema_name FROM datasource_info";
		List<Map<String, Object>> rawRes = this.getJdbcTemplate().queryForList(selectAll);
		List<DatasourceInfo> results = rawRes
										.stream()
										.map(mp -> new DatasourceInfo(Long.valueOf(mp.get("id").toString()), Long.valueOf(mp.get("feed_id").toString()), String.valueOf(mp.get("source_name")), String.valueOf(mp.get("source_url")), String.valueOf(mp.get("schema_name"))))
										.collect(Collectors.toList());
		
		return results;
	}

	@Override
	public void deleteDatasourceInfo(long feedId) throws HttpClientErrorException {
		int rows = this.getJdbcTemplate().update("DELETE FROM datasource_info WHERE feed_id=?", new Object[]{feedId});
		if(rows == 0) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

}
