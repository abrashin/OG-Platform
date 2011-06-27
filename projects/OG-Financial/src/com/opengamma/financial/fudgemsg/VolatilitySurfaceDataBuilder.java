/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.fudgemsg;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.fudgemsg.FudgeField;
import org.fudgemsg.FudgeMsg;
import org.fudgemsg.MutableFudgeMsg;
import org.fudgemsg.mapping.FudgeBuilder;
import org.fudgemsg.mapping.FudgeBuilderFor;
import org.fudgemsg.mapping.FudgeDeserializationContext;
import org.fudgemsg.mapping.FudgeSerializationContext;

import com.opengamma.financial.analytics.volatility.surface.VolatilitySurfaceData;
import com.opengamma.util.money.Currency;
import com.opengamma.util.tuple.Pair;

/**
 * Builder for converting VolatilitySurfaceDefinition instances to/from Fudge messages.
 */
@FudgeBuilderFor(VolatilitySurfaceData.class)
public class VolatilitySurfaceDataBuilder implements FudgeBuilder<VolatilitySurfaceData<?, ?>> {

  @Override
  public MutableFudgeMsg buildMessage(final FudgeSerializationContext context, final VolatilitySurfaceData<?, ?> object) {
    final MutableFudgeMsg message = context.newMessage();
    context.addToMessage(message, "currency", null, object.getCurrency());
    message.add("definitionName", object.getDefinitionName());
    message.add("specificationName", object.getSpecificationName());
    for (final Object x : object.getXs()) {
      message.add("xs", null, FudgeSerializationContext.addClassHeader(context.objectToFudgeMsg(x), x.getClass()));
    }
    for (final Object y : object.getYs()) {
      message.add("ys", null, FudgeSerializationContext.addClassHeader(context.objectToFudgeMsg(y), y.getClass()));
    }
    for (final Entry<?, Double> entry : object.asMap().entrySet()) {
      @SuppressWarnings("unchecked")
      final Pair<Object, Object> pair = (Pair<Object, Object>) entry.getKey();
      final MutableFudgeMsg subMessage = context.newMessage();
      subMessage.add("x", null, context.objectToFudgeMsg(pair.getFirst()));
      subMessage.add("y", null, context.objectToFudgeMsg(pair.getSecond()));
      subMessage.add("value", null, entry.getValue());
      message.add("values", null, subMessage);
    }
    return message;
  }

  @Override
  public VolatilitySurfaceData<?, ?> buildObject(final FudgeDeserializationContext context, final FudgeMsg message) {
    final Currency currency = context.fieldValueToObject(Currency.class, message.getByName("currency"));
    final String definitionName = message.getString("definitionName");
    final String specificationName = message.getString("specificationName");
    final List<FudgeField> xsFields = message.getAllByName("xs");
    final List<Object> xs = new ArrayList<Object>();
    Object[] xsArray = null;
    for (final FudgeField xField : xsFields) {
      final Object x = context.fieldValueToObject(xField);
      xs.add(x);
      if (xsArray == null) {
        xsArray = (Object[]) Array.newInstance(x.getClass(), 0);
      }
    }
    final List<FudgeField> ysFields = message.getAllByName("ys");
    final List<Object> ys = new ArrayList<Object>();
    Object[] ysArray = null;
    for (final FudgeField yField : ysFields) {
      final Object y = context.fieldValueToObject(yField);
      ys.add(y);
      if (ysArray == null) {
        ysArray = (Object[]) Array.newInstance(y.getClass(), 0);
      }
    }
    if (xs.size() > 0 && ys.size() > 0) {
      final Class<?> xClazz = xs.get(0).getClass();
      final Class<?> yClazz = ys.get(0).getClass();
      final Map<Pair<Object, Object>, Double> values = new HashMap<Pair<Object, Object>, Double>();
      final List<FudgeField> valuesFields = message.getAllByName("values");
      for (final FudgeField valueField : valuesFields) {
        final FudgeMsg subMessage = (FudgeMsg) valueField.getValue();
        final Object x = context.fieldValueToObject(xClazz, subMessage.getByName("x"));
        final Object y = context.fieldValueToObject(yClazz, subMessage.getByName("y"));
        final Double value = subMessage.getDouble("value");
        values.put(Pair.of(x, y), value);
      }
      return new VolatilitySurfaceData<Object, Object>(definitionName, specificationName, currency, xs.toArray(xsArray), ys.toArray(ysArray), values);
    } else {
      return new VolatilitySurfaceData<Object, Object>(definitionName, specificationName, currency, xs.toArray(), ys.toArray(), Collections.<Pair<Object, Object>, Double> emptyMap());
    }
  }

}
