package com.lawencon.jobportaladmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeLeafController {

	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}
}
