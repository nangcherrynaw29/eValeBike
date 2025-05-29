-- Insert into the app_user table if the id doesn't already exist
IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 1)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('John Doe', 'john.doe@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 2)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Jane Smith', 'jane.smith@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 3)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Alice Johnson', 'alice.johnson@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'ADMIN', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 4)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Bob Brown', 'bob.brown@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'SUPER_ADMIN', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 5)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Michael Green', 'michael.green@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 6)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Sarah White', 'sarah.white@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 7)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Nathan', 'nathan@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 8)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Nathaniel', 'nathaniel@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 9)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Jean', 'jean@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 10)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Jeremy', 'jeremy@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 11)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Kevin', 'kevine@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 12)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Andrew', 'andrew@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 13)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Nicky', 'nicky@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 14)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Aaron', 'aaron@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 15)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Riko', 'riko@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 16)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Nora', 'nora@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'ADMIN', 'APPROVED');
    END

IF NOT EXISTS (SELECT 1 FROM app_user WHERE id = 17)
    BEGIN
        INSERT INTO app_user (name, email, password, role, user_status)
        VALUES ('Waymack', 'waymack@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED');
    END
-- Insert into app_user table with id check
INSERT INTO app_user (name, email, password, role, user_status)
SELECT
    'Sara',
    'sara@example.com',
    '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
    'BIKE_OWNER',
    'APPROVED'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 18);

INSERT INTO app_user (name, email, password, role, user_status)
SELECT
    'Abby',
    'abby@example.com',
    '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
    'TECHNICIAN',
    'APPROVED'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 19);

INSERT INTO app_user (name, email, password, role, user_status)
SELECT
    'Lila',
    'lila@example.com',
    '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
    'BIKE_OWNER',
    'APPROVED'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 20);

-- Insert into bike_owner table with id check
INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT 1, '555-1234', '1990-05-20'
WHERE NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = 1);

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT 6, '555-7890', '1992-07-22'
WHERE NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = 6);

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT 8, '555-1234', '1990-05-20'
WHERE NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = 8);

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT 9, '555-1234', '1990-05-20'
WHERE NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = 9);

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT 10, '555-1234', '1990-05-20'
WHERE NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = 10);

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT 11, '555-1234', '1990-05-20'
WHERE NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = 11);

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT 18, '555-1234', '1990-05-20'
WHERE NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = 18);

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT 20, '555-1234', '1990-05-20'
WHERE NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = 20);

-- Insert bikes with unique QR check
INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT '123e4567-e89b-12d3-a456-426614174001',
       'Giant',
       'Talon',
       2022,
       'M',
       1000,
       'Shimano',
       'Electric',
       'Chain Drive',
       500.0,
       25.0,
       750.0,
       500.0,
       85.0,
       '2024-01-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = '123e4567-e89b-12d3-a456-426614174001');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT '223e4567-e89b-12d3-a456-426614174002',
       'Trek',
       'Marlin',
       2023,
       'L',
       500,
       'SRAM',
       'Electric',
       'Belt Drive',
       600.0,
       28.0,
       800.0,
       600.0,
       90.0,
       '2024-02-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = '223e4567-e89b-12d3-a456-426614174002');

-- Insert into bike_owner_bike table with bike assignment check
INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT 1, '123e4567-e89b-12d3-a456-426614174001'
WHERE NOT EXISTS (SELECT 1
                  FROM bike_owner_bike
                  WHERE bike_owner_id = 1
                    AND bike_qr = '123e4567-e89b-12d3-a456-426614174001');

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT 1, '223e4567-e89b-12d3-a456-426614174002'
WHERE NOT EXISTS (SELECT 1
                  FROM bike_owner_bike
                  WHERE bike_owner_id = 1
                    AND bike_qr = '223e4567-e89b-12d3-a456-426614174002');

-- Insert into technician table with id check
INSERT INTO technician (id)
SELECT 2
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 2);

INSERT INTO technician (id)
SELECT 7
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 7);

INSERT INTO technician (id)
SELECT 12
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 12);

INSERT INTO technician (id)
SELECT 13
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 13);

INSERT INTO technician (id)
SELECT 14
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 14);

-- Insert into administrator table with company name check
INSERT INTO administrator (id, company_name)
SELECT 3, 'E-Bike Corp'
WHERE NOT EXISTS (SELECT 1 FROM administrator WHERE id = 3);

INSERT INTO administrator (id, company_name)
SELECT 16, 'E-Nursing'
WHERE NOT EXISTS (SELECT 1 FROM administrator WHERE id = 16);

-- Insert into super_admin table with id check
INSERT INTO super_admin (id)
SELECT 4
WHERE NOT EXISTS (SELECT 1 FROM super_admin WHERE id = 4);

-- Initialize test benches
INSERT INTO test_bench (last_calibration_date, location, status, test_bench_name, technician_id)
VALUES (CAST(GETDATE() AS DATE), 'Zone A', 'INACTIVE', 'Test Bench 1', NULL),
       (CAST(GETDATE() AS DATE), 'Zone A', 'INACTIVE', 'Test Bench 2', NULL),
       (CAST(GETDATE() AS DATE), 'Zone B', 'INACTIVE', 'Test Bench 3', NULL),
       (CAST(GETDATE() AS DATE), 'Zone B', 'INACTIVE', 'Test Bench 4', NULL),
       (CAST(GETDATE() AS DATE), 'Zone C', 'INACTIVE', 'Test Bench 5', NULL);

-- Insert into recent_activity table with activity check
INSERT INTO recent_activity (activity, description, date, user_id)
VALUES
    ('CREATED_USER', 'Created technician Mike', '2025-04-26', 4),
    ('UPDATED_USER', 'Updated information about Mike', '2025-04-28', 4),
    ('INITIALIZED_TEST', 'All tests started successfully', '2025-04-28 14:45', 4),
    ('INITIALIZED_TEST', 'All tests started successfully', '2025-04-23 15:09', 4);

-- Reset the identity seed for app_user table
DECLARE @maxAppUserId INT;
SELECT @maxAppUserId = ISNULL(MAX(id), 0) FROM app_user;
DBCC CHECKIDENT ('app_user', RESEED, @maxAppUserId);

-- Reset the identity seed for recent_activity table
DECLARE @maxRecentActivityId INT;
SELECT @maxRecentActivityId = ISNULL(MAX(id), 0) FROM recent_activity;
DBCC CHECKIDENT ('recent_activity', RESEED, @maxRecentActivityId);
