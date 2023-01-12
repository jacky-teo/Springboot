package com.application.repo;

import com.application.model.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;


@DataJpaTest // This annotation allows me to test repo layer only. Persist data into database at the time of its execution, the data will be flushed after method is used.
class JobRepoTest {
    @Autowired
    private JobRepo jobRepo;


    @BeforeEach
    void setUp(){
        String[] firstJobDescription = {"Basic Salary - $2K + Commission", "Replacement leave for work on weekends", "Overseas Allowance", "Flight and Accommodations paid for overseas travels`"};
        String[] firstJobResponsibilities = {"Build value based customer relationships","Develop a strategic networking platform with clients"};
        String[] firstJobRequirements = {"Possess Degree","Excellent Spoken and Written English"};

        Job job= Job.builder()
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

    }

    @Test
    void testFindById(){
        String job_title = "sales engineer";
        Job job = jobRepo.findById(1L).get();
        assertEquals("Passed Test Case",job_title,job.getJob_title());
    }

    @Test
    public void testSaveJob(){
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

    @Test
    void testGetAll(){

    }
}