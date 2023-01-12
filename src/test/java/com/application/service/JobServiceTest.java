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
        job = mock(Job.class);
    }

    @Test
    void NoJobsListed(){
        System.out.println("Running test no job listed");
        //Given setup precondition
        given(jopRepo.findAll()).willReturn(List.of());
        assertThrows(NoJobsListedException.class,()->{
            jobService.getAllJobs();
        });
        System.out.println("Test no job listed success");
    }

    @Test // Get all jobs currently
    void getAllJobs() {
        System.out.println("Running test get all jobs ");

        Job job2 = mock(Job.class);
        //Given setup precondition
        given(jopRepo.findAll()).willReturn(List.of(job,job2));
        //When -- Action being tested
        List<Job> jobList = jobService.getAllJobs();

        assertNotNull(jobList);
        assertEquals(2,jobList.size());

        System.out.println("Test get all jobs success");
    }

    @Test
    void getJobById() {
        System.out.println("Running test get job by ID ");

        //given
        given(jopRepo.findById(1L)).willReturn(Optional.of(job));
        //when
        Job savedJob = jobService.getJobById(1L);
        //then
        System.out.println((savedJob));
        assertNotNull(savedJob);

        System.out.println("Test get job by ID Success");
    }

    @Test
    void NoJobByIdFound(){
        System.out.println("Running test no job id found ");
        given(jopRepo.findById(2L)).willReturn(Optional.empty());
        //When
        assertThrows(NoSuchJobException.class,()->{
            jobService.getJobById(2L);
        });
        System.out.println("Test get no job by ID Success");
    }

    @Test
    void testCreateJob() {
        System.out.println("Running test create job");
        System.out.println("Running create job failed test due to empty job title");

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
        System.out.println("Test Create Job Success");
    }

    @Test // Empty Job Title upon creation
    void testCreateJobFailedEmptyJobTitle(){
        System.out.println("Running create job failed test due to empty job title");

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
        System.out.println("Test create job failed test due to empty job title Success");
    }

    @Test // Empty Job Description upon creation
    void testCreateJobFailedEmptyJobDescription(){
        System.out.println("Running create job failed test due to empty job description");

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

        System.out.println("Test create job failed test due to empty job description success");
    }

    @Test // Empty Job Description upon creation
    void testCreateJobFailedEmptyJobResponsibilities(){
        System.out.println("Running create job failed test due to empty job responsibilities");

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

        System.out.println("Test create job failed test due to empty job responsibilities success");
    }

    @Test // Empty Job Requirements upon creation
    void testCreateJobFailedEmptyJobRequirements(){
        System.out.println("Running create job failed test due to empty requirements");

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
        System.out.println("Test create job failed test due to empty job requirements Success");
    }

    @Test // Empty Job Title upon creation
    void testCreateJobFailedEmptySalary(){
        System.out.println("Running create job failed test due to empty job title");

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
        System.out.println("Test create job failed test due to empty salary Success");
    }

    @Test // Empty Job working hours upon creation
    void testCreateJobFailedEmptyWorkingHours(){
        System.out.println("Running create job failed test due to empty working hours");

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
        System.out.println("Test create job failed test due to empty working hours Success");
    }

    @Test // Empty Location upon creation
    void testCreateJobFailedEmptyLocation(){
        System.out.println("Running create job failed test due to empty location");

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
        System.out.println("Test create job failed test due to empty location");
    }

    @Test // Empty Classification upon creation
    void testCreateJobFailedEmptyClassification(){
        System.out.println("Running create job failed test due to empty classification");

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
        System.out.println("Test create job failed test due to empty classification");
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
    @Disabled
    void uploadFile() {

    }
}