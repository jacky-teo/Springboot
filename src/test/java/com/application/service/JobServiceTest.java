package com.application.service;

import com.application.exception.CreateJobException;
import com.application.exception.NoJobsListedException;
import com.application.exception.NoSuchJobException;
import com.application.model.Job;
import com.application.repo.JobRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class JobServiceTest {
    @MockBean
    private JobRepo jopRepo;

    private Job job;
    @Autowired
    private JobService jobService;

    @BeforeEach
    void setUp(){
        String[] firstJobDescription = {"Basic Salary - $2K + Commission", "Replacement leave for work on weekends", "Overseas Allowance", "Flight and Accommodations paid for overseas travels`"};
        String[] firstJobResponsibilities = {"Build value based customer relationships","Develop a strategic networking platform with clients"};
        String[] firstJobRequirements = {"Possess Degree","Excellent Spoken and Written English"};
        job = new Job(1L,
                "sales engineer",
                firstJobDescription,
                firstJobResponsibilities,
                firstJobRequirements,
                "Engineer",
                "Central",
                "9 Hours",
                "Finance",
                "$1000 - $20000");
    }

    @Test
    void NoJobsListed(){
        //Given setup precondition
        given(jopRepo.findAll()).willReturn(List.of());
        assertThrows(NoJobsListedException.class,()->{
            jobService.getAllJobs();
        });
    }

    @Test // Get all jobs currently
    void getAllJobs() {
        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job job2 = new Job(2L,
                "Product Manager",
                secondJobDescription,
                secondJobResponsibilities,
                secondJobRequirements,
                "Product",
                "North",
                "9 Hours",
                "Finance",
                "$1000 - $3000");

        //Given setup precondition
        given(jopRepo.findAll()).willReturn(List.of(job,job2));

        //When -- Action being tested
        List<Job> jobList = jobService.getAllJobs();
        System.out.println((jobList));
        assertNotNull(jobList);
        assertEquals(2,jobList.size());

    }

    @Test
    void getJobById() {
        //given
        given(jopRepo.findById(1L)).willReturn(Optional.of(job));

        //when
        Job savedJob = jobService.getJobById(job.getId());

        //then
        System.out.println((savedJob));
        assertNotNull(savedJob);
    }

    @Test
    void NoJobByIdFound(){
        given(jopRepo.findById(2L)).willReturn(Optional.empty());
        //When
        assertThrows(NoSuchJobException.class,()->{
            jobService.getJobById(2L);
        });
    }

    @Test
    void createJob() {
        //given
        given(jopRepo.save(job)).willReturn(job);
        System.out.println(jopRepo);
        System.out.println(jobService);

        //when
        Job savedJob = jobService.createJob(job);

        System.out.println(savedJob);
        //then
        assertNotNull(savedJob);
    }

    @Test // Empty Job Title upon creation
    void createJobFailed(){

        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job job2 = new Job(2L,
                "",
                secondJobDescription,
                secondJobResponsibilities,
                secondJobRequirements,
                "Product",
                "North",
                "9 Hours",
                "Finance",
                "$1000 - $3000");

        //
        given(jopRepo.save(job2)).willReturn(job2);

        //When
        assertThrows(CreateJobException.class,()->{
            jobService.createJob(job2);
        });

        verify(jopRepo,never()).save(any(Job.class ));

    }

    @Test
    void updateJob() {
        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job newJob = new Job(1L,
                "Singer/Song Writer",
                secondJobDescription,
                secondJobResponsibilities,
                secondJobRequirements,
                "Music",
                "North",
                "9 Hours",
                "Finance",
                "$1000 - $3000");

        //Given
        given(jopRepo.save(newJob)).willReturn(newJob);


        Job job = jobService.updateJob(newJob, 1L);
        System.out.println(job);

        assertEquals("Singer/Song Writer",job.getJob_title());
        assertEquals("Music",job.getJob_type());

    }

    @Test
    void uploadFile() {

    }
}