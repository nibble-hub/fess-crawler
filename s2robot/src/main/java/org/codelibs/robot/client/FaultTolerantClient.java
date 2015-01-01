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
package org.codelibs.robot.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codelibs.robot.entity.RequestData;
import org.codelibs.robot.entity.ResponseData;
import org.codelibs.robot.exception.MaxLengthExceededException;
import org.codelibs.robot.exception.RobotMultipleCrawlAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shinsuke
 *
 */
public class FaultTolerantClient implements S2RobotClient {

    private static final Logger logger = LoggerFactory // NOPMD
            .getLogger(FaultTolerantClient.class);

    protected S2RobotClient client;

    protected int maxRetryCount = 5;

    protected long retryInterval = 500;

    protected RequestListener listener;

    @Override
    public void setInitParameterMap(final Map<String, Object> params) {
        client.setInitParameterMap(params);
    }

    @Override
    public ResponseData execute(final RequestData request) {
        if (listener != null) {
            listener.onRequestStart(this, request);
        }

        List<Exception> exceptionList = null;
        try {
            int count = 0;
            while (count < maxRetryCount) {
                if (listener != null) {
                    listener.onRequest(this, request, count);
                }

                try {
                    return client.execute(request);
                } catch (final MaxLengthExceededException e) {
                    throw e;
                } catch (final Exception e) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Failed to access to " + request.getUrl(),
                                e);
                    }

                    if (listener != null) {
                        listener.onException(this, request, count, e);
                    }

                    if (exceptionList == null) {
                        exceptionList = new ArrayList<Exception>();
                    }
                    exceptionList.add(e);
                }

                try {
                    Thread.sleep(retryInterval);
                } catch (final InterruptedException e) {
                    // ignore
                }
                count++;
            }
            throw new RobotMultipleCrawlAccessException("Failed to access to "
                    + request.getUrl(),
                    exceptionList.toArray(new Throwable[exceptionList.size()]));
        } finally {
            if (listener != null) {
                listener.onRequestEnd(this, request, exceptionList);
            }
        }
    }

    public S2RobotClient getRobotClient() {
        return client;
    }

    public void setRobotClient(final S2RobotClient client) {
        this.client = client;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(final int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(final long retryInterval) {
        this.retryInterval = retryInterval;
    }

    public RequestListener getRequestListener() {
        return listener;
    }

    public void setRequestListener(final RequestListener listener) {
        this.listener = listener;
    }

    public interface RequestListener {

        void onRequestStart(FaultTolerantClient client, RequestData request);

        void onRequest(FaultTolerantClient client, RequestData request,
                int count);

        void onRequestEnd(FaultTolerantClient client, RequestData request,
                List<Exception> exceptionList);

        void onException(FaultTolerantClient client, RequestData request,
                int count, Exception e);

    }
}
