package auth.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import auth.model.User;
import auth.model.UserRole;
import auth.service.UserRoleService;
import auth.service.UserService;
import auth.validator.UserRolesValidator;

@Controller
@RequestMapping("/admin/users/{id}/edit-roles")
@SessionAttributes(value = { "user" })
public class EditUserRolesController {

	@Autowired
	UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	UserRolesValidator userRoleValidator;

	@InitBinder
	private void initBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(userRoleValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getEditUserRoles(@PathVariable Long id, Model model) {
		User user = userService.findUserById(id);
		model.addAttribute("user", user);

		return "edituser";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String editUserRoles(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model,
			@PathVariable Long id) {

		if (bindingResult.hasErrors()) {
			return "edituser";
		}
		userService.save(user);
		return "redirect:/admin/users/";
	}

	@ModelAttribute("roles")
	public List<UserRole> initializeProfiles() {
		return userRoleService.findAll();
	}

}
