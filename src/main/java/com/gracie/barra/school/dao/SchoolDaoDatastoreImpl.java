package com.gracie.barra.school.dao;

import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.gracie.barra.school.objects.School;

public class SchoolDaoDatastoreImpl implements SchoolDao {

	private static final Logger log = Logger.getLogger(SchoolDaoDatastoreImpl.class.getName());

	private DatastoreService datastore;
	private static final String CC_KIND = "School";

	public SchoolDaoDatastoreImpl() {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public School entityToSchool(Entity entity) {
		return new School.Builder() // Convert to CertificationCriterion form
				.id(entity.getKey().getId()).userId((String) entity.getProperty(School.USERID))
				.name((String) entity.getProperty(School.NAME))
				.contactMail((String) entity.getProperty(School.CONTACTMAIL))
				.description((String) entity.getProperty(School.DESCRIPTION)).build();
	}

	@Override
	public School getSchoolByUser(String userId) {
		log.info("Getting school for user " + userId);
		Query query = new Query(CC_KIND).setFilter(new FilterPredicate(School.USERID, FilterOperator.EQUAL, userId));

		PreparedQuery preparedQuery = datastore.prepare(query);
		Entity entity = preparedQuery.asSingleEntity();
		log.info("Got entity " + entity);

		return entity == null ? null : entityToSchool(entity);
	}

	@Override
	public Long createSchool(School school) {
		Entity entity = new Entity(CC_KIND); // Key will be assigned once
												// written
		entity.setProperty(School.USERID, school.getUserId());
		entity.setProperty(School.NAME, school.getName());
		entity.setProperty(School.CONTACTMAIL, school.getContactMail());
		entity.setProperty(School.DESCRIPTION, school.getDescription());

		Key ccKey = datastore.put(entity); // Save the Entity
		return ccKey.getId(); // The ID of the Key
	}

	@Override
	public void updateSchool(School school) {
		Key key = KeyFactory.createKey(CC_KIND, school.getId()); // From a
																	// certificationCriterion,
																	// create a
																	// Key
		Entity entity = new Entity(key); // Convert CertificationCriterion to an
											// Entity
		entity.setProperty(School.USERID, school.getUserId());
		entity.setProperty(School.NAME, school.getName());
		entity.setProperty(School.CONTACTMAIL, school.getContactMail());
		entity.setProperty(School.DESCRIPTION, school.getDescription());

		datastore.put(entity); // Update the Entity
		log.info("Updated entity " + entity);

	}

}
