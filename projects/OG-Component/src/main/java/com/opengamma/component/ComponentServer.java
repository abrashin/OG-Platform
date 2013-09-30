/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.component;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.opengamma.component.rest.RemoteComponentServer;
import com.opengamma.util.ArgumentChecker;
import org.joda.beans.Bean;

/**
 * Information about a principal component of the OpenGamma system.
 */
@BeanDefinition
public class ComponentServer extends DirectBean {

  /**
   * The URI that the server is published at.
   * <p>
   * This is set by {@link RemoteComponentServer}.
   */
  @PropertyDefinition(validate = "notNull")
  private URI _uri;
  /**
   * The complete set of available components.
   */
  @PropertyDefinition(validate = "notNull")
  private List<ComponentInfo> _componentInfos = new ArrayList<ComponentInfo>();

  /**
   * Creates an instance.
   */
  protected ComponentServer() {
  }

  /**
   * Creates an instance.
   * 
   * @param uri  the uri of the server, not null
   */
  public ComponentServer(URI uri) {
    setUri(uri);
  }

  //-------------------------------------------------------------------------
  /**
   * Finds a component by type and classifier.
   * <p>
   * The returned information will contain the URI of the component for access.
   * 
   * @param type  the type of the component, typically an interface
   * @param classifier  the classifier of the type, used to name instances of the same type
   * @return the info for the component, not null
   */
  public ComponentInfo getComponentInfo(Class<?> type, String classifier) {
    for (ComponentInfo info : getComponentInfos()) {
      if (info.matches(type, classifier)) {
        return info;
      }
    }
    throw new IllegalArgumentException("Component not found: " + type + "::" + classifier);
  }

  /**
   * Finds a component by type.
   * <p>
   * The returned information will contain the URI of the component for access.
   * 
   * @param type  the type of the component, typically an interface
   * @return all the matching components, not null
   */
  public List<ComponentInfo> getComponentInfos(Class<?> type) {
    List<ComponentInfo> result = new ArrayList<ComponentInfo>();
    for (ComponentInfo info : getComponentInfos()) {
      if (info.getType().equals(type)) {
        result.add(info);
      }
    }
    return result;
  }

  /**
   * Gets a map of component information for a given type, keyed by classifier.
   * <p>
   * The returned information will contain the URI of the component for access.
   * 
   * @param type  the type of the component, typically an interface
   * @return all the matching components, keyed by classifier, not null
   */
  public Map<String, ComponentInfo> getComponentInfoMap(Class<?> type) {
    Map<String, ComponentInfo> result = new LinkedHashMap<String, ComponentInfo>();
    for (ComponentInfo info : getComponentInfos()) {
      if (info.getType().equals(type)) {
        result.put(info.getClassifier(), info);
      }
    }
    return result;
  }

  //-------------------------------------------------------------------------
  /**
   * Applies the URI for the server.
   * <p>
   * This recursively sets the URI onto any component informations that have a relative URI.
   * This is normally called after the information is retrieved from a remote server.
   *
   * @param baseUri  the base URI of the server, not null
   */
  public void applyBaseUri(URI baseUri) {
    ArgumentChecker.notNull(baseUri, "baseUri");
    setUri(baseUri);
    for (ComponentInfo info : getComponentInfos()) {
      info.setUri(baseUri.resolve(info.getUri()));
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ComponentServer}.
   * @return the meta-bean, not null
   */
  public static ComponentServer.Meta meta() {
    return ComponentServer.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ComponentServer.Meta.INSTANCE);
  }

  @Override
  public ComponentServer.Meta metaBean() {
    return ComponentServer.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the URI that the server is published at.
   * <p>
   * This is set by {@link RemoteComponentServer}.
   * @return the value of the property, not null
   */
  public URI getUri() {
    return _uri;
  }

  /**
   * Sets the URI that the server is published at.
   * <p>
   * This is set by {@link RemoteComponentServer}.
   * @param uri  the new value of the property, not null
   */
  public void setUri(URI uri) {
    JodaBeanUtils.notNull(uri, "uri");
    this._uri = uri;
  }

  /**
   * Gets the the {@code uri} property.
   * <p>
   * This is set by {@link RemoteComponentServer}.
   * @return the property, not null
   */
  public final Property<URI> uri() {
    return metaBean().uri().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the complete set of available components.
   * @return the value of the property, not null
   */
  public List<ComponentInfo> getComponentInfos() {
    return _componentInfos;
  }

  /**
   * Sets the complete set of available components.
   * @param componentInfos  the new value of the property, not null
   */
  public void setComponentInfos(List<ComponentInfo> componentInfos) {
    JodaBeanUtils.notNull(componentInfos, "componentInfos");
    this._componentInfos = componentInfos;
  }

  /**
   * Gets the the {@code componentInfos} property.
   * @return the property, not null
   */
  public final Property<List<ComponentInfo>> componentInfos() {
    return metaBean().componentInfos().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public ComponentServer clone() {
    BeanBuilder<? extends ComponentServer> builder = metaBean().builder();
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
      ComponentServer other = (ComponentServer) obj;
      return JodaBeanUtils.equal(getUri(), other.getUri()) &&
          JodaBeanUtils.equal(getComponentInfos(), other.getComponentInfos());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getUri());
    hash += hash * 31 + JodaBeanUtils.hashCode(getComponentInfos());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("ComponentServer{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("uri").append('=').append(getUri()).append(',').append(' ');
    buf.append("componentInfos").append('=').append(getComponentInfos()).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ComponentServer}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code uri} property.
     */
    private final MetaProperty<URI> _uri = DirectMetaProperty.ofReadWrite(
        this, "uri", ComponentServer.class, URI.class);
    /**
     * The meta-property for the {@code componentInfos} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<ComponentInfo>> _componentInfos = DirectMetaProperty.ofReadWrite(
        this, "componentInfos", ComponentServer.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "uri",
        "componentInfos");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 116076:  // uri
          return _uri;
        case 1349827208:  // componentInfos
          return _componentInfos;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ComponentServer> builder() {
      return new DirectBeanBuilder<ComponentServer>(new ComponentServer());
    }

    @Override
    public Class<? extends ComponentServer> beanType() {
      return ComponentServer.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code uri} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<URI> uri() {
      return _uri;
    }

    /**
     * The meta-property for the {@code componentInfos} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<ComponentInfo>> componentInfos() {
      return _componentInfos;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 116076:  // uri
          return ((ComponentServer) bean).getUri();
        case 1349827208:  // componentInfos
          return ((ComponentServer) bean).getComponentInfos();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 116076:  // uri
          ((ComponentServer) bean).setUri((URI) newValue);
          return;
        case 1349827208:  // componentInfos
          ((ComponentServer) bean).setComponentInfos((List<ComponentInfo>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((ComponentServer) bean)._uri, "uri");
      JodaBeanUtils.notNull(((ComponentServer) bean)._componentInfos, "componentInfos");
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
