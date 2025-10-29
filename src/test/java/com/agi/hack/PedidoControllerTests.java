package com.agi.hack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void pedidoNaoEncontrado() throws Exception {
		mockMvc.perform(get("/pedidos/9999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void pedidoCriadoComSucesso1() throws Exception {
		String json ="""
				{
				}
				""";

		mockMvc.perform(post("/pedidos")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isCreated());
	}

}