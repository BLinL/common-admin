package com.example.spst.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "zhangsan")
                        .param("password", "123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

//        Map<String, String> userJSON = Map.of("username", "zhangsan", "password", "123");
//        String s = new ObjectMapper().writeValueAsString(userJSON);
//        HttpEntity<String> objectHttpEntity = new HttpEntity<>(s, null);
//
//        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:8001/api/account/create",objectHttpEntity, String.class);
//        assertTrue(stringResponseEntity.getStatusCode().is2xxSuccessful());
//        System.out.println(stringResponseEntity.getBody());
    }
}