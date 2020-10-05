package fi.pellikka.s2020Bookstore.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.pellikka.s2020Bookstore.domain.RegisterUser;
import fi.pellikka.s2020Bookstore.domain.User;
import fi.pellikka.s2020Bookstore.domain.UserRepository;

/*
 * Controller to user specific requests
 * MPellikka*/


@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/registerNewUser")
	public String addNewUser(Model model) {
		System.out.println("registerNewUser");
		model.addAttribute("registerUser", new RegisterUser());
		return "register";

	}
	//${}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("registerUser") RegisterUser registerUser, 
			BindingResult bindingResult) {
		
		System.out.println("saveUser-metodi");
		if (!bindingResult.hasErrors()) { // validation errors
			if (registerUser.getPassword().equals(registerUser.getPwdcheck())) { // check password match
				System.out.println("pwd ovat samoja");
				String pwd = registerUser.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);

				User newUser = new User();
				
				newUser.setFirstname(registerUser.getFirstName());
				newUser.setLastname(registerUser.getLastName());
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(registerUser.getUsername());
				newUser.setRole(registerUser.getRole().toUpperCase());
				if (userRepository.findByUsername(registerUser.getUsername()) == null) { // Check if user exists
					userRepository.save(newUser);
				} else {
					bindingResult.rejectValue("username", "err.username", "Username already exists");
					System.out.println("username is already in use");
					return "registration";
				}
			} else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");
				return "registration";
			}
		} else {
			return "registration";
		}
		return "redirect:/login";
	}

}
