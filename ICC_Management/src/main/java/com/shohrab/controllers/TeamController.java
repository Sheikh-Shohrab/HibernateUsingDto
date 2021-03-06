package com.shohrab.controllers;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;



import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.shohrab.requestDto.TeamRequestDto;
import com.shohrab.service.CountryService;
import com.shohrab.service.TeamService;

@Controller
public class TeamController {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private CountryService countryService;
	
	@GetMapping("/team/add")
	public String addTeam_GET(Model model) {
		model.addAttribute("countries", countryService.getCountry());
		model.addAttribute("pageTitle", "Add Team");
		model.addAttribute("team", new TeamRequestDto());
		model.addAttribute("message", "Please add a team");
		return "team/add";
	}
	
	@PostMapping("/team/add")
	public String addTeam(RedirectAttributes redirectAttributes, 
			@ModelAttribute(name = "team") TeamRequestDto teamRequestDto) {
		System.out.println("It's my country: "+teamRequestDto.getCountryId());
		teamService.addTeam(teamRequestDto);
		redirectAttributes.addFlashAttribute("message", "Team added successfully");
		return "redirect:/team/show-all";
	}

	@GetMapping("/team/show-all")
	public String showAll_GET(Model model, @ModelAttribute("message") String message) {
		
		if(message != null) {
			model.addAttribute("message", message);
		}else {
			model.addAttribute("message", "Showing all teams");
		}
		model.addAttribute("pageTitle", "Team List");
		model.addAttribute("teams", teamService.getTeam());
		
		return "team/show-all";
	}
}
