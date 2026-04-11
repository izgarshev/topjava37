package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.repository.MealRepository;
import ru.javawebinar.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository mealRepository;

    @Override
    public void init() throws ServletException {
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("meals do post");
        Meal meal = new Meal();
        meal.setId(getId(req));
        meal.setDescription(req.getParameter("description"));
        meal.setDateTime(LocalDateTime.parse(req.getParameter("dateTime")));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));
        mealRepository.save(meal);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        if (action == null) action = "all";

        switch (action) {
            case "delete":
                Integer id = getId(request);
                if (id != null) mealRepository.delete(id);
                response.sendRedirect("meals");
                break;
            case "update":
                request.setAttribute("meal", mealRepository.getById(getId(request)));
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "create":
                request.setAttribute("meal", new Meal(null, LocalDateTime.now(), "default", 541));
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("meals", MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDay));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

//        response.sendRedirect("meals.jsp");
    }

    private Integer getId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}