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
import java.util.Date;
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
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.gracie.barra.admin.objects.SchoolEvent;
import com.gracie.barra.admin.objects.SchoolEvent.SchoolEventStatus;

public class SchoolEventDaoDatastoreImpl implements SchoolEventDao {

	private DatastoreService datastore;
	private static final String SE_KIND = "SchoolEvent";

	public SchoolEventDaoDatastoreImpl() {
		datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized
																	// Datastore
																	// service
	}

	public SchoolEvent entityToSchoolEvent(Entity entity) {
		return new SchoolEvent.Builder() // Convert to
											// SchoolEvent
											// form
				.id(entity.getKey().getId()).date((Date) entity.getProperty(SchoolEvent.DATE))
				.description((String) entity.getProperty(SchoolEvent.DESCRIPTION))
				.object((String) entity.getProperty(SchoolEvent.OBJECT))
				.objectId((Long) entity.getProperty(SchoolEvent.OBJECT_ID))
				.schoolId((Long) entity.getProperty(SchoolEvent.SCHOOL_ID))
				.status(SchoolEventStatus.values()[((Long) entity.getProperty(SchoolEvent.STATUS)).intValue()]).build();
	}

	@Override
	public Long createSchoolEvent(SchoolEvent cc) {
		Entity seEntity = new Entity(SE_KIND);
		seEntity.setProperty(SchoolEvent.DATE, new Date());
		seEntity.setProperty(SchoolEvent.DESCRIPTION, cc.getDescription());
		seEntity.setProperty(SchoolEvent.OBJECT, cc.getObject());
		seEntity.setProperty(SchoolEvent.OBJECT_ID, cc.getObjectId());
		seEntity.setProperty(SchoolEvent.SCHOOL_ID, cc.getSchoolId());
		seEntity.setProperty(SchoolEvent.STATUS, cc.getStatus().ordinal());

		Key ccKey = datastore.put(seEntity); // Save the Entity
		return ccKey.getId(); // The ID of the Key
	}

	@Override
	public SchoolEvent readSchoolEvent(Long seId) {
		try {
			Entity seEntity = datastore.get(KeyFactory.createKey(SE_KIND, seId));
			return entityToSchoolEvent(seEntity);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	@Override
	public void updateSchoolEvent(SchoolEvent schoolEvent) {
		Key key = KeyFactory.createKey(SE_KIND, schoolEvent.getId());
		Entity seEntity = new Entity(key);
		seEntity.setProperty(SchoolEvent.DATE, schoolEvent.getDate());
		seEntity.setProperty(SchoolEvent.DESCRIPTION, schoolEvent.getDescription());
		seEntity.setProperty(SchoolEvent.OBJECT, schoolEvent.getObject());
		seEntity.setProperty(SchoolEvent.OBJECT_ID, schoolEvent.getObjectId());
		seEntity.setProperty(SchoolEvent.SCHOOL_ID, schoolEvent.getSchoolId());
		seEntity.setProperty(SchoolEvent.STATUS, schoolEvent.getStatus().ordinal());

		datastore.put(seEntity); // Update the Entity
	}

	@Override
	public void deleteSchoolEvent(Long schoolEvent) {
		Key key = KeyFactory.createKey(SE_KIND, schoolEvent); // Create
																// the
																// Key
		datastore.delete(key); // Delete the Entity
	}

	public List<SchoolEvent> entitiesToSchoolEvents(Iterator<Entity> results) {
		List<SchoolEvent> result = new ArrayList<>();
		while (results.hasNext()) { // We still have data
			// Add the SchoolEvent to the List
			result.add(entityToSchoolEvent(results.next()));
		}
		return result;
	}

	@Override
	public List<SchoolEvent> listSchoolEvents() {
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(1000);
		// We only care about CertificationCriteria
		// Use default Index "title"
		Query query = new Query(SE_KIND).addSort(SchoolEvent.DATE, SortDirection.DESCENDING).addSort(SchoolEvent.STATUS);
		PreparedQuery preparedQuery = datastore.prepare(query);
		QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

		List<SchoolEvent> resultCertificationCriteria = entitiesToSchoolEvents(results);
		return resultCertificationCriteria;
	}

}
