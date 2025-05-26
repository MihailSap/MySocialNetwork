package ru.example.MySocialNetwork.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.example.MySocialNetwork.exceptions.TooManyRequestsException;
import ru.example.MySocialNetwork.services.PersonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequestTimeLimitAspect {

    private final PersonService personService;
    private final Map<Long, List<Long>> personRequestTimes = new HashMap<>();

    @Value("${request-count}")
    private int max_count;

    @Value("${waiting-time}")
    private long waiting_time;

    @Before("@annotation(ru.example.MySocialNetwork.aspects.RequestTimeLimit)")
    public void checkRequestCountInTime(){
        var personId = personService.getActivePerson().getId();
        var now = System.currentTimeMillis();

        var times = personRequestTimes.get(personId);
        if (times == null) {
            times = new ArrayList<>();
            personRequestTimes.put(personId, times);
        }

        times.removeIf(t -> now - t > waiting_time);

        if (times.size() >= max_count) {
            throw new TooManyRequestsException("Нельзя выполнять более четырёх действий с лайками за минуту, теперь ожидайте!");
        }

        times.add(now);
    }
}
