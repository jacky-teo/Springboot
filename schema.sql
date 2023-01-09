create database job; -- Creates the new database
create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
grant all on job.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database

CREATE TABLE Jobs (
    id int,
    job_title varchar(255),
    job_description varchar(255),
    job_responsibility varchar(255),
    requirements varchar(255),
    job_type varchar(255),
    location varchar(255),
    working_hours varchar(255),
    classification varchar(255),
	salary varchar(255)
);