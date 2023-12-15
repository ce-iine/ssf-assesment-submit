package vttp.ssf.assessment.eventmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication // commandLine runner in day15class
public class EventmanagementApplication implements CommandLineRunner {
	// implements CommandLineRunner

	@Autowired
	DatabaseService databaseSvc;

	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}
	// TODO: Task 1

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Running command line runner");
		databaseSvc.readFile();
	}
	

}
