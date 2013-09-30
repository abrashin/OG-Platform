/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.engine.view;

import java.util.Map;

import org.apache.commons.lang.text.StrBuilder;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.depgraph.DependencyNode;
import com.opengamma.util.PublicAPI;
import org.joda.beans.Bean;

/**
 * Adds context to an execution log to indicate where the log events occurred.
 */
@PublicAPI
@BeanDefinition
public class ExecutionLogWithContext extends DirectBean {
  
  /**
   * The name of the engine function which produced the result, not null.
   */
  @PropertyDefinition(validate = "notNull")
  private String _functionName;
  
  /**
   * The specification of the target on which the engine function was operating, not null.
   */
  @PropertyDefinition(validate = "notNull")
  private ComputationTargetSpecification _targetSpecification;
  
  /**
   * The execution log, not null.
   */
  @PropertyDefinition(validate = "notNull")
  private ExecutionLog _executionLog;
    
  //-------------------------------------------------------------------------
  public static ExecutionLogWithContext of(DependencyNode node, ExecutionLog executionLog) {
    return of(node.getFunction().getFunction().getFunctionDefinition().getShortName(), node.getComputationTarget(), executionLog);
  }
  
  public static ExecutionLogWithContext of(String functionName, ComputationTargetSpecification targetSpec, ExecutionLog executionLog) {
    ExecutionLogWithContext result = new ExecutionLogWithContext();
    result.setFunctionName(functionName);
    result.setTargetSpecification(targetSpec);
    result.setExecutionLog(executionLog);
    return result;    
  }
  
  //-------------------------------------------------------------------------  
  @Override
  public String toString() {
    return new StrBuilder()
      .append("ExecutionLogWithContext[")
      .append(getFunctionName())
      .append(", ")
      .append(getTargetSpecification())
      .append(", ")
      .append(getExecutionLog())
      .append(']')
      .toString();
  }
  
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ExecutionLogWithContext}.
   * @return the meta-bean, not null
   */
  public static ExecutionLogWithContext.Meta meta() {
    return ExecutionLogWithContext.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ExecutionLogWithContext.Meta.INSTANCE);
  }

  @Override
  public ExecutionLogWithContext.Meta metaBean() {
    return ExecutionLogWithContext.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the name of the engine function which produced the result, not null.
   * @return the value of the property, not null
   */
  public String getFunctionName() {
    return _functionName;
  }

  /**
   * Sets the name of the engine function which produced the result, not null.
   * @param functionName  the new value of the property, not null
   */
  public void setFunctionName(String functionName) {
    JodaBeanUtils.notNull(functionName, "functionName");
    this._functionName = functionName;
  }

  /**
   * Gets the the {@code functionName} property.
   * @return the property, not null
   */
  public final Property<String> functionName() {
    return metaBean().functionName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the specification of the target on which the engine function was operating, not null.
   * @return the value of the property, not null
   */
  public ComputationTargetSpecification getTargetSpecification() {
    return _targetSpecification;
  }

  /**
   * Sets the specification of the target on which the engine function was operating, not null.
   * @param targetSpecification  the new value of the property, not null
   */
  public void setTargetSpecification(ComputationTargetSpecification targetSpecification) {
    JodaBeanUtils.notNull(targetSpecification, "targetSpecification");
    this._targetSpecification = targetSpecification;
  }

  /**
   * Gets the the {@code targetSpecification} property.
   * @return the property, not null
   */
  public final Property<ComputationTargetSpecification> targetSpecification() {
    return metaBean().targetSpecification().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the execution log, not null.
   * @return the value of the property, not null
   */
  public ExecutionLog getExecutionLog() {
    return _executionLog;
  }

  /**
   * Sets the execution log, not null.
   * @param executionLog  the new value of the property, not null
   */
  public void setExecutionLog(ExecutionLog executionLog) {
    JodaBeanUtils.notNull(executionLog, "executionLog");
    this._executionLog = executionLog;
  }

  /**
   * Gets the the {@code executionLog} property.
   * @return the property, not null
   */
  public final Property<ExecutionLog> executionLog() {
    return metaBean().executionLog().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public ExecutionLogWithContext clone() {
    BeanBuilder<? extends ExecutionLogWithContext> builder = metaBean().builder();
    for (MetaProperty<?> mp : metaBean().metaPropertyIterable()) {
      if (mp.style().isBuildable()) {
        Object value = mp.get(this);
        if (value instanceof Bean) {
          value = ((Bean) value).clone();
        }
        builder.set(mp.name(), value);
      }
    }
    return builder.build();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ExecutionLogWithContext other = (ExecutionLogWithContext) obj;
      return JodaBeanUtils.equal(getFunctionName(), other.getFunctionName()) &&
          JodaBeanUtils.equal(getTargetSpecification(), other.getTargetSpecification()) &&
          JodaBeanUtils.equal(getExecutionLog(), other.getExecutionLog());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getFunctionName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getTargetSpecification());
    hash += hash * 31 + JodaBeanUtils.hashCode(getExecutionLog());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ExecutionLogWithContext}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code functionName} property.
     */
    private final MetaProperty<String> _functionName = DirectMetaProperty.ofReadWrite(
        this, "functionName", ExecutionLogWithContext.class, String.class);
    /**
     * The meta-property for the {@code targetSpecification} property.
     */
    private final MetaProperty<ComputationTargetSpecification> _targetSpecification = DirectMetaProperty.ofReadWrite(
        this, "targetSpecification", ExecutionLogWithContext.class, ComputationTargetSpecification.class);
    /**
     * The meta-property for the {@code executionLog} property.
     */
    private final MetaProperty<ExecutionLog> _executionLog = DirectMetaProperty.ofReadWrite(
        this, "executionLog", ExecutionLogWithContext.class, ExecutionLog.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "functionName",
        "targetSpecification",
        "executionLog");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -211372413:  // functionName
          return _functionName;
        case -1553345806:  // targetSpecification
          return _targetSpecification;
        case -1217189620:  // executionLog
          return _executionLog;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ExecutionLogWithContext> builder() {
      return new DirectBeanBuilder<ExecutionLogWithContext>(new ExecutionLogWithContext());
    }

    @Override
    public Class<? extends ExecutionLogWithContext> beanType() {
      return ExecutionLogWithContext.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code functionName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> functionName() {
      return _functionName;
    }

    /**
     * The meta-property for the {@code targetSpecification} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ComputationTargetSpecification> targetSpecification() {
      return _targetSpecification;
    }

    /**
     * The meta-property for the {@code executionLog} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExecutionLog> executionLog() {
      return _executionLog;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -211372413:  // functionName
          return ((ExecutionLogWithContext) bean).getFunctionName();
        case -1553345806:  // targetSpecification
          return ((ExecutionLogWithContext) bean).getTargetSpecification();
        case -1217189620:  // executionLog
          return ((ExecutionLogWithContext) bean).getExecutionLog();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -211372413:  // functionName
          ((ExecutionLogWithContext) bean).setFunctionName((String) newValue);
          return;
        case -1553345806:  // targetSpecification
          ((ExecutionLogWithContext) bean).setTargetSpecification((ComputationTargetSpecification) newValue);
          return;
        case -1217189620:  // executionLog
          ((ExecutionLogWithContext) bean).setExecutionLog((ExecutionLog) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((ExecutionLogWithContext) bean)._functionName, "functionName");
      JodaBeanUtils.notNull(((ExecutionLogWithContext) bean)._targetSpecification, "targetSpecification");
      JodaBeanUtils.notNull(((ExecutionLogWithContext) bean)._executionLog, "executionLog");
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
