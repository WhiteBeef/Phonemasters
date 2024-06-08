package ru.phonemasters.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.phonemasters.dto.UserDTO;
import ru.phonemasters.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{page}")
    public String getOrdersPage(Model model, @PathVariable(required = false) Long page) {
        List<UserDTO> users = userService.findAllUsers();
        if (page == null) {
            page = 1L;
        }
        long pagesCount = users.size() / 10 + 1L;

        if (page > pagesCount) {
            return "redirect:/admin/users/" + pagesCount;
        }

        users = users.stream()
                .skip((page - 1) * 10)
                .limit(10)
                .toList();

        model.addAttribute("pagesCount", pagesCount);
        model.addAttribute("users", users);
        model.addAttribute("page", page);

        return "users/Users";
    }

    @GetMapping("")
    public String redirectToFirstPage() {
        return "redirect:orders/1";
    }

    @GetMapping("/")
    public String secondRedirectToFirstPage() {
        return "redirect:1";
    }

    @GetMapping("/create")
    public String getOrderCreatePage(HttpServletResponse response) {
        return "users/CreateUser";
    }
}
