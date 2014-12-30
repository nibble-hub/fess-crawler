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
package org.codelibs.robot.extractor.impl;

import java.io.InputStream;
import java.util.Map;

import org.codelibs.core.io.InputStreamUtil;
import org.codelibs.robot.Constants;
import org.codelibs.robot.RobotSystemException;
import org.codelibs.robot.entity.ExtractData;
import org.codelibs.robot.extractor.ExtractException;
import org.codelibs.robot.extractor.Extractor;

/**
 * @author shinsuke
 *
 */
public class TextExtractor implements Extractor {

    protected String encoding = Constants.UTF_8;

    @Override
    public ExtractData getText(final InputStream in,
            final Map<String, String> params) {
        if (in == null) {
            throw new RobotSystemException("The inputstream is null.");
        }
        try {
            return new ExtractData(new String(InputStreamUtil.getBytes(in),
                    getEncoding()));
        } catch (final Exception e) {
            throw new ExtractException(e);
        }
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }
}
