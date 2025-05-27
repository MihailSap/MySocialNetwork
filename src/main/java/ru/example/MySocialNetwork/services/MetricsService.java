package ru.example.MySocialNetwork.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MetricsService {

    private final Map<String, Counter> likeCounters = new HashMap<>();
    private final MeterRegistry meterRegistry;
    private final PersonService personService;

    public void incrementPersonLikeCounter() {
        var personUsername = personService.getActivePerson().getUsername();
        var counter = likeCounters.get(personUsername);
        if (counter == null) {
            counter = Counter.builder("likes.counter")
                    .description("Кол-во лайков, которые поставил/убрал пользователь")
                    .tag("person", personUsername)
                    .register(meterRegistry);
            likeCounters.put(personUsername, counter);
        }
        counter.increment();
    }

    public void incrementExceptionCounter(String exceptionName) {
        var counter = Counter.builder("exceptions.counter")
                .description("Количество ошибок")
                .tag("exception", exceptionName)
                .register(meterRegistry);
        counter.increment();
    }
}