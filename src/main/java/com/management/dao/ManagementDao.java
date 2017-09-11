package com.management.dao;

import java.util.List;

public interface ManagementDao {
	
	int checkIfBlocked(String person1Email, String person2Email);
	
	int makeFriends(String person1Email, String person2Email);

	List<String> getFriends(String email);

	List<String> getCommonFriends(String email);

	int subscribeToUpdates(String followee, String follower);
	
	int checkIfFriends(String person1Email, String person2Email);
			
	int blockPerson(String followee, String follower);

	int blockUpdates(String followee, String follower);
	
	List<String> getBlockedPersons(String email);

	List<String> getAllFriendAndFollowers(String sender);

}
