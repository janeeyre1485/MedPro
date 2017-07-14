package auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import auth.model.User;
import auth.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {
	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String getAdminPage(Model model) {
		List<User> userList;
		userList = userService.findAll();

		model.addAttribute("users", userList);
		return "admin";
	}
}
