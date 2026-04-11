package ru.javawebinar.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.repository.MealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    public static Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final static AtomicInteger counter = new AtomicInteger(0);

    static {
        for (Meal meal: MealsUtil.meals) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        }
    }

    @Override
    public List<Meal> getAll() {
        log.info("get all meals" + new ArrayList<>(repository.values()));
        return new ArrayList<>(repository.values());
    }

    @Override
    public Meal getById(int id) {
        log.info("get meal by id: {}", id);
        return repository.get(id);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save meal: {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            return repository.put(meal.getId(), meal);
        }
        return repository.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        log.info("delete meal: {}", id);
        repository.remove(id);
    }
}
