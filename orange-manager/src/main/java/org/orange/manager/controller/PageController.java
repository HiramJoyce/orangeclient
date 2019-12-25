package org.orange.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hiram 2019年12月20日 14:58
 */
@Controller 
public class PageController {

	@GetMapping("/") 
	public String home() {
		return "/home";
	}
}
