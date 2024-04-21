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

https://cloud.google.com/sql/docs/postgres/connect-overview#java
https://cloud.google.com/sql/docs/postgres/connect-connectors
https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory/blob/main/docs/jdbc.md
https://www.sohamkamani.com/java/cloudsql/
https://cloud.google.com/scheduler/docs/configuring/cron-job-schedules
https://cloud.google.com/scheduler/docs/creating#gcloud
https://cloud.google.com/looker/docs/reference/param-view-timezone-values
https://cloud.google.com/about/locations#lightbox-regions-map
https://cloud.google.com/iam/docs/understanding-roles
https://cloud.google.com/scheduler/docs/tut-gcf-pub-sub
https://www.googlecloudcommunity.com/gc/Serverless/Cloud-Run-to-Cloud-SQL-Connection-stops-working-after-1-hour/td-p/161579
https://stackoverflow.com/questions/58047596/gcp-failed-to-update-metadata-for-cloud-sql-instance
https://docs.github.com/en/actions/security-guides/using-secrets-in-github-actions
https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory/issues/267
