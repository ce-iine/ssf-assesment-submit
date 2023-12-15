package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.assessment.eventmanagement.models.Event;

import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping("/")
public class EventController {

	@Autowired
	DatabaseService databaseSvc;

	//TODO: Task 5
	@GetMapping("events/listing")
	public String displayEvents(Model model) throws Exception{
		List<Event> allEvents = databaseSvc.allEventsFromRedis();
		model.addAttribute("events", allEvents);
		return "view0";
	}



}
