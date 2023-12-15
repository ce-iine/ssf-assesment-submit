package vttp.ssf.assessment.eventmanagement.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    Integer eventId;
    String eventName;
    Integer eventSize;
    Long eventDate;
    Integer participants;

    public JsonObject toJson(){
        JsonObject builder = Json.createObjectBuilder()
            .add("eventId", this.getEventId().toString())
            .add("eventName", this.getEventName())
            .add("eventSize", this.getEventSize().toString())
            .add("eventDate", this.getEventDate().toString())
            .add("participants", this.getParticipants().toString())
            .build();
        return builder;
    }
}
