package com.management.service;

import java.util.List;

public interface ManagementService {
	
	int makeFriends(String person1Email, String person2Email);

	List<String> getFriends(String email);

	List<String> getCommonFriends(String person1Email, String person2Email);

	int subscribeToUpdates(String followee, String follower);

	int blockUpdates(String followee, String follower);

	List<String> updatesReceiving(String sender, String text);

}
