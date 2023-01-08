package com.application.controller;

import com.application.model.Job;
import com.application.repo.JobRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class JobControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JobRepo jobRepo;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JobController jobControllerTest;
    @Test
    void testGetAllJobs() throws Exception {

        String[] firstJobDescription = {"Basic Salary - $2K + Commission", "Replacement leave for work on weekends", "Overseas Allowance", "Flight and Accommodations paid for overseas travels`"};
        String[] firstJobResponsibilities = {"Build value based customer relationships","Develop a strategic networking platform with clients"};
        String[] firstJobRequirements = {"Possess Degree","Excellent Spoken and Written English"};
        String[] secondJobDescription = {"Basic Salary - $1K + Commission", "4 day work week", "Local Allowance"};
        String[] secondJobResponsibilities = {"Build value based products","Develop a relationships"};
        String[] secondJobRequirements = {"Possess Diploma","Excellent Spoken and Written Chinese"};

        List<Job> listJobs = new ArrayList<Job>();

        listJobs.add(new Job( 1L,
                "sales engineer",
                firstJobDescription,
                firstJobResponsibilities,
                firstJobRequirements,
                "Engineer",
                "Central",
                "9 Hours",
                "Finance",
                "$1000 - $20000"));

        listJobs.add(new Job(2L,
                "Product Manager",
                secondJobDescription,
                secondJobResponsibilities,
                secondJobRequirements,
                "Product",
                "North",
                "9 Hours",
                "Finance",
                "$1000 - $3000"));

        ResponseEntity<List<Job>> results =  new ResponseEntity<>(listJobs, HttpStatus.OK);
        Mockito.when(jobControllerTest.getAllJobs()).thenReturn(listJobs);
        String url = "/getAllJobs";
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testGetJobById() throws Exception{
        String[] firstJobDescription = {"Basic Salary - $2K + Commission", "Replacement leave for work on weekends", "Overseas Allowance", "Flight and Accommodations paid for overseas travels`"};
        String[] firstJobResponsibilities = {"Build value based customer relationships","Develop a strategic networking platform with clients"};
        String[] firstJobRequirements = {"Possess Degree","Excellent Spoken and Written English"};

        Optional<Job> job = Optional.of(new Job(
                1L,
                "Product Manager",
                firstJobDescription,
                firstJobResponsibilities,
                firstJobRequirements,
                "Product",
                "North",
                "9 Hours",
                "Finance",
                "$1000 - $3000"));

        ResponseEntity<Optional<Job>> results = new ResponseEntity<>(job,HttpStatus.OK);
//        Mockito.when(jobControllerTest.getJobById(1L)).thenReturn(results);
    }
    @Test
    void testCreateJob() throws Exception{
        String[] firstJobDescription = {"Basic Salary - $2K + Commission", "Replacement leave for work on weekends", "Overseas Allowance", "Flight and Accommodations paid for overseas travels`"};
        String[] firstJobResponsibilities = {"Build value based customer relationships","Develop a strategic networking platform with clients"};
        String[] firstJobRequirements = {"Possess Degree","Excellent Spoken and Written English"};

        mockMvc.perform(MockMvcRequestBuilders
                .post("/createJob")
                .content(asJsonString(new Job(
                        4L,
                        "Product Manager",
                        firstJobDescription,
                        firstJobResponsibilities,
                        firstJobRequirements,
                        "Product",
                        "North",
                        "9 Hours",
                        "Finance",
                        "$1000 - $3000"
                )))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}