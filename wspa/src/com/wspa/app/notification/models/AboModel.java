package com.wspa.app.notification.models;

public class AboModel {	
	private String email;
	private String optin;
	private String action_outcome;
	private String error;
	private String reason;
	
	public AboModel() {
	}

	public AboModel(String email, String optin, String action_outcome, String error, String reason) {
		this.email = email;
		this.optin = optin;
		this.action_outcome = action_outcome;
		this.error = error;
		this.reason = reason;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOptin(String optin) {
		this.optin = optin;
	}

	public void setActionOutcome(String action_outcome) {
		this.action_outcome = action_outcome;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public void setError(String error) {
		this.error = error;
	}

	public String getEmail() {
		return email;
	}

	public String getOptin() {
		return optin;
	}

	public String getActionOutcome() {
		return action_outcome;
	}

	public String getError() {
		return error;
	}
	
	public String getReason() {
		return reason;
	}
}
