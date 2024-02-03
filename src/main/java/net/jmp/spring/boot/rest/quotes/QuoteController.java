package net.jmp.spring.boot.rest.quotes;

/*
 * (#)QuoteController.java  0.5.0   02/02/2024
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

import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v5")
public class QuoteController {
    private static final Quote NONE = new Quote("None");
    private static final Random RANDOMIZER = new Random();

    private final QuoteRepository repository;

    public QuoteController(final QuoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<QuoteResource> getAll() {
        return repository.findAll().stream()
                .map(quote -> new QuoteResource(quote, "success"))
                .toList();
    }

    @GetMapping("/{id}")
    public QuoteResource getOne(final @PathVariable Long id) {
        return repository.findById(id)
                .map(quote -> new QuoteResource(quote, "success"))
                .orElse(new QuoteResource(NONE, "Quote " + id + " does not exist"));
    }

    @GetMapping("/random")
    public QuoteResource getRandomOne() {
        return getOne(nextLong(1, repository.count() + 1));
    }

    private long nextLong(final long lowerRange, final long upperRange) {
        return (long) (RANDOMIZER.nextDouble() * (upperRange - lowerRange)) + lowerRange;
    }
}
