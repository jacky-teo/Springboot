package com.application.model;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name="Jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String job_title;
    private String[] job_description;
    private String[]  job_responsibility;
    private String[] requirements;
    private String job_type;
    private String location;
    private String working_hours;
    private String classification;
    private String salary;

    public Job() {
    }

    public Job(Long id, String job_title, String[] job_description, String[] job_responsibility, String[] requirements, String job_type, String location, String working_hours, String classification, String salary) {
        this.id = id;
        this.job_title = job_title;
        this.job_description = job_description;
        this.job_responsibility = job_responsibility;
        this.requirements = requirements;
        this.job_type = job_type;
        this.location = location;
        this.working_hours = working_hours;
        this.classification = classification;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String[] getJob_description() {
        return job_description;
    }

    public void setJob_description(String[] job_description) {
        this.job_description = job_description;
    }

    public String[] getJob_responsibility() {
        return job_responsibility;
    }

    public void setJob_responsibility(String[] job_responsibility) {
        this.job_responsibility = job_responsibility;
    }

    public String[] getRequirements() {
        return requirements;
    }

    public void setRequirements(String[] requirements) {
        this.requirements = requirements;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", job_title='" + job_title + '\'' +
                ", job_description=" + Arrays.toString(job_description) +
                ", job_responsibility=" + Arrays.toString(job_responsibility) +
                ", requirements=" + Arrays.toString(requirements) +
                ", job_type='" + job_type + '\'' +
                ", location='" + location + '\'' +
                ", working_hours='" + working_hours + '\'' +
                ", classification='" + classification + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
