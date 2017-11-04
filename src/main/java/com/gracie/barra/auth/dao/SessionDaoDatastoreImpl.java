package com.gracie.barra.auth.dao;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class SessionDaoDatastoreImpl implements SessionDao {

	private DatastoreService datastore;
	private static final String SESSION_KIND = "Session";

	public SessionDaoDatastoreImpl() {
		datastore = DatastoreServiceFactory.getDatastoreService();
		// TODO remove older ones
	}

	@Override
	public String getToken(String sessionId) {
		Query query = new Query(SESSION_KIND).setFilter(new FilterPredicate("sessionId", FilterOperator.EQUAL, sessionId));

		PreparedQuery preparedQuery = datastore.prepare(query);
		Entity entity = preparedQuery.asSingleEntity();

		return (String) (entity == null ? null : entity.getProperty("token"));
	}

	@Override
	public void storeToken(String sessionId, String token) {
		Query query = new Query(SESSION_KIND).setFilter(new FilterPredicate("sessionId", FilterOperator.EQUAL, sessionId));

		PreparedQuery preparedQuery = datastore.prepare(query);
		Entity entity = preparedQuery.asSingleEntity();

		if (entity == null) {
			entity = new Entity(SESSION_KIND); // Key will be assigned once
			entity.setProperty("created", new Date());
			entity.setProperty("sessionId", sessionId);
		}
		// written
		entity.setProperty("token", token);
		datastore.put(entity); // Sav

	}

}
