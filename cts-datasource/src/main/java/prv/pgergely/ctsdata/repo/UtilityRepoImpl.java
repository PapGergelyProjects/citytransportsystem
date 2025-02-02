package prv.pgergely.ctsdata.repo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.ctsdata.interfaces.UtilityRepo;
import prv.pgergely.ctsdata.model.Schema;

@Repository
public class UtilityRepoImpl extends NamedParameterJdbcTemplate implements UtilityRepo {
	
	@Autowired
	private Schema schema;
	
	@Autowired
	public UtilityRepoImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void updateSourceState(DataSourceState state) {
		final String sql = "UPDATE cts_transit_feed.datasource_info SET state=:st WHERE feed_id=:id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("st", state.toString());
		params.addValue("id", schema.getFeedId());
		
		this.update(sql, params);
	}

}
