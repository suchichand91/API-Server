package com.management.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ManagementDaoImpl implements ManagementDao {

	private static final String CHECK_IF_BLOCKED= "SELECT id FROM blocked where person_email = ? AND blocked_email = ? UNION SELECT id FROM blocked where blocked_email = ? AND person_email = ?";
	private static final String MAKE_FRIENDS = "INSERT INTO friends_network(person1_email, person2_email) VALUES (?, ?)";
	private static final String GET_FRIENDS = "SELECT person1_email FROM friends_network WHERE person2_email = ? UNION SELECT person2_email FROM friends_network WHERE person1_email = ?";
	private static final String GET_COMMON_FRIENDS = "SELECT person2_email FROM friends_network WHERE person1_email = ? UNION SELECT person1_email FROM friends_network WHERE person2_email = ?";
	private static final String SUBSCRIBE_UPDATES = "INSERT INTO followers_network(followee, follower) VALUES (?, ?)";
	private static final String CHECK_IF_FRIENDS = "SELECT id FROM friends_network WHERE person1_email = ? AND person2_email = ? UNION SELECT id FROM friends_network WHERE person2_email = ? AND person1_email = ?";
	private static final String BLOCK_UPDATES = "DELETE FROM followers_network WHERE followee = ? AND follower = ?";
	private static final String BLOCK_PERSON = "INSERT INTO blocked(person_email, blocked_email) VALUES (?, ?)";
	private static final String GET_BLOCKED_PERSONS = "SELECT person_email FROM blocked WHERE blocked_email = ?";
	private static final String GET_ALL_FRIENDS_FOLLOWERS = "SELECT person1_email FROM friends_network WHERE person2_email = ? UNION SELECT person2_email FROM friends_network WHERE person1_email = ? UNION SELECT follower FROM followers_network WHERE followee = ?";
	
	
	@Autowired
	DataSource dataSource;

	JdbcTemplate jdbcTemplate;

	private void getTemplate() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	@Override
	public int checkIfBlocked(String person1Email, String person2Email) {
		if (jdbcTemplate == null) {
			getTemplate();
		}
		return jdbcTemplate.query(CHECK_IF_BLOCKED, ps -> {
			ps.setString(1, person1Email);
			ps.setString(2, person2Email);
			ps.setString(3, person1Email);
			ps.setString(4, person2Email);
		}, (rs, rowNum) -> rs.getInt(1)).size();
	}
	
	@Override
	public int makeFriends(String person1Email, String person2Email) {
		if (jdbcTemplate == null) {
			getTemplate();
		}
		
		return jdbcTemplate.update(MAKE_FRIENDS, ps -> {
			ps.setString(1, person1Email);
			ps.setString(2, person2Email);
		});
	}

	@Override
	public List<String> getFriends(String email) {
		if (jdbcTemplate == null) {
			getTemplate();
		}
		return jdbcTemplate.query(GET_FRIENDS, ps -> {
			ps.setString(1, email);
			ps.setString(2, email);
		}, (rs, rowNum) -> rs.getString(1));
	}

	@Override
	public List<String> getCommonFriends(String email) {
		if (jdbcTemplate == null) {
			getTemplate();
		}
		return jdbcTemplate.query(GET_COMMON_FRIENDS, ps -> {
			ps.setString(1, email);
			ps.setString(2, email);
		}, (rs, rowNum) -> rs.getString(1));
	}

	@Override
	public int subscribeToUpdates(String followee, String follower) {
		if (jdbcTemplate == null) {
			getTemplate();
		}
		return jdbcTemplate.update(SUBSCRIBE_UPDATES, ps -> {
			ps.setString(1, followee);
			ps.setString(2, follower);
		});
	}

	@Override
	public int blockUpdates(String followee, String follower) {
		if (jdbcTemplate == null) {
			getTemplate();
		}
		return jdbcTemplate.update(BLOCK_UPDATES, ps -> {
			ps.setString(1, followee);
			ps.setString(2, follower);
		});
	}
	
	@Override
	public int blockPerson(String followee, String follower) {
		if (jdbcTemplate == null) {
			getTemplate();
		}
		return jdbcTemplate.update(BLOCK_PERSON, ps -> {
			ps.setString(1, followee);
			ps.setString(2, follower);
		});
	}

	@Override
	public int checkIfFriends(String person1Email, String person2Email) {
		if (jdbcTemplate == null) {
			getTemplate();
		}
		return jdbcTemplate.query(CHECK_IF_FRIENDS, ps -> {
			ps.setString(1, person1Email);
			ps.setString(2, person2Email);
			ps.setString(3, person1Email);
			ps.setString(4, person2Email);
		}, (rs, rowNum) -> rs.getInt(1)).size();
		
	}
	
	@Override
	public List<String> getBlockedPersons(String email) {
		if (jdbcTemplate == null) {
			getTemplate();
		}
		return jdbcTemplate.query(GET_BLOCKED_PERSONS, ps -> {
			ps.setString(1, email);
		}, (rs, rowNum) -> rs.getString(1));
	}
	
	@Override
	public List<String> getAllFriendAndFollowers(String sender) {
		if(jdbcTemplate == null){
			getTemplate();
		}
		return jdbcTemplate.query(GET_ALL_FRIENDS_FOLLOWERS, ps -> {
			ps.setString(1, sender);
			ps.setString(2, sender);
			ps.setString(3, sender);
		}, (rs, rowNum) -> rs.getString(1));
	}






}
