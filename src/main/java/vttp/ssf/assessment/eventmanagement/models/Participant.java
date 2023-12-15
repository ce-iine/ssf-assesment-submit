package vttp.ssf.assessment.eventmanagement.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {

    @NotEmpty (message = "Name is mandatory")
    @Size (min=5, max=25, message="Name must be between 5 and 25 characters")
    private String fullName;

    @DateTimeFormat (pattern="yyyy-MM-dd")
    @Past (message = "Date of birth must be before than today")
    private LocalDate dob;

    @NotEmpty (message = "Email is mandatory")
    @Email (message = "Invalid email format")
    @Size (max=50, message="email exceeded 50 characters")
    private String email;

    @NotEmpty (message = "Phone Number is mandatory")
    @Pattern (regexp = "(8|9)[0-9]{7}", message="Invalid phone number entered")
    private String mobileNo;

    private String gender;

    @Min(value=1, message="You must purchase at least 1 ticket")
	@Max(value=3, message="You can purchase a maximum of 3 tickets")
    @NotNull (message = "You must purchase at least 1 ticket")
    private Integer numOfTix;
    
    
}
