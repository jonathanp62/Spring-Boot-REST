package net.jmp.spring.boot.rest;

/*
 * (#)PersonControllerIntegrationTests.java 0.1.0   01/30/2024
 *
 * @author    Jonathan Parker
 * @version   0.4.0
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
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerIntegrationTests {
    @Autowired
    private TestRestTemplate template;

    @Test
    void testNoId() throws Exception {
        final ResponseEntity<Person> response = template.getForEntity("/api/person", Person.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
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

        final ResponseEntity<Person> response = template.getForEntity("/api/person?id=1", Person.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedPerson);
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

        final ResponseEntity<Person> response = template.getForEntity("/api/person?id=2", Person.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedPerson);
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

        final ResponseEntity<Person> response = template.getForEntity("/api/person?id=3", Person.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedPerson);
    }

    @Test
    void testInvalidId() throws Exception {
        final var expectedApiError = new ApiError(
                "Not OK",
                "No person was found with id 4"
        );

        final ResponseEntity<ApiError> response = template.getForEntity("/api/person?id=4", ApiError.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(expectedApiError);
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

        final ResponseEntity<String> response = template.getForEntity("/api/people", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedJson);
    }
}
