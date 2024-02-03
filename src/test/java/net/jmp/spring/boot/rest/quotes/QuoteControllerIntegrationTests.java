package net.jmp.spring.boot.rest.quotes;

/*
 * (#)QuoteControllerIntegrationTests.java  0.5.0   02/03/2024
 *
 * @author    Jonathan Parker
 * @version   0.5.0
 * @since     0.5.0
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

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteControllerIntegrationTests {
    @Autowired
    private TestRestTemplate template;

    @Test
    void testId3() {
        final var expectedString = "{\"type\":\"success\",\"value\":{\"id\":3,\"quote\":\"Spring has come quite a ways in addressing developer enjoyment and ease of use since the last time I built an application using it.\"}}";

        final ResponseEntity<String> response = template.getForEntity("/api/v5/3", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedString);
    }

    @Test
    void testAll() {
        final var expectedString = "[{\"type\":\"success\",\"value\":{\"id\":1,\"quote\":\"Working with Spring Boot is like pair-programming with the Spring developers.\"}},{\"type\":\"success\",\"value\":{\"id\":2,\"quote\":\"With Boot you deploy everywhere you can find a JVM basically.\"}},{\"type\":\"success\",\"value\":{\"id\":3,\"quote\":\"Spring has come quite a ways in addressing developer enjoyment and ease of use since the last time I built an application using it.\"}},{\"type\":\"success\",\"value\":{\"id\":4,\"quote\":\"Previous to Spring Boot, I remember XML hell, confusing set up, and many hours of frustration.\"}},{\"type\":\"success\",\"value\":{\"id\":5,\"quote\":\"Spring Boot solves this problem. It gets rid of XML and wires up common components for me, so I don't have to spend hours scratching my head just to figure out how it's all pieced together.\"}},{\"type\":\"success\",\"value\":{\"id\":6,\"quote\":\"It embraces convention over configuration, providing an experience on par with frameworks that excel at early stage development, such as Ruby on Rails.\"}},{\"type\":\"success\",\"value\":{\"id\":7,\"quote\":\"The real benefit of Boot, however, is that it's just Spring. That means any direction the code takes, regardless of complexity, I know it's a safe bet.\"}},{\"type\":\"success\",\"value\":{\"id\":8,\"quote\":\"I don't worry about my code scaling. Boot allows the developer to peel back the layers and customize when it's appropriate while keeping the conventions that just work.\"}},{\"type\":\"success\",\"value\":{\"id\":9,\"quote\":\"So easy it is to switch container in #springboot.\"}},{\"type\":\"success\",\"value\":{\"id\":10,\"quote\":\"Really loving Spring Boot, makes stand alone Spring apps easy.\"}},{\"type\":\"success\",\"value\":{\"id\":11,\"quote\":\"I have two hours today to build an app from scratch. @springboot to the rescue!\"}},{\"type\":\"success\",\"value\":{\"id\":12,\"quote\":\"@springboot with @springframework is pure productivity! Who said in #java one has to write double the code than in other langs? #newFavLib\"}}]";

        final ResponseEntity<String> response = template.getForEntity("/api/v5/all", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedString);
    }
}
