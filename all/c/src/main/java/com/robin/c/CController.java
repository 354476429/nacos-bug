package com.robin.c;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CController {

	@PostMapping("/c/test")
	public String c() {
		return "ccccccc";
	}
	
}
