package me.jaegyu.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jaegyu.domain.User;
import me.jaegyu.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	private List<User> users = new ArrayList<>();

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepositiry.findByUserId(userId);
		if (user == null) {
			System.out.println("Login Failure");
			return "redirect:/users/loginForm";
		}

		if (!password.equals(user.getPassword())) {
			System.out.println("Login Failure");
			return "redirect:/users/loginForm";
		}

		System.out.println("Login Success");
		session.setAttribute("sessionedUser", user);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}

	@Autowired
	private UserRepository userRepositiry;

	@PostMapping("")
	public String create(User user) {
		System.out.println(user);
		userRepositiry.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepositiry.findAll());
		return "/user/list";
	}

	@GetMapping("form")
	public String form() {
		return "/user/form";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		User sessionedUser = (User) session.getAttribute("sessionedUser");

		if (sessionedUser == null) {
			return "redirect:/users/loginForm";
		}

		if (id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("자신의 정보만 수정 할 수 있습니다.");
		}

		model.addAttribute("user", userRepositiry.findOne(id));
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {

		User sessionedUser = (User) session.getAttribute("sessionedUser");

		if (sessionedUser == null) {
			return "redirect:/users/loginForm";
		}

		if (id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("자신의 정보만 수정 할 수 있습니다.");
		}

		User user = userRepositiry.findOne(id);
		user.update(updatedUser);
		userRepositiry.save(user);
		return "redirect:/users";
	}
}
