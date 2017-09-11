package com.management.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.service.ManagementService;
import com.management.vo.FriendListVO;
import com.management.vo.RecipientVO;
import com.management.vo.StatusVO;

@RestController
public class ManagementController {

	@Autowired
	private ManagementService managementService;

	/**
	 * 
	 * @param httpEntity
	 * @return StatusVO
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/makeFriends", method = RequestMethod.POST, consumes = "application/json")
	public StatusVO makeFriends(HttpEntity<String> httpEntity)
			throws JsonParseException, JsonMappingException, IOException {
		String json = httpEntity.getBody();
		json = json.substring(json.indexOf("'")+1,json.lastIndexOf("'"));
		json=json.replace(" ", "");
		json=json.replace("'", "");
		String[] arr = json.split(",");
		StatusVO statusObj = new StatusVO();
		if (arr.length == 2) {
			arr[1] = arr[1].replaceAll("(?m)^[ \t]*\r?\n", "");
			if (!arr[0].equals(arr[1]) && !(arr[0].equals("") || arr[1].equals("") || arr[0].equals(arr[1]))) {
				int val = managementService.makeFriends(arr[0], arr[1]);
				if (val == 1) {
					statusObj.setSuccess(true);
				} else {
					statusObj.setSuccess(false);
				}
			}
		}
		return statusObj;
	}

	/**
	 * 
	 * @param httpEntity
	 * @return FriendListVO
	 */
	@RequestMapping(value = "/getFriends", method = RequestMethod.POST, consumes = "application/json")
	public FriendListVO getFriends(HttpEntity<String> httpEntity) {
		String json = httpEntity.getBody();
		String email = json.substring(json.indexOf("'")+1,json.lastIndexOf("'"));
		FriendListVO friendListObj = new FriendListVO();
		if (email != null && !email.equals("")) {
			List<String> friends = managementService.getFriends(email);
			friendListObj.setFriends(friends);
			friendListObj.setSuccess(true);
			friendListObj.setCount(friends.size());
		}
		return friendListObj;
	}
	
	/**
	 * 
	 * @param httpEntity
	 * @return FriendListVO
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getCommonFriends", method = RequestMethod.POST, consumes = "application/json")
	public FriendListVO getCommonFriends(HttpEntity<String> httpEntity)
			throws JsonParseException, JsonMappingException, IOException {
		String json = httpEntity.getBody();
		json = json.substring(json.indexOf("'")+1,json.lastIndexOf("'"));
		json=json.replace(" ", "");
		json=json.replace("'", "");
		String[] arr = json.split(",");
		FriendListVO friendListObj = new FriendListVO();
		if (arr.length == 2) {
			arr[1] = arr[1].replaceAll("(?m)^[ \t]*\r?\n", "");
			if(!arr[0].equals(arr[1]) && !(arr[0].equals("") || arr[1].equals("") || arr[0].equals(arr[1]))){
				List<String> friends = managementService.getCommonFriends(arr[0],arr[1]);
				friendListObj.setFriends(friends);
				friendListObj.setSuccess(true);
				friendListObj.setCount(friends.size());
			}
		}
		return friendListObj;
	}
	
	/**
	 * 
	 * @param httpEntity
	 * @return StatusVO
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/subscribeToUpdates", method = RequestMethod.POST, consumes = "application/json")
	public StatusVO subscribeToUpdates(HttpEntity<String> httpEntity)
			throws JsonParseException, JsonMappingException, IOException {
		String json = httpEntity.getBody();
		HashMap<String, String> result = new ObjectMapper().readValue(json,
				HashMap.class);
		String follower = result.get("requestor");
		String followee = result.get("target");
		StatusVO statusObj = new StatusVO();
		if (follower != null && !follower.equals("") && followee != null
				&& !followee.equals("") && !follower.equals(followee)) {
			int val = managementService.subscribeToUpdates(followee, follower);
			if (val == 1) {
				statusObj.setSuccess(true);
			} else {
				statusObj.setSuccess(false);
			}
		}
		return statusObj;
	}
	
	/**
	 * 
	 * @param httpEntity
	 * @return StatusVO
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/blockUpdates", method = RequestMethod.POST, consumes = "application/json")
	public StatusVO blockUpdates(HttpEntity<String> httpEntity)
			throws JsonParseException, JsonMappingException, IOException {
		String json = httpEntity.getBody();
		HashMap<String, String> result = new ObjectMapper().readValue(json,
				HashMap.class);
		String follower = result.get("requestor");
		String followee = result.get("target");
		StatusVO statusObj = new StatusVO();
		if (follower != null && !follower.equals("") && followee != null
				&& !followee.equals("") && !follower.equals(followee)) {
			int val = managementService.blockUpdates(followee, follower);
			if (val == 1) {
				statusObj.setSuccess(true);
			} else {
				statusObj.setSuccess(false);
			}
		}
		return statusObj;
	}
	/**
	 * 
	 * @param httpEntity
	 * @return RecipientVO
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updatesReceiving", method = RequestMethod.POST, consumes = "application/json")
	public RecipientVO updatesReceiving(HttpEntity<String> httpEntity)
			throws JsonParseException, JsonMappingException, IOException {
		String json = httpEntity.getBody();
		HashMap<String, String> result = new ObjectMapper().readValue(json,
				HashMap.class);
		String sender = result.get("sender");
		String text = result.get("text");
		RecipientVO recipientObj = new RecipientVO();
		if (sender != null && !sender.equals("")) {
			List<String> recipients = managementService.updatesReceiving(sender, text);
			recipientObj.setRecipients(recipients);
			recipientObj.setSuccess(true);
		}
		return recipientObj;
	}

}
