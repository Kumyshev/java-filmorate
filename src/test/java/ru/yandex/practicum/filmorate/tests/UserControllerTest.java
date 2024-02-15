package ru.yandex.practicum.filmorate.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void validUserFieldsTest() throws Exception {
        String user = "{\r\n  \"login\": \"dolore\",\r\n  \"name\": \"Nick Name\",\r\n  \"email\": \"mail@mail.ru\",\r\n  \"birthday\": \"1946-08-20\"\r\n}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void notValidUserFieldsTest() throws Exception {
        String user = "{\r\n  \"login\": \"dolore ullamco\",\r\n  \"name\": \"\",\r\n  \"email\": \"mail.ru\",\r\n  \"birthday\": \"1980-08-20\"\r\n}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
