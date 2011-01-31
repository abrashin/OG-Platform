/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.fudgemsg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.fudgemsg.FudgeContext;
import org.fudgemsg.FudgeFieldContainer;
import org.fudgemsg.MutableFudgeFieldContainer;
import org.fudgemsg.mapping.FudgeDeserializationContext;
import org.fudgemsg.mapping.FudgeSerializationContext;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.core.region.RegionSource;
import com.opengamma.master.region.RegionMaster;
import com.opengamma.master.region.impl.InMemoryRegionMaster;
import com.opengamma.master.region.impl.MasterRegionSource;
import com.opengamma.master.region.impl.RegionFileReader;
import com.opengamma.util.fudge.OpenGammaFudgeContext;

/**
 * Base class for testing OG-Financial objects to and from Fudge messages.
 */
public class FinancialTestBase {

  private static final Logger s_logger = LoggerFactory.getLogger(FinancialTestBase.class);

  private RegionSource _regionSource;
  private FudgeContext _fudgeContext;

  @Before
  public void createFudgeContext() {
    _fudgeContext = OpenGammaFudgeContext.getInstance();
    RegionMaster regionMaster = new InMemoryRegionMaster();
    RegionFileReader.createPopulated(regionMaster);
    _regionSource = new MasterRegionSource(regionMaster);
  }

  protected FudgeContext getFudgeContext() {
    return _fudgeContext;
  }

  protected RegionSource getRegionSource() {
    return _regionSource;
  }

  private FudgeFieldContainer cycleMessage(final FudgeFieldContainer message) {
    final byte[] data = getFudgeContext().toByteArray(message);
    s_logger.info("{} bytes", data.length);
    return getFudgeContext().deserialize(data).getMessage();
  }

  protected <T> T cycleObject(final Class<T> clazz, final T object) {
    final T newObject = cycleGenericObject(clazz, object);
    assertEquals(object.getClass(), newObject.getClass());
    return newObject;
  }

  protected <T> T cycleGenericObject(final Class<T> clazz, final T object) {
    s_logger.info("object {}", object);
    final FudgeSerializationContext fudgeSerializationContext = new FudgeSerializationContext(getFudgeContext());
    final FudgeDeserializationContext fudgeDeserializationContext = new FudgeDeserializationContext(getFudgeContext());
    final MutableFudgeFieldContainer messageIn = fudgeSerializationContext.newMessage();
    fudgeSerializationContext.objectToFudgeMsg(messageIn, "test", null, object);
    s_logger.info("message {}", messageIn);
    final FudgeFieldContainer messageOut = cycleMessage(messageIn);
    s_logger.info("message {}", messageOut);
    final T newObject = fudgeDeserializationContext.fieldValueToObject(clazz, messageOut.getByName("test"));
    assertNotNull(newObject);
    s_logger.info("object {}", newObject);
    assertTrue(clazz.isAssignableFrom(newObject.getClass()));
    return newObject;
  }

}
