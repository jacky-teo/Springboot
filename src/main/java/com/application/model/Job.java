package com.application.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="job_title",nullable = false)
    private String job_title;
    @Column(name="job_description", nullable = false)
    private String[] job_description;
    @Column(name="job_responsibility", nullable = false)
    private String[]  job_responsibility;
    @Column(name="requirements", nullable = false)
    private String[] requirements;
    @Column(name="job_type",nullable = false)
    private String job_type;
    @Column(name="location",nullable = false)
    private String location;
    @Column(name="working_hours",nullable = false)
    private String working_hours;
    @Column(name="classification",nullable = false)
    private String classification;
    @Column(name="salary",nullable = false)
    private String salary;
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
