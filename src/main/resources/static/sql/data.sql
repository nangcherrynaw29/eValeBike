-- Insert into the app_user table if the id doesn't already exist
INSERT INTO app_user (id, name, email, password, role, user_status)
SELECT 1,
       'John Doe',
       'john.doe@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'BIKE_OWNER',
       'APPROVED'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 1);

INSERT INTO app_user (id, name, email, password, role, user_status)
SELECT 2,
       'Jane Smith',
       'jane.smith@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'TECHNICIAN',
       'APPROVED'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 2);

INSERT INTO technician (id)
SELECT 2
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 2);

INSERT INTO app_user (id, name, email, password, role, user_status)
SELECT 3,
       'Alice Johnson',
       'alice.johnson@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'ADMIN',
       'APPROVED'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 3);

INSERT INTO administrator (id, company_name)
SELECT 3, 'E-Bike Corp'
WHERE NOT EXISTS (SELECT 1 FROM administrator WHERE id = 3);

INSERT INTO app_user (id, name, email, password, role, user_status)
SELECT 4,
       'Bob Brown',
       'bob.brown@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'SUPER_ADMIN',
       'APPROVED'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 4);

-- Insert into the super_admin table if the id doesn't already exist
INSERT INTO super_admin (id)
SELECT 4
WHERE NOT EXISTS (SELECT 1 FROM super_admin WHERE id = 4);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 5,
       'Michael Green',
       'michael.green@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'BIKE_OWNER',
       'APPROVED',
       2
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 5);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 6,
       'Sarah White',
       'sarah.white@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'BIKE_OWNER',
       'APPROVED',
       2
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 6);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 7,
       'Nathan',
       'nathan@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'TECHNICIAN',
       'APPROVED',
       3
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 7);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 8,
       'Nathaniel',
       'nathaniel@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'BIKE_OWNER',
       'APPROVED',
       2
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 8);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 9,
       'Jean',
       'jean@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'BIKE_OWNER',
       'APPROVED',
       2
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 9);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 10,
       'Jeremy',
       'jeremy@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'BIKE_OWNER',
       'APPROVED',
       2
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 10);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 11,
       'Kevin',
       'kevine@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'BIKE_OWNER',
       'APPROVED',
       2
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 11);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 12,
       'Andrew',
       'andrew@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'TECHNICIAN',
       'APPROVED',
       3
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 12);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 13,
       'Nicky',
       'nicky@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'TECHNICIAN',
       'APPROVED',
       3
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 13);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 14,
       'Aaron',
       'aaron@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'TECHNICIAN',
       'APPROVED',
       3
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 14);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 15,
       'Riko',
       'riko@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'TECHNICIAN',
       'APPROVED',
       3
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 15);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 16,
       'Nora',
       'nora@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'ADMIN',
       'APPROVED',
       4
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 16);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 17,
       'Wymack',
       'waymack@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'TECHNICIAN',
       'APPROVED',
       3
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 17);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 18,
       'Sara',
       'sara@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'BIKE_OWNER',
       'APPROVED',
       2
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 18);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 19,
       'Abby',
       'abby@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'TECHNICIAN',
       'APPROVED',
       3
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 19);

INSERT INTO app_user (id, name, email, password, role, user_status, created_by_id)
SELECT 20,
       'Lila',
       'lila@example.com',
       '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
       'BIKE_OWNER',
       'APPROVED',
       2
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE id = 20);

-- Insert into the bike_owner table
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

-- Insert bikes
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

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT '323e4567-e89b-12d3-a456-426614174003',
       'Specialized',
       'Rockhopper',
       2021,
       'S',
       1200,
       'Shimano',
       'Electric',
       'Chain Drive',
       550.0,
       26.0,
       770.0,
       520.0,
       88.0,
       '2024-03-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = '323e4567-e89b-12d3-a456-426614174003');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT '423e4567-e89b-12d3-a456-426614174004',
       'Cannondale',
       'Trail 7',
       2020,
       'M',
       800,
       'SRAM',
       'Electric',
       'Belt Drive',
       580.0,
       27.0,
       820.0,
       580.0,
       92.0,
       '2024-04-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = '423e4567-e89b-12d3-a456-426614174004');

-- Assign multiple bikes to owners
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

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT 6, '423e4567-e89b-12d3-a456-426614174004'
WHERE NOT EXISTS (SELECT 1
                  FROM bike_owner_bike
                  WHERE bike_owner_id = 6
                    AND bike_qr = '423e4567-e89b-12d3-a456-426614174004');

-- Insert into the technician table if the id doesn't already exist
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

INSERT INTO technician (id)
SELECT 15
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 15);

INSERT INTO technician (id)
SELECT 17
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 17);

INSERT INTO technician (id)
SELECT 19
WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 19);

-- Insert into the administrator table if the id doesn't already exist
INSERT INTO administrator (id, company_name)
SELECT 16, 'E-Nursing'
WHERE NOT EXISTS (SELECT 1 FROM administrator WHERE id = 16);

-- Initialize test benches
INSERT INTO test_bench (last_calibration_date, location, status, test_bench_name, technician_id)
VALUES (CURRENT_DATE, 'Zone A', 'INACTIVE', 'Test Bench 1', null),
       (CURRENT_DATE, 'Zone A', 'INACTIVE', 'Test Bench 2', null),
       (CURRENT_DATE, 'Zone B', 'INACTIVE', 'Test Bench 3', null),
       (CURRENT_DATE, 'Zone B', 'INACTIVE', 'Test Bench 4', null),
       (CURRENT_DATE, 'Zone C', 'INACTIVE', 'Test Bench 5', null)
ON CONFLICT (test_bench_name) DO NOTHING;

INSERT INTO recent_activity (id, activity, description, date, user_id)
VALUES (1, 'CREATED_USER', 'Created technician Mike', '2025-04-26', 4),
       (2, 'UPDATED_USER', 'Updated information about Mike', '2025-04-28', 4),
       (3, 'INITIALIZED_TEST', 'All tests started successfully', '2025-04-28 14:45', 4),
       (4, 'INITIALIZED_TEST', 'All tests started successfully', '2025-04-23 15:09', 4)
ON CONFLICT (id) DO NOTHING;

-- Reset app_user sequence
SELECT setval('app_user_id_seq', COALESCE((SELECT MAX(id) FROM app_user), 0) + 1, false);

-- Reset recent_activity sequence
SELECT setval('recent_activity_id_seq', COALESCE((SELECT MAX(id) FROM recent_activity), 0) + 1, false);