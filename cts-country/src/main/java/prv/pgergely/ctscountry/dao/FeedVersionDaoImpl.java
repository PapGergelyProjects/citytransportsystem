package prv.pgergely.ctscountry.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.annotation.PostConstruct;
import prv.pgergely.ctscountry.interfaces.FeedVersionDao;
import prv.pgergely.ctscountry.model.FeedVersion;

@Repository
public class FeedVersionDaoImpl extends JdbcDaoSupport implements FeedVersionDao {
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	private void initSource() {
		super.setDataSource(dataSource);
	}
	
	@Override
	public void insert(FeedVersion value) {
		final String insert = "INSERT INTO feed_version(feed_id, title, latest_version, recent, new_version) VALUES(?, ?, ?, ?, ?);";
		this.getJdbcTemplate().update(insert, value.getFeedId(), value.getTitle(), Date.from(value.getLatestVersion().atStartOfDay(ZoneId.systemDefault()).toInstant()), true, true);
	}

	@Override
	public void update(FeedVersion value) {
		final String update="UPDATE feed_version SET feed_id=?, title=?, latest_version=?, recent=?, new_version=? WHERE feed_id="+value.getFeedId();
		this.getJdbcTemplate().update(update, value.getFeedId(), value.getTitle(), Date.from(value.getLatestVersion().atStartOfDay(ZoneId.systemDefault()).toInstant()), value.isRecent(), value.isNewVersion());
	}

	@Override
	public FeedVersion getFeedVersionById(long id) {
		final String select = "SELECT id, feed_id, title, latest_version, recent, new_version FROM feed_version WHERE feed_id="+id;
		List<Map<String, Object>> allFeed = this.getJdbcTemplate().queryForList(select);
		List<FeedVersion> res = new ArrayList<>();
		allFeed.forEach(feed -> {
			FeedVersion vers = new FeedVersion();
			vers.setId(Long.valueOf(feed.get("id").toString()));
			vers.setFeedId(Long.valueOf(feed.get("feed_id").toString()));
			vers.setTitle(feed.get("title").toString());
			vers.setLatestVersion(LocalDate.parse(feed.get("latest_version").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			vers.setRecent((boolean)feed.get("recent"));
			vers.setNewVersion((boolean)feed.get("new_version"));
			res.add(vers);
		});
		if(res.isEmpty()) {
			return null;
		}
		return res.get(0);
	}

	@Override
	public List<FeedVersion> getFeedVersions() {
		final String select = "SELECT v.id, v.feed_id, v.title, v.latest_version, v.recent, v.new_version, d.active, d.source_url, d.schema_name "
							+ "FROM feed_version v "
							+ "INNER JOIN datasource_info d USING(feed_id)";
		List<Map<String, Object>> allFeed = this.getJdbcTemplate().queryForList(select);
		List<FeedVersion> res = new ArrayList<>();
		allFeed.forEach(feed -> {
			FeedVersion vers = new FeedVersion();
			vers.setId(Long.valueOf(feed.get("id").toString()));
			vers.setFeedId(Long.valueOf(feed.get("feed_id").toString()));
			vers.setTitle(feed.get("title").toString());
			vers.setLatestVersion(LocalDate.parse(feed.get("latest_version").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			vers.setRecent((boolean)feed.get("recent"));
			vers.setNewVersion((boolean)feed.get("new_version"));
			vers.setActive((boolean)feed.get("active"));
			vers.setDsUrl(feed.get("source_url").toString());
			vers.setSchemaName(feed.get("schema_name").toString());
			res.add(vers);
		});
		return res;
	}

	@Override
	public void deletegetFeedVersion(FeedVersion value) throws HttpClientErrorException {
		int rows = this.getJdbcTemplate().update("DELETE FROM feed_version WHERE feed_id=?", new Object[]{value.getFeedId()});
		if(rows == 0) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

}
