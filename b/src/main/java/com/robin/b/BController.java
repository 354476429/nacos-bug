package com.robin.b;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BController {

	@PostMapping("/b/test")
	public String c() {
		return "bbbbbbb";
	}
	
}
