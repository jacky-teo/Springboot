package com.application.controller;

import com.application.model.Job;;
import com.application.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class JobControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

//    @Mock
//    private JobRepo jobRepo;


    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    private Job job;

    @InjectMocks
    private JobController jobController;



    @BeforeEach
    void setUp(){
        String[] firstJobDescription = {"Basic Salary - $2K + Commission", "Replacement leave for work on weekends", "Overseas Allowance", "Flight and Accommodations paid for overseas travels`"};
        String[] firstJobResponsibilities = {"Build value based customer relationships","Develop a strategic networking platform with clients"};
        String[] firstJobRequirements = {"Possess Degree","Excellent Spoken and Written English"};
        job = Job.builder()
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
    void testGetAllJobs_success() throws Exception{
        List<Job> jobList = new ArrayList<>(Arrays.asList(job));
        when(jobService.getAllJobs()).thenReturn(jobList);
        //Emulation of a get request
        mockMvc.perform(MockMvcRequestBuilders
                .get("/getAllJobs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].job_title",is("sales engineer"))
        );
    }

    @Test // Raise Questions on this
    void testGetAllJobs_fails() throws Exception{
        List<Job> jobList = new ArrayList<>(Arrays.asList());
        when(jobService.getAllJobs()).thenReturn(jobList);
        //Emulation of a get request
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getAllJobs")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$",hasSize(0))
                );
    }

    @Test
    void testGetJobById() throws Exception{
        when(jobService.getJobById(1L)).thenReturn(job);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/getJobById/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.job_title",is("sales engineer")));
    }

    @Test
    void testCreateJob() throws Exception{
        when(jobService.createJob(job)).thenReturn(job);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/createJob")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(job)))
                        .andExpect(status().isOk());
    }
}