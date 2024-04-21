# tf-http-fn-cicd
http cloud function CICD

--role to enable services 
--permission: serviceusage.services.enable
roles/serviceusage.serviceUsageAdmin

--IAM permission "cloudscheduler.jobs.create"
roles/cloudscheduler.admin

gcloud sql connect my-instance --database bird_encyclopedia

CREATE TABLE birds (
  id SERIAL PRIMARY KEY,
  bird VARCHAR(256),
  description VARCHAR(1024)
);


INSERT INTO birds (bird , description) VALUES 
('pigeon', 'common in cities'),
('eagle', 'bird of prey');

Role: Cloud SQL Client 
Execute Cloud SQL
