package org.orange.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hiram 2019年12月20日 14:58
 */
@Controller
@RequestMapping("/")
public class PageController {

	@GetMapping 
	public String home() {
		return "/home";
	}
}
