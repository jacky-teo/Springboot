package com.application.service;

import com.application.exception.CreateJobException;
import com.application.exception.NoJobsListedException;
import com.application.exception.NoSuchJobException;
import com.application.model.Job;
import com.application.repo.JobRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import static org.mockito.Mockito.*;

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
        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        job = Job.builder()
                .id(1L)
                .job_title("Engineer")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("$1000 - $3000")
                .working_hours("9 Hours")
                .location("North")
                .classification("Finance")
                .build();
    }

    @Test
    void getJobById() {
        String jobTitle = "Engineer";
        //given
        given(jopRepo.findById(1L)).willReturn(Optional.of(job));
        //when
        Job savedJob = jobService.getJobById(1L);
        //then
        assertNotNull(savedJob);
        assertEquals(jobTitle,savedJob.getJob_title());
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


        Job job2 = mock(Job.class);
        //Given setup precondition
        given(jopRepo.findAll()).willReturn(List.of(job,job2));
        //When -- Action being tested
        List<Job> jobList = jobService.getAllJobs();

        assertNotNull(jobList);
        assertEquals(2,jobList.size());

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
    void testCreateJob() {

        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job jobToSave = Job.builder()
                .job_title("Engineer")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("$1000 - $3000")
                .working_hours("9 Hours")
                .location("North")
                .classification("Finance")
                .build();

        //given
        given(jopRepo.save(jobToSave)).willReturn(jobToSave);

        //when
        Job savedJob = jobService.createJob(jobToSave);

        System.out.println(savedJob);
        //then
        assertNotNull(savedJob);

    }

    @Test // Empty Job Title upon creation
    void testCreateJobFailedEmptyJobTitle(){

        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job jobToSave = Job.builder()
                .job_title("")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("$1000 - $3000")
                .working_hours("9 Hours")
                .location("North")
                .classification("Finance")
                .build();

        given(jopRepo.save(any(Job.class))).willReturn(jobToSave);

        //When
        assertThrows(CreateJobException.class,()->{
            jobService.createJob(jobToSave);
        });

        verify(jopRepo,never()).save(any(Job.class ));
    }

    @Test // Empty Job Description upon creation
    void testCreateJobFailedEmptyJobDescription(){

        String[] secondJobDescription = {};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job jobToSave = Job.builder()
                .job_title("Engineer")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("$1000 - $3000")
                .working_hours("9 Hours")
                .location("North")
                .classification("Finance")
                .build();

        given(jopRepo.save(any(Job.class))).willReturn(jobToSave);

        //When
        assertThrows(CreateJobException.class,()->{
            jobService.createJob(jobToSave);
        });

        verify(jopRepo,never()).save(any(Job.class ));

    }

    @Test // Empty Job Description upon creation
    void testCreateJobFailedEmptyJobResponsibilities(){

        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job jobToSave = Job.builder()
                .job_title("Engineer")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("$1000 - $3000")
                .working_hours("9 Hours")
                .location("North")
                .classification("Finance")
                .build();

        given(jopRepo.save(any(Job.class))).willReturn(jobToSave);

        //When
        assertThrows(CreateJobException.class,()->{
            jobService.createJob(jobToSave);
        });

        verify(jopRepo,never()).save(any(Job.class ));


    }

    @Test // Empty Job Requirements upon creation
    void testCreateJobFailedEmptyJobRequirements(){


        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {};

        Job jobToSave = Job.builder()
                .job_title("Engineer")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("$1000 - $3000")
                .working_hours("9 Hours")
                .location("North")
                .classification("Finance")
                .build();

        given(jopRepo.save(any(Job.class))).willReturn(jobToSave);

        //When
        assertThrows(CreateJobException.class,()->{
            jobService.createJob(jobToSave);
        });

        verify(jopRepo,never()).save(any(Job.class ));

    }

    @Test // Empty Job Title upon creation
    void testCreateJobFailedEmptySalary(){


        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job jobToSave = Job.builder()
                .job_title("Engineer")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("")
                .working_hours("9 Hours")
                .location("North")
                .classification("Finance")
                .build();

        given(jopRepo.save(any(Job.class))).willReturn(jobToSave);

        //When
        assertThrows(CreateJobException.class,()->{
            jobService.createJob(jobToSave);
        });

        verify(jopRepo,never()).save(any(Job.class ));

    }

    @Test // Empty Job working hours upon creation
    void testCreateJobFailedEmptyWorkingHours(){


        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job jobToSave = Job.builder()
                .job_title("Engineer")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("$1000 - $3000")
                .working_hours("")
                .location("North")
                .classification("Finance")
                .build();

        given(jopRepo.save(any(Job.class))).willReturn(jobToSave);

        //When
        assertThrows(CreateJobException.class,()->{
            jobService.createJob(jobToSave);
        });

        verify(jopRepo,never()).save(any(Job.class ));

    }

    @Test // Empty Location upon creation
    void testCreateJobFailedEmptyLocation(){


        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job jobToSave = Job.builder()
                .job_title("Engineer")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("$1000 - $3000")
                .working_hours("9 Hours")
                .location("")
                .classification("Finance")
                .build();

        given(jopRepo.save(any(Job.class))).willReturn(jobToSave);

        //When
        assertThrows(CreateJobException.class,()->{
            jobService.createJob(jobToSave);
        });

        verify(jopRepo,never()).save(any(Job.class ));

    }

    @Test // Empty Classification upon creation
    void testCreateJobFailedEmptyClassification(){


        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        Job jobToSave = Job.builder()
                .job_title("")
                .job_type("Product")
                .job_description(secondJobDescription)
                .job_responsibility(secondJobResponsibilities)
                .requirements(secondJobRequirements)
                .salary("$1000 - $3000")
                .working_hours("9 Hours")
                .location("North")
                .classification("")
                .build();

        given(jopRepo.save(any(Job.class))).willReturn(jobToSave);

        //When
        assertThrows(CreateJobException.class,()->{
            jobService.createJob(jobToSave);
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

        assertEquals("Singer/Song Writer",job.getJob_title());
        assertEquals("Music",job.getJob_type());

    }

    @Test
    @Disabled
    void uploadFile() {

    }
}