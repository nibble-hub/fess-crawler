/*
 * Copyright 2012-2019 CodeLibs Project and the Others.
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
package org.codelibs.fess.crawler.client.storage;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.codelibs.core.io.InputStreamUtil;
import org.codelibs.fess.crawler.container.StandardCrawlerContainer;
import org.codelibs.fess.crawler.entity.ResponseData;
import org.codelibs.fess.crawler.exception.ChildUrlsException;
import org.codelibs.fess.crawler.exception.CrawlerSystemException;
import org.codelibs.fess.crawler.helper.impl.MimeTypeHelperImpl;
import org.dbflute.utflute.core.PlainTestCase;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;

import io.minio.MinioClient;

/**
 * @author shinsuke
 * 
 */
public class StorageClientTest extends PlainTestCase {
    public StorageClient storageClient;

    private GenericContainer minioServer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String accessKey = "AKIAIOSFODNN7EXAMPLE";
        String secretKey = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";
        String bucketName = "fess";

        minioServer = new GenericContainer<>("minio/minio")//
                .withEnv("MINIO_ACCESS_KEY", accessKey)//
                .withEnv("MINIO_SECRET_KEY", secretKey)//
                .withExposedPorts(9000)//
                .withCommand("server /data");
        minioServer.start();

        Integer mappedPort = minioServer.getFirstMappedPort();
        Testcontainers.exposeHostPorts(mappedPort);
        String endpoint = String.format("http://%s:%s", minioServer.getContainerIpAddress(), mappedPort);
        System.out.println("endpoint: " + endpoint);

        StandardCrawlerContainer container = new StandardCrawlerContainer().singleton("mimeTypeHelper", MimeTypeHelperImpl.class)//
                .singleton("storageClient", StorageClient.class);
        storageClient = container.getComponent("storageClient");
        Map<String, Object> params = new HashMap<>();
        params.put("endpoint", endpoint);
        params.put("accessKey", accessKey);
        params.put("secretKey", secretKey);
        params.put("bucket", bucketName);
        storageClient.setInitParameterMap(params);

        MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey);
        minioClient.makeBucket(bucketName);
        minioClient.putObject(bucketName, "file1.txt", new ByteArrayInputStream("file1".getBytes()), null, new HashMap<>(), null, null);
        minioClient.putObject(bucketName, "dir1/file2.txt", new ByteArrayInputStream("file2".getBytes()), null, new HashMap<>(), null,
                null);
        minioClient.putObject(bucketName, "dir1/dir2/file3.txt", new ByteArrayInputStream("file3".getBytes()), null, new HashMap<>(), null,
                null);
        minioClient.putObject(bucketName, "dir3/file4.txt", new ByteArrayInputStream("file4".getBytes()), null, new HashMap<>(), null,
                null);
    }

    @Override
    protected void tearDown() throws Exception {
        minioServer.stop();
        super.tearDown();
    }

    public void test_doGet() throws Exception {
        try (final ResponseData responseData = storageClient.doGet("storage://file1.txt")) {
            assertEquals("storage://file1.txt", responseData.getUrl());
            assertEquals("text/plain", responseData.getMimeType());
            assertEquals("file1", new String(InputStreamUtil.getBytes(responseData.getResponseBody())));
            assertEquals(5, responseData.getContentLength());
        }
        try (final ResponseData responseData = storageClient.doGet("storage://dir1/file2.txt")) {
            assertEquals("storage://dir1/file2.txt", responseData.getUrl());
            assertEquals("text/plain", responseData.getMimeType());
            assertEquals("file2", new String(InputStreamUtil.getBytes(responseData.getResponseBody())));
            assertEquals(5, responseData.getContentLength());
        }
        try (final ResponseData responseData = storageClient.doGet("storage://dir1/dir2/file3.txt")) {
            assertEquals("storage://dir1/dir2/file3.txt", responseData.getUrl());
            assertEquals("text/plain", responseData.getMimeType());
            assertEquals("file3", new String(InputStreamUtil.getBytes(responseData.getResponseBody())));
            assertEquals(5, responseData.getContentLength());
        }
        try (final ResponseData responseData = storageClient.doGet("storage://dir3/file4.txt")) {
            assertEquals("storage://dir3/file4.txt", responseData.getUrl());
            assertEquals("text/plain", responseData.getMimeType());
            assertEquals("file4", new String(InputStreamUtil.getBytes(responseData.getResponseBody())));
            assertEquals(5, responseData.getContentLength());
        }
        try (final ResponseData responseData = storageClient.doGet("storage://")) {
            fail();
        } catch (ChildUrlsException e) {
            String[] values = e.getChildUrlList().stream().map(d -> d.getUrl()).sorted().toArray(n -> new String[n]);
            assertEquals(3, values.length);
            assertEquals("storage://dir1/", values[0]);
            assertEquals("storage://dir3/", values[1]);
            assertEquals("storage://file1.txt", values[2]);
        }
        try (final ResponseData responseData = storageClient.doGet("storage://dir1/")) {
            fail();
        } catch (ChildUrlsException e) {
            String[] values = e.getChildUrlList().stream().map(d -> d.getUrl()).sorted().toArray(n -> new String[n]);
            assertEquals(2, values.length);
            assertEquals("storage://dir1/dir2/", values[0]);
            assertEquals("storage://dir1/file2.txt", values[1]);
        }
        try (final ResponseData responseData = storageClient.doGet("storage://dir1/dir2/")) {
            fail();
        } catch (ChildUrlsException e) {
            String[] values = e.getChildUrlList().stream().map(d -> d.getUrl()).sorted().toArray(n -> new String[n]);
            assertEquals(1, values.length);
            assertEquals("storage://dir1/dir2/file3.txt", values[0]);
        }
        try (final ResponseData responseData = storageClient.doGet("storage://dir3/")) {
            fail();
        } catch (ChildUrlsException e) {
            String[] values = e.getChildUrlList().stream().map(d -> d.getUrl()).sorted().toArray(n -> new String[n]);
            assertEquals(1, values.length);
            assertEquals("storage://dir3/file4.txt", values[0]);
        }
        try (final ResponseData responseData = storageClient.doGet("storage://none")) {
            fail();
        } catch (ChildUrlsException e) {
            String[] values = e.getChildUrlList().stream().map(d -> d.getUrl()).sorted().toArray(n -> new String[n]);
            assertEquals(0, values.length);
        }
        try (final ResponseData responseData = storageClient.doGet("")) {
            fail();
        } catch (CrawlerSystemException e) {
            // nothing
        }
    }

    public void test_doHead() throws Exception {
        try (final ResponseData responseData = storageClient.doHead("storage://file1.txt")) {
            assertEquals("storage://file1.txt", responseData.getUrl());
            assertEquals("application/octet-stream", responseData.getMimeType());
            assertNull(responseData.getResponseBody());
        }
        try (final ResponseData responseData = storageClient.doHead("storage://dir1/file2.txt")) {
            assertEquals("storage://dir1/file2.txt", responseData.getUrl());
            assertEquals("application/octet-stream", responseData.getMimeType());
            assertNull(responseData.getResponseBody());
        }
        try (final ResponseData responseData = storageClient.doHead("storage://dir1/dir2/file3.txt")) {
            assertEquals("storage://dir1/dir2/file3.txt", responseData.getUrl());
            assertEquals("application/octet-stream", responseData.getMimeType());
            assertNull(responseData.getResponseBody());
        }
        try (final ResponseData responseData = storageClient.doHead("storage://dir3/file4.txt")) {
            assertEquals("storage://dir3/file4.txt", responseData.getUrl());
            assertEquals("application/octet-stream", responseData.getMimeType());
            assertNull(responseData.getResponseBody());
        }
        try (final ResponseData responseData = storageClient.doHead("storage://")) {
            assertNull(responseData);
        }
        try (final ResponseData responseData = storageClient.doHead("storage://dir1/")) {
            assertNull(responseData);
        }
        try (final ResponseData responseData = storageClient.doHead("storage://dir1/dir2/")) {
            assertNull(responseData);
        }
        try (final ResponseData responseData = storageClient.doHead("storage://dir3/")) {
            assertNull(responseData);
        }
        try (final ResponseData responseData = storageClient.doHead("storage://none")) {
            assertNull(responseData);
        }
        try (final ResponseData responseData = storageClient.doHead("")) {
            fail();
        } catch (CrawlerSystemException e) {
            // nothing
        }
    }
}
