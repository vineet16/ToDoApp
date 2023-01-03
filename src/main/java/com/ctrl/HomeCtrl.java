package com.ctrl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.entity.ToDo;

@Controller
public class HomeCtrl {

	@Autowired
	ServletContext context;

	@RequestMapping("/home")
	public String home(@RequestParam Map<String, String> reqParam, Model m) {
		List<ToDo> list = (List<ToDo>) context.getAttribute("todolist");
		
		m.addAttribute("todos", list);
		String msg = reqParam.getOrDefault("message", null);
		if(msg != null)
			m.addAttribute("message", msg);
		
		m.addAttribute("page", "home");
		return "home";
	}

	@RequestMapping("/add")
	public String addTodo(Model m) {
		ToDo t = new ToDo();

		m.addAttribute("todo", t);
		m.addAttribute("page", "add");

		return "home";
	}

	@RequestMapping(value = "/saveTodo", method = RequestMethod.POST)
	public ModelAndView saveTodo(@ModelAttribute("todo") ToDo t, Model m) {
		System.out.println(t);
		t.setTodoDate(new Date());

		List<ToDo> list = (List<ToDo>) context.getAttribute("todolist");
		list.add(t);

		m.addAttribute("message", "successfully added...");

		return new ModelAndView("redirect:/home");
	}
}
