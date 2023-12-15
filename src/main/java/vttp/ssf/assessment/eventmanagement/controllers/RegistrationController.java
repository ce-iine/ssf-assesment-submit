package vttp.ssf.assessment.eventmanagement.controllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Participant;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;
import vttp.ssf.assessment.eventmanagement.models.Event;

@Controller
@RequestMapping("/")
public class RegistrationController {

    @Autowired
    DatabaseService databaseSvc;

    @GetMapping("/events/register/{id}") /// {id}
    public String register(@PathVariable String id, Model model, HttpSession session) throws Exception {
        Event event = databaseSvc.getEvent(Long.parseLong(id));
        session.setAttribute("event", event);

        Participant participant = new Participant();
        model.addAttribute("event", event);
        model.addAttribute("participant", participant);
        return "eventregister";
    }

    // TODO: Task 7
    @PostMapping("/registration/register")
    public String processRegistration(@Valid @ModelAttribute("participant") Participant form, BindingResult result,
            HttpSession session, Model model) {

        Event event = (Event) session.getAttribute("event");

        if (form.getDob().isBefore(LocalDate.now().minusYears(100)) ||
                form.getDob().isAfter(LocalDate.now().minusYears(10))) {
            FieldError err = new FieldError("form", "dob", "You must be at least 21 years old!");
            result.addError(err);
        }

        if (result.hasErrors()) {
            model.addAttribute("event", event);
            return "eventregister";
        }

        Integer ticketsPurchased = form.getNumOfTix();
        databaseSvc.addParticipant(ticketsPurchased, event.getEventId());

        Boolean canPurchase = databaseSvc.checkAvail(form, event);

        if (canPurchase) {
            model.addAttribute("event", event);
            return "SuccessRegistration";
        }

        if (!canPurchase) {
            return "ErrorRegistration";
        }

        return "";
    }

}
