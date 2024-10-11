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
/*
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("mysql")
@DisabledInNativeImage
@DisabledInAotMode
class MySqlIntegrationTests {


	@LocalServerPort
	int port;

	@Autowired
	private VetRepository vets;

	@Autowired
	private RestTemplateBuilder builder;

	@Test
	void testFindAll() throws Exception {
		vets.findAll();
		vets.findAll(); // served from cache
	}

	@Test
	void testOwnerDetails() {
		//RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		//ResponseEntity<String> result = template.exchange(RequestEntity.get("/owners/1").build(), String.class);
		//assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		String baseUrl = System.getenv("SPRING_APP_BASE_URL");
		RestTemplate template = builder.rootUri(baseUrl).build();
		ResponseEntity<String> result = template.exchange(RequestEntity.get("/owners/1").build(), String.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	@Test
	void testOwnerDetails() {
		String baseUrl = System.getenv("SPRING_APP_BASE_URL");
		RestTemplate template = new RestTemplate(); // Create a new RestTemplate instance
		ResponseEntity<String> result = template.exchange(baseUrl + "/owners/1", HttpMethod.GET, null, String.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}*/

class MySqlIntegrationTests {

	private static String baseUrl;
	private RestTemplate restTemplate;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@BeforeAll
	static void setUp() {
		// Set the base URL to your already deployed app's URL
		baseUrl = System.getenv("SPRING_APP_BASE_URL");
		if (baseUrl == null || baseUrl.isEmpty()) {
			baseUrl = "http://localhost:8085"; // Set this to your actual service URL
		}
	}

	@BeforeAll
	void initRestTemplate() {
		restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
	}

	@Test
	void testFindAll() {
		ResponseEntity<String> result = restTemplate.getForEntity("/vets", String.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void testOwnerDetails() {
		ResponseEntity<String> result = restTemplate.exchange(RequestEntity.get("/owners/1").build(), String.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
