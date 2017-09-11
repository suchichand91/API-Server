package com.management.vo;

import java.util.ArrayList;
import java.util.List;

public class RecipientVO {
	

    private boolean success = true;
    
    private List<String> recipients = new ArrayList<>();

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

}
