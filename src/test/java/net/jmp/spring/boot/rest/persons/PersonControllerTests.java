package net.jmp.spring.boot.rest.persons;

/*
 * (#)PersonControllerTests.java    0.5.0   02/02/2024
 * (#)PersonControllerTests.java    0.4.0   01/29/2024
 *
 * @author    Jonathan Parker
 * @version   0.5.0
 * @since     0.4.0
 *
 * MIT License
 *
 * Copyright (c) 2024 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void testNoId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/person"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testId1() throws Exception {
        final var expectedPerson = new Person(
                "OK",
                1L,
                "Parker",
                "Jonathan",
                "555-123-4567",
                "jonathan@domain.com"
        );

        final var objectMapper = new ObjectMapper();
        final var expectedJson = objectMapper.writeValueAsString(expectedPerson);

        mvc.perform(MockMvcRequestBuilders.get("/api/v2/person?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    void testId2() throws Exception {
        final var expectedPerson = new Person(
                "OK",
                2L,
                "Kirk",
                "James",
                "555-234-5678",
                "james@domain.com"
        );

        final var objectMapper = new ObjectMapper();
        final var expectedJson = objectMapper.writeValueAsString(expectedPerson);

        mvc.perform(MockMvcRequestBuilders.get("/api/v2/person?id=2"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    void testId3() throws Exception {
        final var expectedPerson = new Person(
                "OK",
                3L,
                "McCoy",
                "Leonard",
                "555-345-6789",
                "leonard@domain.com"
        );

        final var objectMapper = new ObjectMapper();
        final var expectedJson = objectMapper.writeValueAsString(expectedPerson);

        mvc.perform(MockMvcRequestBuilders.get("/api/v2/person?id=3"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }

    @Test
    void testInvalidId() throws Exception {
        final var expectedApiError = new ApiError(
                "Not OK",
                "No person was found with id 4"
        );

        final var objectMapper = new ObjectMapper();
        final var expectedJson = objectMapper.writeValueAsString(expectedApiError);

        mvc.perform(MockMvcRequestBuilders.get("/api/v2/person?id=4"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedJson));
    }

    @Test
    void testPeople() throws Exception {
        final var expectedPeople = List.of(
                new Person("OK", 1L, "Parker", "Jonathan", "555-123-4567", "jonathan@domain.com"),
                new Person("OK", 2L, "Kirk", "James", "555-234-5678", "james@domain.com"),
                new Person("OK", 3L, "McCoy", "Leonard", "555-345-6789", "leonard@domain.com")
        );

        final var objectMapper = new ObjectMapper();
        final var expectedJson = objectMapper.writeValueAsString(expectedPeople);

        mvc.perform(MockMvcRequestBuilders.get("/api/v2/people"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));
    }
}
