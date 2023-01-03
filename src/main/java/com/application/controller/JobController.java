package com.application.controller;

import com.application.model.Job;
import com.application.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController

public class JobController {
    @Autowired
    private JobRepo jobRepo;

    @GetMapping("/getAllJobs")
    public ResponseEntity<List<Job>> getAllJobs(){
        try{
            List<Job> jobList = new ArrayList<>();
            jobRepo.findAll().forEach(jobList::add);

            if(jobList.isEmpty()){
                return new ResponseEntity<>(jobList ,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(jobList ,HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getJobById/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id){
        try{
            Optional<Job> jobData = jobRepo.findById(id);

            if(jobData.isPresent()){
                return new ResponseEntity<>(jobData.get() ,HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PostMapping("/createJob")
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        try{
            Job jobObj = jobRepo.save(job);
            return new ResponseEntity<>(jobObj, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updateJobById/{id}")
    public ResponseEntity<Job> updateJobById(@PathVariable Long id, @RequestBody Job newJobData){
        try{
            Optional<Job> oldJobData = jobRepo.findById(id);
            if(oldJobData.isPresent()){
                Job updatedJobData = oldJobData.get();
                if(!Objects.equals(newJobData.getJob_title(), updatedJobData.getJob_title()) & !newJobData.getJob_title().isEmpty()){
                    updatedJobData.setJob_title(newJobData.getJob_title());
                }
                if(!Objects.equals(newJobData.getJob_description(),updatedJobData.getJob_description()) & !newJobData.getJob_description().isEmpty()){
                    updatedJobData.setJob_description(newJobData.getJob_description());
                }
                Job jobObj = jobRepo.save(updatedJobData);
                return new ResponseEntity<>(jobObj, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/deleteJobById/{id}")
    public ResponseEntity<HttpStatus> deleteJobById(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
