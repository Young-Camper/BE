package com.youngcamp.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class ServerApplicationTests {

	@Autowired
	private DataSource dataSource;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		// 애플리케이션 컨텍스트 로드 여부를 확인하는 기본 테스트
		assertThat(dataSource).isNotNull();
	}

	@Test
	void testDatabaseConnection() throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			assertThat(connection.isValid(1)).isTrue();
		}
	}

	@Test
	void testDatabaseCrudOperations() {
		// 실제 CRUD 테스트를 여기에 작성하세요
	}

	@Test
	void testSwaggerUI() {
		String url = "http://localhost:" + port + "/swagger-ui.html";
		String response = this.restTemplate.getForObject(url, String.class);
		assertThat(response).contains("Swagger UI");
	}

	@Test
	public void testGetTest() throws Exception {
		mockMvc.perform(get("/test"))
				.andExpect(status().isOk())
				.andExpect(content().string("test success"));
	}
}
