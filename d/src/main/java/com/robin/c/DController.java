package com.robin.c;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DController {

	@PostMapping("/d/test")
	public String c() {
		return "dddddd";
	}
	
}
