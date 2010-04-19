/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.view.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.ComputationTargetType;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.id.Identifier;
import com.opengamma.id.IdentificationScheme;
import com.opengamma.transport.FudgeRequestSender;
import com.opengamma.transport.InMemoryRequestConduit;

/**
 * 
 *
 * @author kirk
 */
public class RemoteViewComputationCacheSourceTest {
  private RemoteViewComputationCacheSource _cacheSource;
  
  @Before
  public void setupCacheSource() {
    RemoteCacheServer server = new RemoteCacheServer();
    FudgeRequestSender conduit = InMemoryRequestConduit.create(server);
    RemoteCacheClient client = new RemoteCacheClient(conduit);
    RemoteViewComputationCacheSource cacheSource = new RemoteViewComputationCacheSource(client);
    _cacheSource = cacheSource;
  }
  
  @After
  public void clearCaches() {
    if(_cacheSource != null) {
      _cacheSource.releaseAllLocalCaches();
    }
    _cacheSource = null;
  }

  @Test(timeout=10000l)
  public void singleThreadPutLoad() throws InterruptedException {
    ValueSpecification valueSpec = new ValueSpecification(new ValueRequirement("Test Value", new ComputationTargetSpecification(ComputationTargetType.PRIMITIVE, new Identifier(new IdentificationScheme("Kirk"), "Value"))));
    ComputedValue inputValue = new ComputedValue(valueSpec, 2.0);
    
    long timestamp = System.currentTimeMillis();
    ViewComputationCache cache = _cacheSource.getCache("View1", "Config1", timestamp);
    
    cache.putValue(inputValue);
    
    // First, check that it hit the remote side. RemoteClient doesn't cache locally.
    Object resultValue = _cacheSource.getRemoteClient().getValue("View1", "Config1", timestamp, valueSpec);
    assertNotNull(resultValue);
    assertTrue(resultValue instanceof Double);
    assertEquals(2.0, (Double)resultValue, 0.0001);
    
    // Now check the local caching.
    resultValue = cache.getValue(valueSpec);
    assertNotNull(resultValue);
    assertTrue("resultValue was " + resultValue, resultValue instanceof Double);
    assertEquals(2.0, (Double)resultValue, 0.0001);
    resultValue = cache.getValue(valueSpec);
    assertNotNull(resultValue);
    assertTrue("resultValue was " + resultValue, resultValue instanceof Double);
    assertEquals(2.0, (Double)resultValue, 0.0001);
    resultValue = cache.getValue(valueSpec);
    assertNotNull(resultValue);
    assertTrue("resultValue was " + resultValue, resultValue instanceof Double);
    assertEquals(2.0, (Double)resultValue, 0.0001);
  }

}
