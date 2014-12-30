/*
 * Copyright 2004-2014 the Seasar Foundation and the Others.
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
package org.codelibs.robot.entity;

import java.util.Date;

import org.codelibs.core.beans.util.BeanUtil;
import org.codelibs.robot.Constants;

/**
 * @author shinsuke
 *
 */
public class AccessResultImpl implements AccessResult {
    protected Long id;

    protected String sessionId;

    protected String ruleId;

    protected String url;

    protected String parentUrl;

    protected Integer status = Constants.OK_STATUS;

    protected Integer httpStatusCode;

    protected String method;

    protected String mimeType;

    protected Long createTime;

    protected Integer executionTime;

    protected Long contentLength;

    protected Long lastModified;

    protected AccessResultData accessResultData;

    @Override
    public void init(final ResponseData responseData,
            final ResultData resultData) {

        setCreateTime(new Long(new Date().getTime()));
        if (responseData != null) {
            BeanUtil.copyBeanToBean(responseData, this);
        }

        final AccessResultData accessResultData = new AccessResultDataImpl();
        if (resultData != null) {
            BeanUtil.copyBeanToBean(resultData, accessResultData);
        }
        setAccessResultData(accessResultData);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#setId(java.lang.Long)
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getSessionId()
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#setSessionId(java.lang.String)
     */
    @Override
    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getRuleId()
     */
    @Override
    public String getRuleId() {
        return ruleId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#setRuleId(java.lang.String)
     */
    @Override
    public void setRuleId(final String ruleId) {
        this.ruleId = ruleId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getUrl()
     */
    @Override
    public String getUrl() {
        return url;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#setUrl(java.lang.String)
     */
    @Override
    public void setUrl(final String url) {
        this.url = url;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getParentUrl()
     */
    @Override
    public String getParentUrl() {
        return parentUrl;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#setParentUrl(java.lang.String)
     */
    @Override
    public void setParentUrl(final String parentUrl) {
        this.parentUrl = parentUrl;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getStatus()
     */
    @Override
    public Integer getStatus() {
        return status;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#setStatus(java.lang.Integer)
     */
    @Override
    public void setStatus(final Integer status) {
        this.status = status;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getHttpStatusCode()
     */
    @Override
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.codelibs.robot.entity.AccessResult#setHttpStatusCode(java.lang.Integer)
     */
    @Override
    public void setHttpStatusCode(final Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getMethod()
     */
    @Override
    public String getMethod() {
        return method;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#setMethod(java.lang.String)
     */
    @Override
    public void setMethod(final String method) {
        this.method = method;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getMimeType()
     */
    @Override
    public String getMimeType() {
        return mimeType;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#setMimeType(java.lang.String)
     */
    @Override
    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getCreateTime()
     */
    @Override
    public Long getCreateTime() {
        return createTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.codelibs.robot.entity.AccessResult#setCreateTime(java.sql.Long)
     */
    @Override
    public void setCreateTime(final Long createTime) {
        this.createTime = createTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getAccessResultDataAsOne()
     */
    @Override
    public AccessResultData getAccessResultData() {
        return accessResultData;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.codelibs.robot.entity.AccessResult#setAccessResultDataAsOne(org.seasar
     * .robot.db.exentity.AccessResultData)
     */
    @Override
    public void setAccessResultData(final AccessResultData accessResultDataAsOne) {
        accessResultData = accessResultDataAsOne;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.codelibs.robot.entity.AccessResult#getExecutionTime()
     */
    @Override
    public Integer getExecutionTime() {
        return executionTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.codelibs.robot.entity.AccessResult#setExecutionTime(java.lang.Integer)
     */
    @Override
    public void setExecutionTime(final Integer executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public Long getContentLength() {
        return contentLength;
    }

    @Override
    public void setContentLength(final Long contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public Long getLastModified() {
        return lastModified;
    }

    @Override
    public void setLastModified(final Long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "AccessResultImpl [id=" + id + ", sessionId=" + sessionId
                + ", ruleId=" + ruleId + ", url=" + url + ", parentUrl="
                + parentUrl + ", status=" + status + ", httpStatusCode="
                + httpStatusCode + ", method=" + method + ", mimeType="
                + mimeType + ", createTime=" + createTime + ", executionTime="
                + executionTime + ", contentLength=" + contentLength
                + ", lastModified=" + lastModified + ", accessResultData="
                + accessResultData + "]";
    }

}
