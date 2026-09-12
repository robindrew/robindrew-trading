package com.robindrew.taskmanager.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Spring Boot's DispatcherServlet is itself mapped to "/", so a plain @WebServlet can never claim
// that exact path (see HomeServlet) - this forwards the request server-side to the servlet that
// actually renders the page, keeping the site root ("/") showing the Home page without a
// client-visible redirect.
@Controller
public class HomeController {

    @GetMapping("/")
    public void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/Home").forward(request, response);
    }
}
