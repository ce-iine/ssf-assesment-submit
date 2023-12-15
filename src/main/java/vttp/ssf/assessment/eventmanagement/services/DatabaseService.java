package vttp.ssf.assessment.eventmanagement.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.JsonObject;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.Participant;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Service
public class DatabaseService {

    @Autowired
    RedisRepository redisRepo;

    Resource resource = new ClassPathResource("static/events.json");

    // TODO: Task 1
    public List<Event> readFile() throws IOException {//String fileName
        List<Event> eventList = new ArrayList<>();
        InputStream is = resource.getInputStream();
        JsonReader jsonReader = Json.createReader(is);
        JsonArray jsonArray = jsonReader.readArray();

        for (JsonValue jsonValue : jsonArray) { // for (JsonObject object: jsonArray.getValuesAs(JsonObject.class)){}
            JsonObject jsonObject = jsonValue.asJsonObject();
            Event event = new Event();
            event.setEventId(Integer.parseInt(jsonObject.get("eventId").toString()));
            event.setEventName(jsonObject.get("eventName").toString());
            event.setEventSize(Integer.parseInt(jsonObject.get("eventSize").toString()));
            event.setEventDate(Long.parseLong(jsonObject.get("eventDate").toString()));
            event.setParticipants(Integer.parseInt(jsonObject.get("participants").toString()));

            eventList.add(event);
        }

        System.out.println("READ EVENTS FILE>>>>>>" + eventList);

        for (int i=0; i<eventList.size(); i++){
            Event event = new Event();
            event = eventList.get(i);
            redisRepo.saveRecord(event);
        }

        return eventList;
    }


    public List<Event> allEventsFromRedis() throws Exception {
        List<Event> allEvents = redisRepo.allEventsFromRedis();
        return allEvents;
    }

    public Event getEvent(Long index) throws JsonMappingException, JsonProcessingException{
		Event event = redisRepo.getEvent(index);
		return event;
	}

    public Boolean checkAvail(Participant form, Event event){
        Boolean canPurchase = true;
        Integer ticketsPurchased = form.getNumOfTix();
        Integer eventSize= event.getEventSize();
        Integer participantCount = event.getParticipants();
        Integer shouldNotExceed = eventSize - participantCount;
        if (ticketsPurchased <= shouldNotExceed){
            return canPurchase;
        }
        return !canPurchase;

    }

    public void addParticipant(Integer ticketsPurchased, Integer index){
        redisRepo.updateParticipantCount();
    }



}
