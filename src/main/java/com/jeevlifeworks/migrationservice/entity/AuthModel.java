package com.jeevlifeworks.migrationservice.entity;

import java.util.List;

public class AuthModel {

	String responseStatus;
	String sessionId;
	int userId;
	List vaultIds;
	int vaultId;
	@Override
	public String toString() {
		return "AuthModel [responseStatus=" + responseStatus + ", sessionId=" + sessionId + ", userId=" + userId
				+ ", vaultIds=" + vaultIds + ", vaultId=" + vaultId + "]";
	}
	public List getVaultIds() {
		return vaultIds;
	}
	public void setVaultIds(List vaultIds) {
		this.vaultIds = vaultIds;
	}
	public int getVaultId() {
		return vaultId;
	}
	public void setVaultId(int vaultId) {
		this.vaultId = vaultId;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
