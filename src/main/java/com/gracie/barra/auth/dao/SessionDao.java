package com.gracie.barra.auth.dao;

public interface SessionDao {

	public String getToken(String sessionId);

	public void storeToken(String sessionId, String token);

}
