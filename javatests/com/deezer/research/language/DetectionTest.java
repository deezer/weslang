/*
 * Copyright 2014-present Deezer.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.deezer.research.language;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


// See http://docs.spring.io/spring/docs/3.0.x/api/org/springframework/web/client/RestTemplate.html for documentation
// See https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-web-secure/src/test/java/sample/ui/secure/SampleSecureApplicationTests.java for an example
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
    classes = {DetectionConfiguration.class, MetricRepositoryAutoConfiguration.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles({"both"})
public class DetectionTest {
  @Value("${local.server.port}")
  private int port;
  private String baseUrl;
  private TestRestTemplate rest;

  @Before
  public void testSetup() {
    this.rest = new TestRestTemplate();
    this.baseUrl = "http://localhost:" + this.port;
  }

  public String getUrl(String path) {
    return this.baseUrl + path;
  }

  @Test
  public void testDetectionNoTextArgument() {
    ResponseEntity<String> response = this.rest.getForEntity(
      getUrl("/detect"), String.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void testDetectionEmptyText() {
    ResponseEntity<DetectionResult> response = this.rest.getForEntity(
      getUrl("/detect?q="), DetectionResult.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(new DetectionResult("UNKNOWN", 0.0), response.getBody());
  }

  @Test
  public void testDetectionEnglishGet() {
    ResponseEntity<DetectionResult> response = this.rest.getForEntity(
      getUrl("/detect?q=this is a text in english"), DetectionResult.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    DetectionResult result = response.getBody();
    assertEquals("en", result.getLanguage());
    assertTrue(result.getConfidence() > 0.8);
  }

  @Test
  public void testDetectionEnglishPost() {
    HttpHeaders  headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity req = new HttpEntity<>("q=this is a text in english", headers);
    ResponseEntity<DetectionResult> response = this.rest.postForEntity(
      getUrl("/detect"), req, DetectionResult.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    DetectionResult result = response.getBody();
    assertEquals("en", result.getLanguage());
    assertTrue(result.getConfidence() > 0.8);
  }
}
