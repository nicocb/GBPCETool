package com.gracie.barra.school.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.gracie.barra.admin.dao.CertificationDao;
import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;
import com.gracie.barra.school.objects.School;
import com.gracie.barra.school.objects.School.Belt;
import com.gracie.barra.school.objects.School.SchoolStatus;
import com.gracie.barra.school.objects.SchoolsByRank;
import com.gracie.barra.school.objects.ScoredSchool;

public class SchoolDaoDatastoreImpl implements SchoolDao {

	private static final Logger log = Logger.getLogger(SchoolDaoDatastoreImpl.class.getName());

	CertificationDao certificationDao;

	private DatastoreService datastore;
	private static final String SCHOOL_KIND = "School";

	public SchoolDaoDatastoreImpl(CertificationDao certificationDao) {
		datastore = DatastoreServiceFactory.getDatastoreService();
		this.certificationDao = certificationDao;
	}

	private School entityToSchool(Entity entity) {
		Long belt = (Long) entity.getProperty(School.INSTRUCTOR_BELT);
		Long status = (Long) entity.getProperty(School.STATUS);
		Long agreementStatus = (Long) entity.getProperty(School.AGREEMENT_STATUS);
		Long initialFeeStatus = (Long) entity.getProperty(School.INITIAL_FEE_STATUS);
		Long monthlyFeeStatus = (Long) entity.getProperty(School.MONTHLY_FEE_STATUS);

		return new School.Builder() // Convert to CertificationCriterion form
				.id(entity.getKey().getId()).userId((String) entity.getProperty(School.USERID))
				.contactMail((String) entity.getProperty(School.CONTACT_MAIL))
				.contactName((String) entity.getProperty(School.CONTACT_NAME))
				.contactPhone((String) entity.getProperty(School.CONTACT_PHONE))
				.instructorBelt(belt == null ? null : Belt.values()[belt.intValue()])
				.instructorName((String) entity.getProperty(School.INSTRUCTOR_NAME))
				.instructorProfessor((String) entity.getProperty(School.INSTRUCTOR_PROFESSOR))
				.schoolAddress((String) entity.getProperty(School.SCHOOL_ADDRESS))
				.schoolCountry((String) entity.getProperty(School.SCHOOL_COUNTRY))
				.schoolZip((String) entity.getProperty(School.SCHOOL_ZIP))
				.schoolCity((String) entity.getProperty(School.SCHOOL_CITY))
				.schoolMail((String) entity.getProperty(School.SCHOOL_MAIL))
				.schoolName((String) entity.getProperty(School.SCHOOL_NAME))
				.schoolPhone((String) entity.getProperty(School.SCHOOL_PHONE))
				.schoolWeb((String) entity.getProperty(School.SCHOOL_WEB))
				.status(status == null ? null : SchoolStatus.values()[status.intValue()])
				.agreementStatus(agreementStatus == null ? null : SchoolStatus.values()[agreementStatus.intValue()])
				.initialFeeStatus(initialFeeStatus == null ? null : SchoolStatus.values()[initialFeeStatus.intValue()])
				.monthlyFeeStatus(monthlyFeeStatus == null ? null : SchoolStatus.values()[monthlyFeeStatus.intValue()])
				.dateCreated((Date) entity.getProperty(School.DATE_CREATED))
				.dateValidated((Date) entity.getProperty(School.DATE_VALIDATED)).build();
	}

	private void schoolToEntity(School school, Entity entity) {
		entity.setProperty(School.USERID, school.getUserId());
		entity.setProperty(School.CONTACT_MAIL, school.getContactMail());
		entity.setProperty(School.CONTACT_NAME, school.getContactName());
		entity.setProperty(School.CONTACT_PHONE, school.getContactPhone());
		entity.setProperty(School.INSTRUCTOR_BELT, school.getInstructorBelt().ordinal());
		entity.setProperty(School.INSTRUCTOR_NAME, school.getInstructorName());
		entity.setProperty(School.INSTRUCTOR_PROFESSOR, school.getInstructorProfessor());
		entity.setProperty(School.SCHOOL_NAME, school.getSchoolName());
		entity.setProperty(School.SCHOOL_ADDRESS, school.getSchoolAddress());
		entity.setProperty(School.SCHOOL_COUNTRY, school.getSchoolCountry());
		entity.setProperty(School.SCHOOL_CITY, school.getSchoolCity());
		entity.setProperty(School.SCHOOL_ZIP, school.getSchoolZip());
		entity.setProperty(School.SCHOOL_MAIL, school.getSchoolMail());
		entity.setProperty(School.SCHOOL_PHONE, school.getSchoolPhone());
		entity.setProperty(School.SCHOOL_WEB, school.getSchoolWeb());
		entity.setProperty(School.AGREEMENT_STATUS, school.getAgreementStatus().ordinal());
		entity.setProperty(School.INITIAL_FEE_STATUS, school.getInitialFeeStatus().ordinal());
		entity.setProperty(School.MONTHLY_FEE_STATUS, school.getMonthlyFeeStatus().ordinal());

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
		schoolToEntity(school, entity);
		entity.setProperty(School.STATUS, SchoolStatus.PENDING.ordinal());
		entity.setProperty(School.DATE_CREATED, new Date());

		Key ccKey = datastore.put(entity); // Save the Entity
		return ccKey.getId(); // The ID of the Key
	}

	@Override
	public void updateSchool(School school) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(SCHOOL_KIND, school.getId());
		Entity entity = datastore.get(key);
		// Entity
		schoolToEntity(school, entity);

		datastore.put(entity); // Update the Entity

	}

	@Override
	public void updateSchoolStatus(Long id, SchoolStatus pending) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(SCHOOL_KIND, id);
		Entity entity = datastore.get(key);
		entity.setProperty(School.STATUS, pending.ordinal());
		entity.setProperty(School.DATE_VALIDATED, new Date());

		datastore.put(entity); // Update the Entity
	}

	@Override
	public void updateSchoolInitialFeeStatus(Long id, SchoolStatus pending) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(SCHOOL_KIND, id);
		Entity entity = datastore.get(key);
		entity.setProperty(School.INITIAL_FEE_STATUS, pending.ordinal());

		datastore.put(entity); // Update the Entity
	}

	@Override
	public void updateSchoolMonthlyFeeStatus(Long id, SchoolStatus pending) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(SCHOOL_KIND, id);
		Entity entity = datastore.get(key);
		entity.setProperty(School.MONTHLY_FEE_STATUS, pending.ordinal());

		datastore.put(entity); // Update the Entity
	}

	@Override
	public void updateSchoolAgreementStatus(Long id, SchoolStatus validated) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(SCHOOL_KIND, id);
		Entity entity = datastore.get(key);
		entity.setProperty(School.AGREEMENT_STATUS, validated.ordinal());

		datastore.put(entity); // Update the Entity

	}

	@Override
	public List<School> listSchools() {
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(100);

		Query query = new Query(SCHOOL_KIND).addSort(School.STATUS, SortDirection.ASCENDING).addSort(School.SCHOOL_NAME,
				SortDirection.ASCENDING);
		PreparedQuery preparedQuery = datastore.prepare(query);
		QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

		List<School> result = entitiesToSchools(results);
		return result;
	}

	@Override
	public List<SchoolsByRank> listSchoolsByRank() {
		List<School> schools = listSchools();
		List<SchoolsByRank> schoolsBR = new ArrayList<>();
		Map<CertificationCriterionRank, SchoolsByRank> ranks = new HashMap<>();

		for (School school : schools) {
			ScoredSchool sSchool = certificationDao.scoreSchool(school);
			SchoolsByRank current = ranks.get(sSchool.getRank());
			if (current == null) {
				current = new SchoolsByRank();
				current.setRank(sSchool.getRank());
				ranks.put(sSchool.getRank(), current);
			}
			current.getSchools().add(sSchool);
		}
		for (CertificationCriterionRank rank : CertificationCriterionRank.values()) {
			if (ranks.get(rank) != null) {
				schoolsBR.add(ranks.get(rank));
			}
		}
		return schoolsBR;
	}

	@Override
	public void deleteSchool(Long id) {
		Key key = KeyFactory.createKey(SCHOOL_KIND, id);

		datastore.delete(key); // Update the Entity
		log.info("Deleted entity " + key);
	}

}
