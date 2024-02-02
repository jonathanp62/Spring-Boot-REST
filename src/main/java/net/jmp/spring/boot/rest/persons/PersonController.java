package net.jmp.spring.boot.rest.persons;

/*
 * (#)PersonController.java 0.5.0   02/02/2024
 * (#)PersonController.java 0.4.0   01/29/2024
 * (#)PersonController.java 0.3.0   01/29/2024
 * (#)PersonController.java 0.2.0   01/27/2024
 *
 * @author    Jonathan Parker
 * @version   0.5.0
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.jmp.spring.boot.rest.ApiError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class PersonController {
    private final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final Map<Long, Person> people;

    PersonController() {
        super();

        this.people = Map.of(
            1L, new Person("OK", 1L, "Parker", "Jonathan", "555-123-4567", "jonathan@domain.com"),
            2L, new Person("OK", 2L, "Kirk", "James", "555-234-5678", "james@domain.com"),
            3L, new Person("OK", 3L, "McCoy", "Leonard", "555-345-6789", "leonard@domain.com")
        );
    }

    @GetMapping("/person")
    public ResponseEntity<Object> person(final @RequestParam(required = true) Long id) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("id    : {}", id);
            this.logger.debug("people: {}", this.people.entrySet());
        }

        if (this.people.containsKey(id))
            return new ResponseEntity<>(this.people.get(id), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ApiError("Not OK", String.format("No person was found with id %d", id)), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/people")
    public List<Person> people() {
        final var persons = new ArrayList<>(this.people.values());

        persons.sort(Comparator.comparing(Person::id));

        return persons;
    }
}
