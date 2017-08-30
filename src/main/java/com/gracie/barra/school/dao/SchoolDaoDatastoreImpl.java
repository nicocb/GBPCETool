package com.gracie.barra.school.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.gracie.barra.school.objects.School;

public class SchoolDaoDatastoreImpl implements SchoolDao {

	private static final Logger log = Logger.getLogger(SchoolDaoDatastoreImpl.class.getName());

	private DatastoreService datastore;
	private static final String SCHOOL_KIND = "School";

	public SchoolDaoDatastoreImpl() {
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public School entityToSchool(Entity entity) {
		return new School.Builder() // Convert to CertificationCriterion form
				.id(entity.getKey().getId()).userId((String) entity.getProperty(School.USERID))
				.name((String) entity.getProperty(School.NAME)).contactMail((String) entity.getProperty(School.CONTACTMAIL))
				.description((String) entity.getProperty(School.DESCRIPTION))
				.pending((Boolean) entity.getProperty(School.PENDING)).build();
	}

	public List<School> entitiesToSchools(Iterator<Entity> results) {
		List<School> resultCertificationCriterions = new ArrayList<>();
		while (results.hasNext()) { // We still have data
			// Add the CertificationCriterion to the List
			resultCertificationCriterions.add(entityToSchool(results.next()));
		}
		return resultCertificationCriterions;
	}

	@Override
	public School getSchoolByUser(String userId) {
		Query query = new Query(SCHOOL_KIND).setFilter(new FilterPredicate(School.USERID, FilterOperator.EQUAL, userId));

		PreparedQuery preparedQuery = datastore.prepare(query);
		Entity entity = preparedQuery.asSingleEntity();

		return entity == null ? null : entityToSchool(entity);
	}

	@Override
	public School getSchool(Long id) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(SCHOOL_KIND, id);
		Entity entity = datastore.get(key);
		return entity == null ? null : entityToSchool(entity);
	}

	@Override
	public Long createSchool(School school) {
		Entity entity = new Entity(SCHOOL_KIND); // Key will be assigned once
													// written
		entity.setProperty(School.USERID, school.getUserId());
		entity.setProperty(School.NAME, school.getName());
		entity.setProperty(School.CONTACTMAIL, school.getContactMail());
		entity.setProperty(School.DESCRIPTION, school.getDescription());
		entity.setProperty(School.PENDING, school.getPending());

		Key ccKey = datastore.put(entity); // Save the Entity
		return ccKey.getId(); // The ID of the Key
	}

	@Override
	public void updateSchool(School school) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(SCHOOL_KIND, school.getId());
		Entity entity = datastore.get(key);
		// Entity
		entity.setProperty(School.USERID, school.getUserId());
		entity.setProperty(School.NAME, school.getName());
		entity.setProperty(School.CONTACTMAIL, school.getContactMail());
		entity.setProperty(School.DESCRIPTION, school.getDescription());

		datastore.put(entity); // Update the Entity
		log.info("Updated entity " + entity);

	}

	@Override
	public void updateSchoolStatus(Long id, Boolean pending) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(SCHOOL_KIND, id);
		Entity entity = datastore.get(key);
		entity.setProperty(School.PENDING, pending);

		datastore.put(entity); // Update the Entity
		log.info("Updated entity " + entity);

	}

	@Override
	public List<School> listSchools() {
		// Only show 10 at a time
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(1000);

		Query query = new Query(SCHOOL_KIND).addSort(School.PENDING, SortDirection.ASCENDING).addSort(School.NAME,
				SortDirection.ASCENDING);
		PreparedQuery preparedQuery = datastore.prepare(query);
		QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

		List<School> result = entitiesToSchools(results);
		return result;
	}

	@Override
	public void deleteSchool(Long id) {
		Key key = KeyFactory.createKey(SCHOOL_KIND, id);

		datastore.delete(key); // Update the Entity
		log.info("Deleted entity " + key);
	}

}
