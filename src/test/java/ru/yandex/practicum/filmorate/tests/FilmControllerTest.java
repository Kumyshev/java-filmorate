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
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void validFilmFieldsTest() throws Exception {
        String film = "{\r\n  \"name\": \"nisi eiusmod\",\r\n  \"description\": \"adipisicing\",\r\n  \"releaseDate\": \"1967-03-25\",\r\n  \"duration\": 100\r\n}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                .content(film)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void notValidFilmFieldsTest() throws Exception {
        String film = "{\r\n  \"name\": \"\",\r\n  \"description\": \"Description\",\r\n  \"releaseDate\": \"1900-03-25\",\r\n  \"duration\": 200\r\n}";

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                .content(film)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
