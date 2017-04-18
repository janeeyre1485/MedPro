package auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import auth.dao.UserRepository;
import auth.model.User;
import auth.service.UserService;
import auth.validator.UserValidator;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	private UserService userService;


	@Autowired
	private UserValidator userValidator;

	@RequestMapping("/login")
	public String loginPage(Model model) {
		return "login";
	}

	@RequestMapping("/")
	public String homePage(Model model) {
		return "home";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String createAccount(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String doCreateAccount(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		userValidator.validate(user, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		userService.save(user);
		return "redirect:/login";
	}

	@RequestMapping("/welcome")
	public String welcomePage() {
		return "welcome";
	}
	
	@RequestMapping("/home")
	public String homePage() {
		return "home";
	}

}
