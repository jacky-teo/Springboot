package com.application.repo;

import com.application.model.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JobRepoTest {
    @Autowired
    private JobRepo jobRepo;

    @Test
    public void saveJob(){
        String[] firstJobDescription = {"Basic Salary - $2K + Commission", "Replacement leave for work on weekends", "Overseas Allowance", "Flight and Accommodations paid for overseas travels`"};
        String[] firstJobResponsibilities = {"Build value based customer relationships","Develop a strategic networking platform with clients"};
        String[] firstJobRequirements = {"Possess Degree","Excellent Spoken and Written English"};
        Job job = Job.builder()
                .id(1L)
                .job_title("sales engineer")
                .job_description(firstJobDescription)
                .job_responsibility(firstJobResponsibilities)
                .requirements(firstJobRequirements)
                .job_type("Engineer")
                .classification("Finance")
                .location("Central")
                .working_hours("9 Hours")
                .salary("$1000 - $20000")
                .build();

        jobRepo.save(job);
    }
}