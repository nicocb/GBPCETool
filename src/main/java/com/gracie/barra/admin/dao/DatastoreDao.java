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
import java.util.Iterator;
import java.util.List;

import com.google.appengine.api.datastore.Cursor;
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
import com.gracie.barra.admin.objects.CertificationCriterion;
import com.gracie.barra.admin.objects.Result;

public class DatastoreDao implements CertificationDao {

  private DatastoreService datastore;
  private static final String CC_KIND = "CertificationCriterion";

  public DatastoreDao() {
    datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
  }

  public CertificationCriterion entityToCertificationCriterion(Entity entity) {
	  return new CertificationCriterion.Builder()                                     // Convert to CertificationCriterion form
			  .id(entity.getKey().getId())
			  .description((String)entity.getProperty(CertificationCriterion.DESCRIPTION))
			  .comment((String)entity.getProperty(CertificationCriterion.COMMENT))
			  .action((String)entity.getProperty(CertificationCriterion.ACTION))
			  .rank((Long)entity.getProperty(CertificationCriterion.RANK))

			  .build();
  }

  @Override
  public Long createCertificationCriterion(CertificationCriterion cc) {
    Entity ccEntity = new Entity(CC_KIND);  // Key will be assigned once written
    ccEntity.setProperty(CertificationCriterion.DESCRIPTION, cc.getDescription());
    ccEntity.setProperty(CertificationCriterion.COMMENT, cc.getComment());
    ccEntity.setProperty(CertificationCriterion.ACTION, cc.getAction());
    ccEntity.setProperty(CertificationCriterion.RANK, cc.getRank());


    Key ccKey = datastore.put(ccEntity); // Save the Entity
    return ccKey.getId();                     // The ID of the Key
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
  public void updateCertificationCriterion(CertificationCriterion certificationCriterion) {
    Key key = KeyFactory.createKey(CC_KIND, certificationCriterion.getId());  // From a certificationCriterion, create a Key
    Entity entity = new Entity(key);         // Convert CertificationCriterion to an Entity
    entity.setProperty(CertificationCriterion.DESCRIPTION, certificationCriterion.getDescription());
    entity.setProperty(CertificationCriterion.COMMENT, certificationCriterion.getComment());
    entity.setProperty(CertificationCriterion.ACTION, certificationCriterion.getAction());
    entity.setProperty(CertificationCriterion.RANK, certificationCriterion.getRank());

    datastore.put(entity);                   // Update the Entity
  }

  @Override
  public void deleteCertificationCriterion(Long certificationCriterionId) {
    Key key = KeyFactory.createKey(CC_KIND, certificationCriterionId);        // Create the Key
    datastore.delete(key);                      // Delete the Entity
  }

  public List<CertificationCriterion> entitiesToCertificationCriterions(Iterator<Entity> results) {
    List<CertificationCriterion> resultCertificationCriterions = new ArrayList<>();
    while (results.hasNext()) {  // We still have data
      resultCertificationCriterions.add(entityToCertificationCriterion(results.next()));      // Add the CertificationCriterion to the List
    }
    return resultCertificationCriterions;
  }

  @Override
  public Result<CertificationCriterion> listCertificationCriterions(String startCursorString) {
    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
    if (startCursorString != null && !startCursorString.equals("")) {
      fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
    }
    Query query = new Query(CC_KIND) // We only care about CertificationCriterions
        .addSort(CertificationCriterion.DESCRIPTION, SortDirection.ASCENDING); // Use default Index "title"
    PreparedQuery preparedQuery = datastore.prepare(query);
    QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

    List<CertificationCriterion> resultCertificationCriterions = entitiesToCertificationCriterions(results);     // Retrieve and convert Entities
    Cursor cursor = results.getCursor();              // Where to start next time
    if (cursor != null && resultCertificationCriterions.size() == 10) {         // Are we paging? Save Cursor
      String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
      return new Result<>(resultCertificationCriterions, cursorString);
    } else {
      return new Result<>(resultCertificationCriterions);
    }
  }
}
