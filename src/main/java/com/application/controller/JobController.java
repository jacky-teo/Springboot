package com.application.controller;

import com.application.model.Job;
import com.application.repo.JobRepo;
import com.application.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class JobController {
    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private JobService jobService;

    @GetMapping(path="/getAllJobs", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Job> getAllJobs() throws Exception{
            return jobService.getAllJobs();
    }
    @GetMapping(path="/getJobById/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Job getJobById(@PathVariable Long id) throws Exception{
        return jobService.getJobById(id);
    }
    @PostMapping(path="/uploadFile")
    public String uploadResume(@RequestParam("file") MultipartFile file) throws IllegalStateException,IOException {
        return jobService.uploadFile(file);
    }
    @PostMapping(path="/createJob")
    public Job createJob(@RequestBody @Validated Job job) throws Exception{
        return jobService.createJob(job);
    }

    @PutMapping("/updateJobById/{id}")
    public ResponseEntity<Job> updateJobById(@PathVariable Long id, @RequestBody Job newJobData) {
        try{
            Job jobObj = jobService.updateJob(newJobData,id);
            return new ResponseEntity<>(jobObj,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteJobById/{id}")
    public ResponseEntity<HttpStatus> deleteJobById(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
