package prv.pgergely.ctscountry.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.ctscountry.interfaces.DatasourceInfoRepo;
import prv.pgergely.ctscountry.model.DatasourceInfo;

@Repository
public class DatasourceInfoRepoImp extends NamedParameterJdbcTemplate implements DatasourceInfoRepo {
	
	@Autowired
	public DatasourceInfoRepoImp(DataSource dataSrc) {
		super(dataSrc);
	}
	
	@Override
	public void insert(DatasourceInfo value) {
		final String insert = "INSERT INTO datasource_info (feed_id, source_name, source_url, schema_name, state, active) VALUES (:feedId, :sourceName, :sourceUrl, :schemaName, :st, :ac)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("feedId", value.getFeedId());
		params.addValue("sourceName", value.getSourceName());
		params.addValue("sourceUrl", value.getSourceUrl());
		params.addValue("schemaName", value.getSchemaName());
		params.addValue("st", value.getState().toString());
		params.addValue("ac", true);
		this.update(insert, params);
	}

	@Override
	public void update(DatasourceInfo value) {
		final String update = "UPDATE datasource_info SET feed_id=:feedId source_name=:sourceName, source_url=:sourceUrl, schema_name=:schemaName, state=:st, active=:ac WHERE id=:ID";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("feedId", value.getFeedId());
		params.addValue("sourceName", value.getSourceName());
		params.addValue("sourceUrl", value.getSourceUrl());
		params.addValue("schemaName", value.getSchemaName());
		params.addValue("st", value.getState().toString());
		params.addValue("ac", value.isActive());
		params.addValue("ID", value.getId());
		this.update(update, params);
	}
	
	@Override
	public void setActive(Long feedId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("act", true);
		params.addValue("st", DataSourceState.ONLINE.toString());
		params.addValue("id", feedId);
		this.update("UPDATE datasource_info SET active=:act, state=:st WHERE feed_id=:id", params);
	}

	@Override
	public DatasourceInfo getDatasourceInfoById(long id) {
		final String select = "SELECT id, feed_id, source_name, source_url, schema_name, state, active FROM datasource_info WHERE feed_id=:feedId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("feedId", id);
		RowMapper<DatasourceInfo> mapper = (rs, rows) ->{
			DatasourceInfo info = new DatasourceInfo(
										rs.getLong("id"), 
										rs.getLong("feed_id"), 
										rs.getString("source_name"), 
										rs.getString("source_url"), 
										rs.getString("schema_name"), 
										DataSourceState.valueOf(rs.getString("state")),
										rs.getBoolean("active")
										);
			return info;
		};
		return this.queryForObject(select, params, mapper);
	}

	@Override
	public List<DatasourceInfo> getAllDatasourceInfo() {
		final String selectAll = "SELECT id, feed_id, source_name, source_url, schema_name, state, active FROM datasource_info";
		List<Map<String, Object>> rawRes = this.getJdbcTemplate().queryForList(selectAll);
		List<DatasourceInfo> results = rawRes
										.stream()
										.map(mp -> getDsInfo(mp))
										.collect(Collectors.toList());
		
		return results;
	}
	
	private DatasourceInfo getDsInfo(Map<String, Object> mp) {
		return new DatasourceInfo(
				Long.valueOf(mp.get("id").toString()), 
				Long.valueOf(mp.get("feed_id").toString()), 
				String.valueOf(mp.get("source_name")), 
				String.valueOf(mp.get("source_url")), 
				String.valueOf(mp.get("schema_name")),
				DataSourceState.valueOf(String.valueOf(mp.get("state"))),
				Boolean.valueOf(mp.get("active").toString())
			);
	}

	@Override
	public void deleteDatasourceInfo(long feedId) throws HttpClientErrorException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", feedId);
		int rows = this.update("DELETE FROM datasource_info WHERE feed_id=:id", params);
		if(rows == 0) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void updateState(Long feedId, DataSourceState state) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("st", DataSourceState.ONLINE.toString());
		params.addValue("id", feedId);
		this.update("UPDATE datasource_info SET state=:st WHERE feed_id=:id", params);
	}

}
