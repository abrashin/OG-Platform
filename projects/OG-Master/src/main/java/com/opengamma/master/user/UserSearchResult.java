/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.user;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.Lists;
import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.id.VersionCorrection;
import com.opengamma.master.AbstractSearchResult;
import com.opengamma.util.PublicSPI;

/**
 * Result from searching for users.
 * <p>
 * The returned documents will match the search criteria.
 * See {@link UserSearchRequest} for more details.
 */
@PublicSPI
@BeanDefinition
public class UserSearchResult extends AbstractSearchResult<UserDocument> {

  /**
   * Creates an instance.
   */
  public UserSearchResult() {
  }

  /**
   * Creates an instance from a collection of documents.
   *
   * @param coll  the collection of documents to add, not null
   */
  public UserSearchResult(Collection<UserDocument> coll) {
    super(coll);
  }

  /**
   * Creates an instance specifying the version-correction searched for.
   * 
   * @param versionCorrection  the version-correction of the data, not null
   */
  public UserSearchResult(VersionCorrection versionCorrection) {
    setVersionCorrection(versionCorrection);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the returned users from within the documents.
   *
   * @return the users, not null
   */
  public List<ManageableOGUser> getUsers() {
    List<ManageableOGUser> result = Lists.newArrayList();
    if (getDocuments() != null) {
      for (UserDocument doc : getDocuments()) {
        result.add(doc.getUser());
      }
    }
    return result;
  }

  /**
   * Gets the first user, or null if no documents.
   *
   * @return the first user, null if none
   */
  public ManageableOGUser getFirstUser() {
    return getDocuments().size() > 0 ? getDocuments().get(0).getUser() : null;
  }

  /**
   * Gets the single result expected from a query.
   * <p>
   * This throws an exception if more than 1 result is actually available.
   * Thus, this method implies an assumption about uniqueness of the queried user.
   *
   * @return the matching user, not null
   * @throws IllegalStateException if no user was found
   */
  public ManageableOGUser getSingleUser() {
    if (getDocuments().size() != 1) {
      throw new OpenGammaRuntimeException("Expecting zero or single resulting match, and was " + getDocuments().size());
    } else {
      return getDocuments().get(0).getUser();
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code UserSearchResult}.
   * @return the meta-bean, not null
   */
  public static UserSearchResult.Meta meta() {
    return UserSearchResult.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(UserSearchResult.Meta.INSTANCE);
  }

  @Override
  public UserSearchResult.Meta metaBean() {
    return UserSearchResult.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  @Override
  public UserSearchResult clone() {
    return (UserSearchResult) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(32);
    buf.append("UserSearchResult{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  @Override
  protected void toString(StringBuilder buf) {
    super.toString(buf);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code UserSearchResult}.
   */
  public static class Meta extends AbstractSearchResult.Meta<UserDocument> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap());

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    public BeanBuilder<? extends UserSearchResult> builder() {
      return new DirectBeanBuilder<UserSearchResult>(new UserSearchResult());
    }

    @Override
    public Class<? extends UserSearchResult> beanType() {
      return UserSearchResult.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
