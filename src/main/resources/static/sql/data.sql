-- Insert into app_user table if the id doesn't already exist
INSERT INTO app_user (id, name, email, password, role)
SELECT 1, 'John Doe', 'john.doe@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 1);

INSERT INTO app_user (id, name, email, password, role)
SELECT 2, 'Jane Smith', 'jane.smith@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 2);

INSERT INTO app_user (id, name, email, password, role)
SELECT 3, 'Alice Johnson', 'alice.johnson@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 3);

INSERT INTO app_user (id, name, email, password, role)
SELECT 4, 'Bob Brown', 'bob.brown@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'SUPER_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 4);

-- Insert into bike_owner table if the id doesn't already exist
INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT 1, '555-1234', '1990-05-20'
WHERE NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = 1);

-- Insert into technician table if the id doesn't already exist
INSERT INTO technician (id)
SELECT 2
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 2);

-- Insert into administrator table if the id doesn't already exist
INSERT INTO administrator (id, company_name)
SELECT 3, 'E-Bike Corp'
WHERE NOT EXISTS (SELECT 1 FROM administrator WHERE id = 3);

-- Insert into super_admin table if the id doesn't already exist
INSERT INTO super_admin (id)
SELECT 4
WHERE NOT EXISTS (SELECT 1 FROM super_admin WHERE id = 4);
