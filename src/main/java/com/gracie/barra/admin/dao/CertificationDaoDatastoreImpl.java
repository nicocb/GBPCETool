/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gracie.barra.admin.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.gracie.barra.admin.objects.CertificationCriteriaByRank;
import com.gracie.barra.admin.objects.CertificationCriterion;
import com.gracie.barra.admin.objects.SchoolCertificationCriteriaByRank;
import com.gracie.barra.admin.objects.SchoolCertificationCriterion;
import com.gracie.barra.admin.objects.SchoolCertificationDashboard;

public class CertificationDaoDatastoreImpl implements CertificationDao {

	private DatastoreService datastore;
	private static final String CC_KIND = "CertificationCriterion";
	private static final String SCC_KIND = "SchoolsCertificationCriterion";

	public CertificationDaoDatastoreImpl() {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized
																	// Datastore
																	// service
	}

	public CertificationCriterion entityToCertificationCriterion(Entity entity) {
		return new CertificationCriterion.Builder() // Convert to
													// CertificationCriterion
													// form
				.id(entity.getKey().getId()).description((String) entity.getProperty(CertificationCriterion.DESCRIPTION))
				.comment((String) entity.getProperty(CertificationCriterion.COMMENT))
				.action((String) entity.getProperty(CertificationCriterion.ACTION))
				.rank((Long) entity.getProperty(CertificationCriterion.RANK))
				.score((Long) entity.getProperty(CertificationCriterion.SCORE))

				.build();
	}

	private SchoolCertificationCriterion entityToSchoolCertificationCriterion(Entity entity, CertificationCriterion cc) {
		return entity == null ? new SchoolCertificationCriterion.Builder().criterion(cc).build()
				: new SchoolCertificationCriterion.Builder().id(entity.getKey().getId()).criterion(cc)
						.comment((String) entity.getProperty(SchoolCertificationCriterion.COMMENT))
						.picture((String) entity.getProperty(SchoolCertificationCriterion.PICTURE))
						.pending((Boolean) entity.getProperty(SchoolCertificationCriterion.PENDING)).build();
	}

	@Override
	public Long createCertificationCriterion(CertificationCriterion cc) {
		Entity ccEntity = new Entity(CC_KIND); // Key will be assigned once
												// written
		ccEntity.setProperty(CertificationCriterion.DESCRIPTION, cc.getDescription());
		ccEntity.setProperty(CertificationCriterion.COMMENT, cc.getComment());
		ccEntity.setProperty(CertificationCriterion.ACTION, cc.getAction());
		ccEntity.setProperty(CertificationCriterion.RANK, cc.getRank());
		ccEntity.setProperty(CertificationCriterion.SCORE, cc.getScore());

		Key ccKey = datastore.put(ccEntity); // Save the Entity
		return ccKey.getId(); // The ID of the Key
	}

	@Override
	public CertificationCriterion readCertificationCriterion(Long ccId) {
		try {
			Entity ccEntity = datastore.get(KeyFactory.createKey(CC_KIND, ccId));
			return entityToCertificationCriterion(ccEntity);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	@Override
	public SchoolCertificationCriterion readSchoolCertificationCriterion(CertificationCriterion cc, Long schoolId) {
		Entity entity = null;
		Collection<Filter> filters = new ArrayList<>();
		filters.add(new FilterPredicate(SchoolCertificationCriterion.SCHOOL_ID, FilterOperator.EQUAL, schoolId));
		filters.add(new FilterPredicate(SchoolCertificationCriterion.CRITERION_ID, FilterOperator.EQUAL, cc.getId()));
		Query query = new Query(SCC_KIND).setFilter(new CompositeFilter(CompositeFilterOperator.AND, filters));

		PreparedQuery preparedQuery = datastore.prepare(query);
		entity = preparedQuery.asSingleEntity();
		return entityToSchoolCertificationCriterion(entity, cc);
	}

	@Override
	public void updateCertificationCriterion(CertificationCriterion certificationCriterion) {
		Key key = KeyFactory.createKey(CC_KIND, certificationCriterion.getId());
		Entity entity = new Entity(key);
		entity.setProperty(CertificationCriterion.DESCRIPTION, certificationCriterion.getDescription());
		entity.setProperty(CertificationCriterion.COMMENT, certificationCriterion.getComment());
		entity.setProperty(CertificationCriterion.ACTION, certificationCriterion.getAction());
		entity.setProperty(CertificationCriterion.RANK, certificationCriterion.getRank());
		entity.setProperty(CertificationCriterion.SCORE, certificationCriterion.getScore());

		datastore.put(entity); // Update the Entity
	}

	@Override
	public void deleteCertificationCriterion(Long certificationCriterionId) {
		Key key = KeyFactory.createKey(CC_KIND, certificationCriterionId); // Create
																			// the
																			// Key
		datastore.delete(key); // Delete the Entity
	}

	public List<CertificationCriterion> entitiesToCertificationCriteria(Iterator<Entity> results) {
		List<CertificationCriterion> resultCertificationCriteria = new ArrayList<>();
		while (results.hasNext()) { // We still have data
			// Add the CertificationCriterion to the List
			resultCertificationCriteria.add(entityToCertificationCriterion(results.next()));
		}
		return resultCertificationCriteria;
	}

	@Override
	public List<CertificationCriterion> listCertificationCriteria() {
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(1000);
		// We only care about CertificationCriteria
		// Use default Index "title"
		Query query = new Query(CC_KIND).addSort(CertificationCriterion.RANK, SortDirection.ASCENDING)
				.addSort(CertificationCriterion.SCORE, SortDirection.DESCENDING)
				.addSort(CertificationCriterion.DESCRIPTION, SortDirection.ASCENDING);
		PreparedQuery preparedQuery = datastore.prepare(query);
		QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

		List<CertificationCriterion> resultCertificationCriteria = entitiesToCertificationCriteria(results);
		return resultCertificationCriteria;
	}

	@Override
	public List<CertificationCriteriaByRank> listCertificationCriteriaByRank() {
		List<CertificationCriteriaByRank> result = new ArrayList<>();
		Long currentRank = -1L;
		CertificationCriteriaByRank currentRankList = null;
		List<CertificationCriterion> criteriaList = listCertificationCriteria();

		for (CertificationCriterion certificationCriterion : criteriaList) {
			if (!certificationCriterion.getRank().equals(currentRank)) {
				currentRankList = new CertificationCriteriaByRank();
				currentRank = certificationCriterion.getRank();
				currentRankList.setRank(currentRank);
				result.add(currentRankList);
			}
			currentRankList.getCriteria().add(certificationCriterion);
			currentRankList.incScore(certificationCriterion.getScore());
		}
		return result;
	}

	@Override
	public List<SchoolCertificationCriterion> listSchoolCertificationCriteria(Long schoolId) {
		// Only show 10 at a time
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(1000);
		Query query = new Query(CC_KIND).addSort(CertificationCriterion.RANK, SortDirection.ASCENDING)
				.addSort(CertificationCriterion.SCORE, SortDirection.DESCENDING)
				.addSort(CertificationCriterion.DESCRIPTION, SortDirection.ASCENDING);
		PreparedQuery preparedQuery = datastore.prepare(query);
		QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

		List<CertificationCriterion> resultCertificationCriteria = entitiesToCertificationCriteria(results);
		// for each one get schooEnrichment
		List<SchoolCertificationCriterion> resultSCC = new ArrayList<>();
		for (CertificationCriterion certificationCriterion : resultCertificationCriteria) {
			resultSCC.add(readSchoolCertificationCriterion(certificationCriterion, schoolId));

		}
		return resultSCC;
	}

	@Override
	public List<SchoolCertificationCriteriaByRank> listSchoolCertificationCriteriaByRank(Long schoolId) {
		List<SchoolCertificationCriteriaByRank> result = new ArrayList<>();
		Long currentRank = -1L;
		SchoolCertificationCriteriaByRank currentRankList = null;
		List<SchoolCertificationCriterion> criteriaList = listSchoolCertificationCriteria(schoolId);

		for (SchoolCertificationCriterion certificationCriterion : criteriaList) {
			if (!certificationCriterion.getCriterion().getRank().equals(currentRank)) {
				currentRankList = new SchoolCertificationCriteriaByRank();
				currentRank = certificationCriterion.getCriterion().getRank();
				currentRankList.setRank(currentRank);
				result.add(currentRankList);
				currentRankList.setAvailable(result.size() == 1 || result.get(result.size() - 2).topScore());
			}
			currentRankList.getCriteria().add(certificationCriterion);
			currentRankList.incScore(certificationCriterion.getCriterion().getScore());
			if (certificationCriterion.getPending() != null && !certificationCriterion.getPending()) {
				currentRankList.incActualScore(certificationCriterion.getCriterion().getScore());
			}
		}
		return result;
	}

	@Override
	public SchoolCertificationDashboard getSchoolCertificationDashboard(Long schoolId) {
		SchoolCertificationDashboard result = null;
		List<SchoolCertificationCriteriaByRank> criteria = listSchoolCertificationCriteriaByRank(schoolId);

		if (criteria != null && criteria.size() > 0) {
			result = new SchoolCertificationDashboard();
			result.setCriteria(criteria);
			for (SchoolCertificationCriteriaByRank schoolCriteriaByRank : result.getCriteria()) {
				result.setOverallScore(result.getOverallScore() + schoolCriteriaByRank.getScore());

				for (SchoolCertificationCriterion criterion : schoolCriteriaByRank.getCriteria()) {

					if (criterion.getPending() == null) {
						result.incNbMissing();
					} else if (criterion.getPending()) {
						result.incNbPending();
					}
				}

			}
		}
		return result;
	}

	@Override
	public SchoolCertificationCriterion updateSchoolCertificationCriterion(Long criterionId, Long schoolId, String picture,
			String comment, Boolean pending) {
		Entity entity = null;
		Collection<Filter> filters = new ArrayList<>();

		filters.add(new FilterPredicate(SchoolCertificationCriterion.SCHOOL_ID, FilterOperator.EQUAL, schoolId));
		filters.add(new FilterPredicate(SchoolCertificationCriterion.CRITERION_ID, FilterOperator.EQUAL, criterionId));
		Query query = new Query(SCC_KIND).setFilter(new CompositeFilter(CompositeFilterOperator.AND, filters));

		PreparedQuery preparedQuery = datastore.prepare(query);
		entity = preparedQuery.asSingleEntity();

		if (entity == null) {
			entity = new Entity(SCC_KIND);
			entity.setProperty(SchoolCertificationCriterion.SCHOOL_ID, schoolId);
			entity.setProperty(SchoolCertificationCriterion.CRITERION_ID, criterionId);
		}

		entity.setProperty(SchoolCertificationCriterion.PENDING, pending);
		if (picture != null) {
			entity.setProperty(SchoolCertificationCriterion.PICTURE, picture);
		}
		if (comment != null) {
			entity.setProperty(SchoolCertificationCriterion.COMMENT, comment);
		}

		datastore.put(entity); // Update the Entity

		return entityToSchoolCertificationCriterion(entity, readCertificationCriterion(criterionId));
	}
}
