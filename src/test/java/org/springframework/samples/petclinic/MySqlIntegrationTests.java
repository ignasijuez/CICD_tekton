/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.http.HttpMethod;


import org.springframework.beans.factory.annotation.Value;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

@Disabled
class MySqlIntegrationTests {

	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		// Initialize the RestTemplate with the base URL for the Kubernetes service
		String baseUrl = "http://springboot-service:80"; // Use the service name and port in Kubernetes
		restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
	}

	@Test
	void testFindAll() {
		// Test fetching all vets
		ResponseEntity<String> result = restTemplate.getForEntity("/vets", String.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void testOwnerDetails() {
		// Test fetching details for owner with ID 1
		ResponseEntity<String> result = restTemplate.exchange(RequestEntity.get("/owners/1").build(), String.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
