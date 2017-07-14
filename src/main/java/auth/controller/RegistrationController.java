package auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import auth.model.User;
import auth.service.UserService;
import auth.validator.UserValidator;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UserService userService;
	
	@InitBinder
	private void initBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(userValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String createAccount(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doCreateAccount(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "registration";
		}
		userService.addRoleToUser(user, "ROLE_USER");
		userService.save(user);
		return "redirect:/login";
	}

}
