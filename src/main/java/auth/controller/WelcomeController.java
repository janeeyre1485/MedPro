package auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String welcomePage(Model model) {
		return "welcome";
	}
}