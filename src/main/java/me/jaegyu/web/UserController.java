package me.jaegyu.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jaegyu.domain.User;
import me.jaegyu.domain.UserRepository;

@Controller
@RequestMapping("")
public class UserController {
	private List<User> users = new ArrayList<>();
	
	@Autowired
	private UserRepository userRepositiry;

	@PostMapping("")
	public String create(User user) {
		System.out.println(user);
		userRepositiry.save(user);
		return "redirect:/users/list";
	}

	@GetMapping("/users")
	public String list(Model model) {
		model.addAttribute("users",userRepositiry.findAll());
		return "/user/list";
	}
}
