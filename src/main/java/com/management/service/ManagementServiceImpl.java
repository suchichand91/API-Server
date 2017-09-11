package com.management.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.dao.ManagementDao;

@Service
public class ManagementServiceImpl implements ManagementService {
	
	@Autowired
	private ManagementDao managementDao;
	
	@Override
	public int makeFriends(String person1Email, String person2Email) {
		if (managementDao.checkIfBlocked(person1Email, person2Email) > 0
				|| managementDao.checkIfFriends(person1Email, person2Email) > 0) {
			return 0;
		} else {
			return managementDao.makeFriends(person1Email, person2Email);
		}
	}

	@Override
	public List<String> getFriends(String email) {
		return managementDao.getFriends(email);
	}

	@Override
	public List<String> getCommonFriends(String person1Email,
			String person2Email) {
		List<String> list1 = managementDao.getCommonFriends(person1Email);
		List<String> list2 = managementDao.getCommonFriends(person2Email);
		List<String> finalList = new ArrayList<>();
		for (String string1 : list1) {
			if (list2.contains(string1)) {
				finalList.add(string1);
			}
		}
		return finalList;
	}

	@Override
	public int subscribeToUpdates(String followee, String follower) {
		return managementDao.subscribeToUpdates(followee, follower);
	}

	@Override
	public int blockUpdates(String followee, String follower) {
		int val = managementDao.blockUpdates(followee, follower);
		if(managementDao.checkIfFriends(followee, follower) == 0){
			val =  managementDao.blockPerson(followee, follower);
		}
		return val;
	}

	@Override
	public List<String> updatesReceiving(String sender, String text) {
		List<String> blockedPersons = managementDao.getBlockedPersons(sender);
		List<String> allFriendsFollowers = managementDao.getAllFriendAndFollowers(sender);
		
		List<String> returnList = new ArrayList<>();
		for(String email : allFriendsFollowers){
			if(!blockedPersons.contains(email)){
				returnList.add(email);
			}
		}
		if(text.contains("@")){
			int beginIndex = text.indexOf("@");
			int endIndex = text.indexOf(".com")+4;
			while(beginIndex>=0 && text.charAt(beginIndex) != ' '){
				beginIndex--;
			}
			text = text.substring(beginIndex+1,endIndex);
			if(!returnList.contains(text)){
				returnList.add(text);
			}
		}
		
		return returnList;
	}
 
}
