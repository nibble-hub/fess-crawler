/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.robot.db.bsentity;

import java.io.Serializable;
import java.util.Set;

import org.seasar.robot.db.allcommon.DBMetaInstanceHandler;
import org.seasar.robot.dbflute.Entity;
import org.seasar.robot.dbflute.dbmeta.DBMeta;

/**
 * The entity of URL_FILTER that is TABLE. <br />
 * <pre>
 * [primary-key]
 *     ID
 * 
 * [column]
 *     ID, SESSION_ID, URL, FILTER_TYPE, CREATE_TIME
 * 
 * [sequence]
 *     
 * 
 * [identity]
 *     ID
 * 
 * [version-no]
 *     
 * 
 * [foreign-table]
 *     
 * 
 * [referrer-table]
 *     
 * 
 * [foreign-property]
 *     
 * 
 * [referrer-property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsUrlFilter implements Entity, Serializable {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** Serial version UID. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // -----------------------------------------------------
    //                                                Column
    //                                                ------
    /** ID: {PK : ID : NotNull : BIGINT(19)} */
    protected Long _id;

    /** SESSION_ID: {NotNull : VARCHAR(20)} */
    protected String _sessionId;

    /** URL: {NotNull : TEXT(65535)} */
    protected String _url;

    /** FILTER_TYPE: {NotNull : VARCHAR(1)} */
    protected String _filterType;

    /** CREATE_TIME: {NotNull : DATETIME(19)} */
    protected java.sql.Timestamp _createTime;

    // -----------------------------------------------------
    //                                              Internal
    //                                              --------
    /** The modified properties for this entity. */
    protected EntityModifiedProperties _modifiedProperties = newEntityModifiedProperties();

    // ===================================================================================
    //                                                                          Table Name
    //                                                                          ==========
    public String getTableDbName() {
        return "URL_FILTER";
    }

    public String getTablePropertyName() { // as JavaBeansRule
        return "urlFilter";
    }

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    public DBMeta getDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(getTableDbName());
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    // ===================================================================================
    //                                                                       Determination
    //                                                                       =============
    public boolean hasPrimaryKeyValue() {
        if (_id == null) {
            return false;
        }
        return true;
    }

    // ===================================================================================
    //                                                                 Modified Properties
    //                                                                 ===================
    public Set<String> getModifiedPropertyNames() {
        return _modifiedProperties.getPropertyNames();
    }

    protected EntityModifiedProperties newEntityModifiedProperties() {
        return new EntityModifiedProperties();
    }

    public void clearModifiedPropertyNames() {
        _modifiedProperties.clear();
    }

    public boolean hasModification() {
        return !_modifiedProperties.isEmpty();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    /**
     * If the primary-key of the other is same as this one, returns true.
     * @param other Other entity.
     * @return Comparing result.
     */
    public boolean equals(Object other) {
        if (other == null || !(other instanceof BsUrlFilter)) {
            return false;
        }
        BsUrlFilter otherEntity = (BsUrlFilter) other;
        if (!helpComparingValue(getId(), otherEntity.getId())) {
            return false;
        }
        return true;
    }

    protected boolean helpComparingValue(Object value1, Object value2) {
        if (value1 == null && value2 == null) {
            return true;
        }
        return value1 != null && value2 != null && value1.equals(value2);
    }

    /**
     * Calculates hash-code from primary-key.
     * @return Hash-code from primary-key.
     */
    public int hashCode() {
        int result = 17;
        if (getId() != null) {
            result = (31 * result) + getId().hashCode();
        }
        return result;
    }

    /**
     * @return The display string of all columns and relation existences. (NotNull)
     */
    public String toString() {
        return buildDisplayString(getClass().getSimpleName(), true, true);
    }

    /**
     * @return The display string of basic informations with one-nested relation values. (NotNull)
     */
    public String toStringWithRelation() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        return sb.toString();
    }

    /**
     * @param name The name for display. (Nullable: If it's null, it does not have a name)
     * @param column Does it contains column values or not?
     * @param relation Does it contains relation existences or not?
     * @return The display string for this entity. (NotNull)
     */
    public String buildDisplayString(String name, boolean column,
            boolean relation) {
        StringBuilder sb = new StringBuilder();
        if (name != null) {
            sb.append(name).append(column || relation ? ":" : "");
        }
        if (column) {
            sb.append(xbuildColumnString());
        }
        if (relation) {
            sb.append(xbuildRelationString());
        }
        sb.append("@").append(Integer.toHexString(hashCode()));
        return sb.toString();
    }

    private String xbuildColumnString() {
        String c = ",";
        StringBuilder sb = new StringBuilder();
        sb.append(c).append(getId());
        sb.append(c).append(getSessionId());
        sb.append(c).append(getUrl());
        sb.append(c).append(getFilterType());
        sb.append(c).append(getCreateTime());
        if (sb.length() > 0) {
            sb.delete(0, c.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    private String xbuildRelationString() {
        return "";
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * ID: {PK : ID : NotNull : BIGINT(19)} <br />
     * @return The value of the column 'ID'. (Nullable)
     */
    public Long getId() {
        return _id;
    }

    /**
     * ID: {PK : ID : NotNull : BIGINT(19)} <br />
     * @param id The value of the column 'ID'. (Nullable)
     */
    public void setId(Long id) {
        _modifiedProperties.addPropertyName("id");
        this._id = id;
    }

    /**
     * SESSION_ID: {NotNull : VARCHAR(20)} <br />
     * @return The value of the column 'SESSION_ID'. (Nullable)
     */
    public String getSessionId() {
        return _sessionId;
    }

    /**
     * SESSION_ID: {NotNull : VARCHAR(20)} <br />
     * @param sessionId The value of the column 'SESSION_ID'. (Nullable)
     */
    public void setSessionId(String sessionId) {
        _modifiedProperties.addPropertyName("sessionId");
        this._sessionId = sessionId;
    }

    /**
     * URL: {NotNull : TEXT(65535)} <br />
     * @return The value of the column 'URL'. (Nullable)
     */
    public String getUrl() {
        return _url;
    }

    /**
     * URL: {NotNull : TEXT(65535)} <br />
     * @param url The value of the column 'URL'. (Nullable)
     */
    public void setUrl(String url) {
        _modifiedProperties.addPropertyName("url");
        this._url = url;
    }

    /**
     * FILTER_TYPE: {NotNull : VARCHAR(1)} <br />
     * @return The value of the column 'FILTER_TYPE'. (Nullable)
     */
    public String getFilterType() {
        return _filterType;
    }

    /**
     * FILTER_TYPE: {NotNull : VARCHAR(1)} <br />
     * @param filterType The value of the column 'FILTER_TYPE'. (Nullable)
     */
    public void setFilterType(String filterType) {
        _modifiedProperties.addPropertyName("filterType");
        this._filterType = filterType;
    }

    /**
     * CREATE_TIME: {NotNull : DATETIME(19)} <br />
     * @return The value of the column 'CREATE_TIME'. (Nullable)
     */
    public java.sql.Timestamp getCreateTime() {
        return _createTime;
    }

    /**
     * CREATE_TIME: {NotNull : DATETIME(19)} <br />
     * @param createTime The value of the column 'CREATE_TIME'. (Nullable)
     */
    public void setCreateTime(java.sql.Timestamp createTime) {
        _modifiedProperties.addPropertyName("createTime");
        this._createTime = createTime;
    }
}
