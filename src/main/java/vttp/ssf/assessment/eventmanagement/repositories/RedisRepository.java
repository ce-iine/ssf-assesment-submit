package vttp.ssf.assessment.eventmanagement.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

	@Autowired @Qualifier("eventbean")
	private RedisTemplate<String, String> template = new RedisTemplate<>();

	String key = "eventList";

	// TODO: Task 2
	public void saveRecord(Event event){
		ListOperations<String,String> list = template.opsForList();
		String record = event.toJson().toString();
		if(template.opsForList().size(key)<4){
		list.leftPush(key, record);
		}
	}

	// TODO: Task 3
	public Long getNumberofEvents(){
		Long cartLen = template.opsForList().size(key);
		return cartLen;
	}

	// TODO: Task 4
	public Event getEvent(Long index) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
            String json = template.opsForList().index(key, index).toString();
            return mapper.readValue(json, Event.class); // returns Event
	}

	public List<Event> allEventsFromRedis() throws Exception {
        Event event = new Event();
        List<Event> allEvents = new ArrayList<Event>();

		for (long i=0; i<template.opsForList().size(key); i++){
			event = getEvent(i);
			allEvents.add(event);
		}
        return allEvents;
    }

	public Event updateParticipantCount(Integer ticketsPurchased, Long index) throws JsonMappingException, JsonProcessingException{
		Event updateEvent = getEvent(index);
		int currCount = updateEvent.getParticipants();
		currCount += ticketsPurchased;
		updateEvent.setParticipants(currCount);

		//update in redis
		return updateEvent;
	}


}
