package prv.pgergely.ctscountry.dao;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.ctscountry.interfaces.FeedVersionRepo;
import prv.pgergely.ctscountry.model.FeedVersion;

@Repository
public class FeedVersionRepoImpl extends NamedParameterJdbcTemplate implements FeedVersionRepo {
	
	@Autowired
	public FeedVersionRepoImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void insert(FeedVersion value) {
		final String insert = "INSERT INTO feed_version(feed_id, title, latest_version, recent, new_version) VALUES(:feedId, :tit, :latestVersion, :rc, :isNew);";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("feedId", value.getFeedId());
		params.addValue("tit", value.getTitle());
		params.addValue("latestVersion", Date.from(value.getLatestVersion().toInstant()));
		params.addValue("rc", true);
		params.addValue("isNew", true);
		this.update(insert, params);
	}

	@Override
	public void update(FeedVersion value) {
		final String update="UPDATE feed_version SET title=:tit, latest_version=:latestVersion, recent=:rc, new_version=:isNew WHERE feed_id=:feedId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("feedId", value.getFeedId());
		params.addValue("tit", value.getTitle());
		params.addValue("latestVersion", Date.from(value.getLatestVersion().toInstant()));
		params.addValue("rc", value.isRecent());
		params.addValue("isNew", value.isNewVersion());
		this.update(update, params);
	}

	@Override
	public FeedVersion getFeedVersionById(long id) {
		final String select = "SELECT id, title, state, latest_version, recent, new_version FROM feed_version WHERE feed_id=:feedId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("feedId", id);
		List<Map<String, Object>> allFeed = this.queryForList(select, params);
		List<FeedVersion> res = allFeed.stream().map(feed -> {
			FeedVersion vers = new FeedVersion();
			vers.setId(Long.valueOf(feed.get("id").toString()));
			vers.setFeedId(id);
			vers.setTitle(feed.get("title").toString());
			vers.setLatestVersion(OffsetDateTime.parse(feed.get("latest_version").toString()));
			vers.setRecent((boolean)feed.get("recent"));
			vers.setNewVersion((boolean)feed.get("new_version"));
			return vers;
		}).collect(Collectors.toList());
		if(res.isEmpty()) {
			return null;
		}
		return res.get(0);
	}

	@Override
	public List<FeedVersion> getFeedVersions() {
		final String select = """
							  SELECT v.id, v.feed_id, v.title, v.latest_version, v.recent, v.new_version, d.state, d.active, d.source_url, d.schema_name 
							  FROM feed_version v  
							  INNER JOIN datasource_info d USING(feed_id) """;
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		List<Map<String, Object>> allFeed = this.queryForList(select, new MapSqlParameterSource());
		List<FeedVersion> res = allFeed.stream().map(feed -> {
			FeedVersion vers = new FeedVersion(feed.get("source_url").toString(), feed.get("schema_name").toString(), DataSourceState.valueOf(String.valueOf(feed.get("state"))), Boolean.valueOf(feed.get("active").toString()));
			vers.setId(Long.valueOf(feed.get("id").toString()));
			vers.setFeedId(Long.valueOf(feed.get("feed_id").toString()));
			vers.setTitle(feed.get("title").toString());
			LocalDateTime locTime = LocalDateTime.parse(feed.get("latest_version").toString(), formatter);
			vers.setLatestVersion(locTime.atOffset(ZoneOffset.UTC));
			vers.setRecent(Boolean.valueOf(feed.get("recent").toString()));
			vers.setNewVersion(Boolean.valueOf(feed.get("new_version").toString()));
			return vers;
		}).collect(Collectors.toList());
		return res;
	}

	@Override
	public void deletegetFeedVersion(FeedVersion value) throws HttpClientErrorException {
		final String delete = "DELETE FROM feed_version WHERE feed_id=:feedId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("feedId", value.getFeedId());
		int rows = this.update(delete, params);
		if(rows == 0) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

}
