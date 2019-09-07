package prv.pgergely.ctscountry.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

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
		this.getJdbcTemplate().update(insert, new Object[]{value.getFeedId(), value.getTitle(), Date.from(value.getLatestVersion().atStartOfDay(ZoneId.systemDefault()).toInstant()), true, true});
	}

	@Override
	public void update(FeedVersion value) {
		final String update="UPDATE feed_version SET feed_id=?, title=?, latest_version=?, recent=?, new_version=? WHERE feed_id="+value.getFeedId();
		this.getJdbcTemplate().update(update, new Object[]{value.getFeedId(), value.getTitle(), Date.from(value.getLatestVersion().atStartOfDay(ZoneId.systemDefault()).toInstant()), value.isRecent(), value.isNewVersion()});
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
		List<Map<String, Object>> allFeed = this.getJdbcTemplate().queryForList("SELECT id, feed_id, title, latest_version, recent, new_version FROM feed_version");
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
		return res;
	}

	@Override
	public void deletegetFeedVersion(FeedVersion value) {
		this.getJdbcTemplate().execute("DELETE FROM feed_version WHERE feed_id="+value.getFeedId());
	}

}
