/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.value;

import java.io.Serializable;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.fudgemsg.FudgeMessageFactory;
import org.fudgemsg.MutableFudgeFieldContainer;
import org.fudgemsg.FudgeFieldContainer;

import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.ComputationTargetType;
import com.opengamma.id.Identifier;
import com.opengamma.util.ArgumentChecker;

/**
 * 
 *
 * @author kirk
 */
public class ValueRequirement implements Serializable {
  public static final String VALUE_NAME_FIELD_NAME = "valueName";
  
  private final String _valueName;
  private final ComputationTargetSpecification _targetSpecification;
  
  public ValueRequirement(String valueName, ComputationTargetType targetType, Identifier targetKey) {
    this(valueName, new ComputationTargetSpecification(targetType, targetKey));
  }
  
  public ValueRequirement(String valueName, ComputationTargetSpecification targetSpecification) {
    ArgumentChecker.checkNotNull(valueName, "Value name");
    ArgumentChecker.checkNotNull(targetSpecification, "Computation target specification");
    _valueName = valueName.intern();
    _targetSpecification = targetSpecification;
  }
  
  public ValueRequirement(String valueName, Object computationTarget) {
    this(valueName, new ComputationTargetSpecification(computationTarget));
  }
  
  /**
   * @return the valueName
   */
  public String getValueName() {
    return _valueName;
  }
  
  /**
   * @return the targetSpecification
   */
  public ComputationTargetSpecification getTargetSpecification() {
    return _targetSpecification;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj == null) {
      return false;
    }
    if(!(obj instanceof ValueRequirement)) {
      return false;
    }
    ValueRequirement other = (ValueRequirement) obj;
    // Note that we're interning, so we can do this.
    if(_valueName != other._valueName) {
      return false;
    }
    if(!ObjectUtils.equals(_targetSpecification, other._targetSpecification)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + _valueName.hashCode();
    result = prime * result + _targetSpecification.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
  
  public void toFudgeMsg (FudgeMessageFactory fudgeContext, MutableFudgeFieldContainer msg) {
    msg.add(VALUE_NAME_FIELD_NAME, _valueName);
    _targetSpecification.toFudgeMsg(fudgeContext, msg);
  }
  
  public static ValueRequirement fromFudgeMsg(FudgeFieldContainer msg) {
    String valueName = msg.getString(VALUE_NAME_FIELD_NAME);
    ComputationTargetSpecification targetSpecification = ComputationTargetSpecification.fromFudgeMsg(msg); 
    return new ValueRequirement(valueName, targetSpecification);
  }
  
}
