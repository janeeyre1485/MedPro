package auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import auth.model.User;
import auth.model.UserRole;
import auth.service.UserRoleService;
import auth.service.UserService;

@Controller
@RequestMapping("/admin/users/{id}/edit-roles")
@SessionAttributes(value = { "user" })
public class EditUserRolesController {

	@Autowired
	UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@RequestMapping(method = RequestMethod.GET)
	public String getEditUserRoles(@PathVariable Long id, Model model) {
		User user;
		user = userService.findUserById(id);
		model.addAttribute("user", user);

		return "edituser";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String editUserRoles(@ModelAttribute("user") User user, BindingResult result, Model model,
			@PathVariable Long id) {

		userService.save(user);
		return "redirect:/admin/users/";
	}

	@ModelAttribute("roles")
	public List<UserRole> initializeProfiles() {
		return userRoleService.findAll();
	}

}
