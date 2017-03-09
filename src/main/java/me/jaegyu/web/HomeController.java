package me.jaegyu.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jaegyu.domain.QuestionRepository;

@Controller
public class HomeController {

	@Resource
	private QuestionRepository repository;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("questions", repository.findAll());
		return "index";
	}
}
