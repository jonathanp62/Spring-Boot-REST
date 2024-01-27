package net.jmp.spring.boot.rest;

/*
 * (#)PersonController.java 0.2.0   01/27/2024
 *
 * @author    Jonathan Parker
 * @version   0.2.0
 * @since     0.2.0
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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    private final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final Map<Long, Person> people;
    private final Person defaultPerson;

    PersonController() {
        super();

        this.people = Map.of(
            1L, new Person(1L, "Parker", "Jonathan", "555-123-4567", "jonathan@domain.com"),
            2L, new Person(2L, "Kirk", "James", "555-234-5678", "james@domain.com"),
            3L, new Person(2L, "McCoy", "Leonard", "555-345-6789", "leonard@domain.com")
        );

        this.defaultPerson = new Person(0L, "Last", "First", "Phone", "Email");
    }

    @GetMapping("/person")
    public Person person(final @RequestParam(required = false) String id) {
        if (id == null)
            return this.defaultPerson;

        final var longId = Long.parseLong(id);

        // @todo Learn how to enable debug level

        if (this.logger.isInfoEnabled()) {
            this.logger.info("id    : {}", id);
            this.logger.info("longId: {}", longId);
            this.logger.info("people: {}", this.people.entrySet());
        }

        return this.people.getOrDefault(longId, this.defaultPerson);  // @todo Return a 404
    }
}
