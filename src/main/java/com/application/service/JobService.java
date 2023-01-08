package com.application.service;

import com.application.exception.CreateJobException;
import com.application.exception.NoJobsListedException;
import com.application.exception.NoSuchJobException;
import com.application.model.Job;
import com.application.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobService{
    @Autowired // Asking Spring boot to give a new instance of job repo and inject into Job service
    private JobRepo jobRepo;

    public List<Job> getAllJobs(){
        List<Job> jobList = jobRepo.findAll();
        if(jobList.isEmpty()){
            throw new NoJobsListedException();
        }
        return jobList;
    }
    public Job getJobById(Long id){
        return jobRepo.findById(id)
                .orElseThrow(()
                -> new NoSuchJobException(id));
    }
    public Job createJob(Job job){
        if(job.getJob_title().isEmpty()){
            throw new CreateJobException("Job title cannot be empty");
        }
        if(job.getJob_description().length == 0){
            throw new CreateJobException("Job description cannot be empty");
        }
        if(job.getJob_responsibility().length == 0){
            throw new CreateJobException("Job responsibility cannot be empty");
        }
        if(job.getJob_type().isEmpty()){
            throw new CreateJobException("Job type cannot be empty");
        }
        if(job.getLocation().isEmpty()){
            throw new CreateJobException("Job Location cannot be empty");
        }

        if(job.getClassification().isEmpty()){
            throw new CreateJobException("Job Classification cannot be empty");
        }

        if(job.getSalary().isEmpty()){
            throw new CreateJobException("Job Salary cannot be empty");
        }

        if(job.getWorking_hours().isEmpty()){
            throw new CreateJobException("Job working hours cannot be empty");
        }

        if(job.getRequirements().length == 0){
            throw new CreateJobException("Job requirements cannot be empty");
        }

        Job jobObj = jobRepo.save(job);
        return jobObj;
    }
    public Job updateJob(Job newJobData,Long id){
        Optional<Job> oldJobData = jobRepo.findById(id);
        if (oldJobData.isPresent()){
            Job updatedJobData =oldJobData.get();
            if(!Objects.equals(newJobData.getJob_title(),updatedJobData.getJob_title()) & !newJobData.getJob_title().isEmpty()){
                updatedJobData.setJob_title(newJobData.getJob_title());
            }

            if(!Objects.equals(newJobData.getJob_description(),updatedJobData.getJob_description()) & ! (newJobData.getJob_description().length == 0)){
                updatedJobData.setJob_description(newJobData.getJob_description());
            }

            if(!Objects.equals(newJobData.getJob_responsibility(),updatedJobData.getJob_responsibility()) & !(newJobData.getJob_responsibility().length ==0)){
                updatedJobData.setJob_responsibility(newJobData.getJob_responsibility());
            }

            if(!Objects.equals(newJobData.getRequirements(),updatedJobData.getRequirements()) & !(newJobData.getRequirements().length == 0)){
                updatedJobData.setRequirements(newJobData.getRequirements());
            }

            if(!Objects.equals(newJobData.getJob_type(),updatedJobData.getJob_type()) & !newJobData.getJob_type().isEmpty()){
                updatedJobData.setJob_type(newJobData.getJob_type());
            }

            if(!Objects.equals(newJobData.getClassification(),updatedJobData.getClassification()) & !newJobData.getClassification().isEmpty()){
                updatedJobData.setClassification(newJobData.getClassification());
            }

            if(!Objects.equals(newJobData.getLocation(),updatedJobData.getLocation()) & !newJobData.getLocation().isEmpty()){
                updatedJobData.setLocation(newJobData.getLocation());
            }

            if(!Objects.equals(newJobData.getSalary(),updatedJobData.getSalary()) & !newJobData.getSalary().isEmpty()){
                updatedJobData.setSalary(newJobData.getSalary());
            }

            if(!Objects.equals(newJobData.getLocation(),updatedJobData.getLocation()) & !newJobData.getLocation().isEmpty()){
                updatedJobData.setLocation(newJobData.getLocation());
            }
        }
        return jobRepo.save(newJobData);
    }
}
