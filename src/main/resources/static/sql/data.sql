--------------------------------------
-- Companies
--------------------------------------
INSERT INTO company (name, address, email, phone)
SELECT 'E-Nursing', 'Lauveut 14 Antwerpen 2000 Belgium', 'e.nursing@example.com', '555-859-7456'
WHERE NOT EXISTS (SELECT 1 FROM company WHERE name = 'E-Nursing');

INSERT INTO company (name, address, email, phone)
SELECT 'Bike-Corp', 'Nationalstraat 19 Antwerpen 2000 Belgium', 'bike.corp@example.com', '420-723-7966'
WHERE NOT EXISTS (SELECT 1 FROM company WHERE name = 'Bike-Corp');

INSERT INTO company (name, address, email, phone)
SELECT 'Pita', 'Hoopland 240 Breda 3650 Netherlands', 'pita@example.com', '520-690-4587'
WHERE NOT EXISTS (SELECT 1 FROM company WHERE name = 'Pita');

--------------------------------------
-- SUPER_ADMIN user
--------------------------------------
INSERT INTO app_user (name, email, password, role, user_status)
SELECT 'Bob Brown', 'bob.brown@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'SUPER_ADMIN', 'APPROVED'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'bob.brown@example.com');

-- Insert into super_admin table only if not already present
INSERT INTO super_admin (id)
SELECT id
FROM app_user
WHERE email = 'bob.brown@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM super_admin WHERE id = (SELECT id FROM app_user WHERE email = 'bob.brown@example.com')
);

--------------------------------------
-- ADMINISTRATOR:
--------------------------------------
INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT
    'Jane Admin',
    'jane.admin@example.com',
    '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
    'ADMIN',
    'APPROVED',
    (SELECT id FROM app_user WHERE email = 'bob.brown@example.com'),
    (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'jane.admin@example.com');

INSERT INTO administrator (id)
SELECT id FROM app_user
WHERE email = 'jane.admin@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM administrator WHERE id = (SELECT id FROM app_user WHERE email = 'jane.admin@example.com')
);

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Wymack', 'wymack@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'ADMIN', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'bob.brown@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'wymack@example.com');

INSERT INTO administrator (id)
SELECT id FROM app_user WHERE email = 'wymack@example.com'
                          AND NOT EXISTS (SELECT 1 FROM administrator WHERE id = (SELECT id FROM app_user WHERE email = 'wymack@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Betsy', 'betsy@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'ADMIN', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'bob.brown@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'betsy@example.com');

INSERT INTO administrator (id)
SELECT id FROM app_user WHERE email = 'betsy@example.com'
                          AND NOT EXISTS (SELECT 1 FROM administrator WHERE id = (SELECT id FROM app_user WHERE email = 'betsy@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Abby', 'abby@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'ADMIN', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'bob.brown@example.com'),
       (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'abby@example.com');

INSERT INTO administrator (id)
SELECT id FROM app_user WHERE email = 'abby@example.com'
                          AND NOT EXISTS (SELECT 1 FROM administrator WHERE id = (SELECT id FROM app_user WHERE email = 'abby@example.com'));

--------------------------------------
-- TECHNICIAN:
--------------------------------------
INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT
    'Mike Tech',
    'mike.tech@example.com',
    '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
    'TECHNICIAN',
    'APPROVED',
    (SELECT id FROM app_user WHERE email = 'bob.brown@example.com'),
    (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'mike.tech@example.com');

INSERT INTO technician (id)
SELECT id FROM app_user
WHERE email = 'mike.tech@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM technician WHERE id = (SELECT id FROM app_user WHERE email = 'mike.tech@example.com')
);

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Riko', 'riko@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'wymack@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'riko@example.com');

INSERT INTO technician (id)
SELECT id FROM app_user WHERE email = 'riko@example.com'
                          AND NOT EXISTS (SELECT 1 FROM technician WHERE id = (SELECT id FROM app_user WHERE email = 'riko@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Jean', 'jean@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'betsy@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'jean@example.com');

INSERT INTO technician (id)
SELECT id FROM app_user WHERE email = 'jean@example.com'
                          AND NOT EXISTS (SELECT 1 FROM technician WHERE id = (SELECT id FROM app_user WHERE email = 'jean@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Jeremy', 'jeremy@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'wymack@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'jeremy@example.com');

INSERT INTO technician (id)
SELECT id FROM app_user WHERE email = 'jeremy@example.com'
                          AND NOT EXISTS (SELECT 1 FROM technician WHERE id = (SELECT id FROM app_user WHERE email = 'jeremy@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Nathaniel', 'nathaniel@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'abby@example.com'),
       (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'nathaniel@example.com');

INSERT INTO technician (id)
SELECT id FROM app_user WHERE email = 'nathaniel@example.com'
                          AND NOT EXISTS (SELECT 1 FROM technician WHERE id = (SELECT id FROM app_user WHERE email = 'nathaniel@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Andrew', 'andrew@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'TECHNICIAN', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'abby@example.com'),
       (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'andrew@example.com');

INSERT INTO technician (id)
SELECT id FROM app_user WHERE email = 'andrew@example.com'
                          AND NOT EXISTS (SELECT 1 FROM technician WHERE id = (SELECT id FROM app_user WHERE email = 'andrew@example.com'));


--------------------------------------
-- BIKE_OWNER:
--------------------------------------
INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT
    'John Doe',
    'john.doe@example.com',
    '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K',
    'BIKE_OWNER',
    'APPROVED',
    (SELECT id FROM app_user WHERE email = 'bob.brown@example.com'),
    (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'john.doe@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-1234', '1990-05-20'
FROM app_user
WHERE email = 'john.doe@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'john.doe@example.com')
);

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Aaron', 'aaron@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'riko@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'aaron@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-0001', '1992-01-01' FROM app_user WHERE email = 'aaron@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'aaron@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Kevin', 'kevin@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'jean@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'kevin@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-0002', '1988-02-02' FROM app_user WHERE email = 'kevin@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'kevin@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Neil', 'neil@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'jeremy@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'neil@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-0003', '1993-03-03' FROM app_user WHERE email = 'neil@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'neil@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Kaytlyn', 'kaytlyn@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'riko@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'kaytlyn@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-0004', '1991-04-04' FROM app_user WHERE email = 'kaytlyn@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'kaytlyn@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Nicky', 'nicky@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'jean@example.com'),
       (SELECT id FROM company WHERE name = 'E-Nursing')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'nicky@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-0005', '1990-05-05' FROM app_user WHERE email = 'nicky@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'nicky@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Renee', 'renee@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'nathaniel@example.com'),
       (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'renee@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-1001', '1989-06-06' FROM app_user WHERE email = 'renee@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'renee@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Allison', 'allison@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'andrew@example.com'),
       (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'allison@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-1002', '1994-07-07' FROM app_user WHERE email = 'allison@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'allison@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Matt', 'matt@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'nathaniel@example.com'),
       (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'matt@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-1003', '1987-08-08' FROM app_user WHERE email = 'matt@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'matt@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Sarah', 'sarah@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'andrew@example.com'),
       (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'sarah@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-1004', '1995-09-09' FROM app_user WHERE email = 'sarah@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'sarah@example.com'));

INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'Seth', 'seth@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED',
       (SELECT id FROM app_user WHERE email = 'nathaniel@example.com'),
       (SELECT id FROM company WHERE name = 'Bike-Corp')
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'seth@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-1005', '1990-10-10' FROM app_user WHERE email = 'seth@example.com'
                                                    AND NOT EXISTS (SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'seth@example.com'));


--------------------------------------
-- Bike (unassigned to user yet)
--------------------------------------
INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT '123e4567-e89b-12d3-a456-426614174001', 'Giant', 'Talon', 2022, 'M', 1000, 'Shimano', 'Electric', 'Chain Drive',
       500.0, 25.0, 750.0, 500.0, 85.0, '2024-01-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = '123e4567-e89b-12d3-a456-426614174001');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT 'e8a1a1d2-7c55-4b34-b8c1-4f5b6e9d9a12', 'Trek', 'Marlin', 2023, 'L', 500, 'Shimano', 'Electric', 'Chain Drive',
       450.0, 20.0, 700.0, 450.0, 80.0, '2024-03-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = 'e8a1a1d2-7c55-4b34-b8c1-4f5b6e9d9a12');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT 'c4f7e22a-16f2-47f6-8ee8-3e922a9a41e8', 'Specialized', 'Rockhopper', 2022, 'M', 300, 'SRAM', 'Electric', 'Belt Drive',
       480.0, 22.0, 720.0, 470.0, 85.0, '2024-05-10'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = 'c4f7e22a-16f2-47f6-8ee8-3e922a9a41e8');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT 'af12b9e4-d354-4eac-88da-efc28e7c3b59', 'Cannondale', 'Trail', 2023, 'S', 150, 'Shimano', 'Electric', 'Chain Drive',
       460.0, 21.0, 710.0, 460.0, 83.0, '2024-02-15'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = 'af12b9e4-d354-4eac-88da-efc28e7c3b59');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT '1e25f6d3-b841-4f76-9c6d-6a7d0b22b2a8', 'Giant', 'Talon', 2022, 'M', 1000, 'Shimano', 'Electric', 'Chain Drive',
       500.0, 25.0, 750.0, 500.0, 85.0, '2024-01-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = '1e25f6d3-b841-4f76-9c6d-6a7d0b22b2a8');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT '09d3b2c7-f72a-4f7f-8b54-e23a1f6c8924', 'Trek', 'X-Caliber', 2021, 'L', 1200, 'SRAM', 'Electric', 'Belt Drive',
       470.0, 23.0, 730.0, 480.0, 82.0, '2024-04-12'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = '09d3b2c7-f72a-4f7f-8b54-e23a1f6c8924');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT 'd2f7f254-4c0d-4a57-9a84-1b7339f98c28', 'Specialized', 'Epic', 2023, 'M', 600, 'Shimano', 'Electric', 'Chain Drive',
       490.0, 24.0, 740.0, 490.0, 84.0, '2024-03-20'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = 'd2f7f254-4c0d-4a57-9a84-1b7339f98c28');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT 'edb67a88-7a4b-4d0e-9232-b2f064f4a1c9', 'Cannondale', 'F-Si', 2022, 'S', 450, 'SRAM', 'Electric', 'Chain Drive',
       460.0, 22.0, 715.0, 460.0, 81.0, '2024-02-28'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = 'edb67a88-7a4b-4d0e-9232-b2f064f4a1c9');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT 'bb7c5c0e-35df-4bd4-b18e-6b1b4c83a1e2', 'Giant', 'Trance', 2023, 'M', 700, 'Shimano', 'Electric', 'Chain Drive',
       480.0, 23.0, 735.0, 470.0, 83.0, '2024-04-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = 'bb7c5c0e-35df-4bd4-b18e-6b1b4c83a1e2');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT 'a75c76d8-3d0f-4f2d-b0a9-40d4f9f6a6d4', 'Specialized', 'Stumpjumper', 2022, 'L', 800, 'SRAM', 'Electric', 'Belt Drive',
       470.0, 22.0, 720.0, 465.0, 82.0, '2024-03-15'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = 'a75c76d8-3d0f-4f2d-b0a9-40d4f9f6a6d4');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT 'fe9e21b0-6f88-4c55-b676-1f8c6e26e8b7', 'Trek', 'Fuel EX', 2023, 'M', 400, 'Shimano', 'Electric', 'Chain Drive',
       460.0, 22.0, 710.0, 460.0, 82.0, '2024-03-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = 'fe9e21b0-6f88-4c55-b676-1f8c6e26e8b7');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT '59e1c2a8-12db-43b6-b0f5-7e2cfa1d3e19', 'Specialized', 'Enduro', 2023, 'L', 350, 'SRAM', 'Electric', 'Belt Drive',
       470.0, 23.0, 720.0, 470.0, 85.0, '2024-04-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = '59e1c2a8-12db-43b6-b0f5-7e2cfa1d3e19');

INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT 'a18f0fbc-96d0-4e88-99e7-dedecf67a2e2', 'Cannondale', 'Habit', 2022, 'S', 500, 'Shimano', 'Electric', 'Chain Drive',
       455.0, 21.0, 705.0, 455.0, 80.0, '2024-05-10'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = 'a18f0fbc-96d0-4e88-99e7-dedecf67a2e2');

--------------------------------------
-- Assign bike
--------------------------------------
INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, '123e4567-e89b-12d3-a456-426614174001'
FROM app_user au
WHERE au.email = 'john.doe@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = '123e4567-e89b-12d3-a456-426614174001'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, 'e8a1a1d2-7c55-4b34-b8c1-4f5b6e9d9a12'
FROM app_user au
WHERE au.email = 'aaron@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = 'e8a1a1d2-7c55-4b34-b8c1-4f5b6e9d9a12'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, 'c4f7e22a-16f2-47f6-8ee8-3e922a9a41e8'
FROM app_user au
WHERE au.email = 'aaron@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = 'c4f7e22a-16f2-47f6-8ee8-3e922a9a41e8'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, 'af12b9e4-d354-4eac-88da-efc28e7c3b59'
FROM app_user au
WHERE au.email = 'kevin@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = 'af12b9e4-d354-4eac-88da-efc28e7c3b59'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, '1e25f6d3-b841-4f76-9c6d-6a7d0b22b2a8'
FROM app_user au
WHERE au.email = 'neil@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = '1e25f6d3-b841-4f76-9c6d-6a7d0b22b2a8'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, '09d3b2c7-f72a-4f7f-8b54-e23a1f6c8924'
FROM app_user au
WHERE au.email = 'neil@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = '09d3b2c7-f72a-4f7f-8b54-e23a1f6c8924'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, 'd2f7f254-4c0d-4a57-9a84-1b7339f98c28'
FROM app_user au
WHERE au.email = 'kaytlyn@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = 'd2f7f254-4c0d-4a57-9a84-1b7339f98c28'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, 'edb67a88-7a4b-4d0e-9232-b2f064f4a1c9'
FROM app_user au
WHERE au.email = 'nicky@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = 'edb67a88-7a4b-4d0e-9232-b2f064f4a1c9'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, 'bb7c5c0e-35df-4bd4-b18e-6b1b4c83a1e2'
FROM app_user au
WHERE au.email = 'renee@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = 'bb7c5c0e-35df-4bd4-b18e-6b1b4c83a1e2'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, 'a75c76d8-3d0f-4f2d-b0a9-40d4f9f6a6d4'
FROM app_user au
WHERE au.email = 'allison@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = 'a75c76d8-3d0f-4f2d-b0a9-40d4f9f6a6d4'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, 'fe9e21b0-6f88-4c55-b676-1f8c6e26e8b7'
FROM app_user au
WHERE au.email = 'matt@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = 'fe9e21b0-6f88-4c55-b676-1f8c6e26e8b7'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, '59e1c2a8-12db-43b6-b0f5-7e2cfa1d3e19'
FROM app_user au
WHERE au.email = 'sarah@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = '59e1c2a8-12db-43b6-b0f5-7e2cfa1d3e19'
);

INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT au.id, 'a18f0fbc-96d0-4e88-99e7-dedecf67a2e2'
FROM app_user au
WHERE au.email = 'seth@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner_bike
    WHERE bike_owner_id = au.id AND bike_qr = 'a18f0fbc-96d0-4e88-99e7-dedecf67a2e2'
);

--------------------------------------
-- TEST BENCHES
--------------------------------------
INSERT INTO test_bench (test_bench_name, last_calibration_date, location, status, technician_id)
SELECT 'Test Bench 1', CAST(GETDATE() AS DATE), 'Zone A', 'INACTIVE', NULL
WHERE NOT EXISTS (SELECT 1 FROM test_bench WHERE test_bench_name = 'Test Bench 1');

INSERT INTO test_bench (test_bench_name, last_calibration_date, location, status, technician_id)
SELECT 'Test Bench 2', CAST(GETDATE() AS DATE), 'Zone B', 'INACTIVE', NULL
WHERE NOT EXISTS (SELECT 1 FROM test_bench WHERE test_bench_name = 'Test Bench 2');

INSERT INTO test_bench (test_bench_name, last_calibration_date, location, status, technician_id)
SELECT 'Test Bench 3', CAST(GETDATE() AS DATE), 'Zone C', 'INACTIVE', NULL
WHERE NOT EXISTS (SELECT 1 FROM test_bench WHERE test_bench_name = 'Test Bench 3');

INSERT INTO test_bench (test_bench_name, last_calibration_date, location, status, technician_id)
SELECT 'Test Bench 4', CAST(GETDATE() AS DATE), 'Zone D', 'INACTIVE', NULL
WHERE NOT EXISTS (SELECT 1 FROM test_bench WHERE test_bench_name = 'Test Bench 4');

INSERT INTO test_bench (test_bench_name, last_calibration_date, location, status, technician_id)
SELECT 'Test Bench 5', CAST(GETDATE() AS DATE), 'Zone E', 'INACTIVE', NULL
WHERE NOT EXISTS (SELECT 1 FROM test_bench WHERE test_bench_name = 'Test Bench 5');

INSERT INTO test_bench (test_bench_name, last_calibration_date, location, status, technician_id)
SELECT 'Test Bench 6', CAST(GETDATE() AS DATE), 'Zone F', 'INACTIVE', NULL
WHERE NOT EXISTS (SELECT 1 FROM test_bench WHERE test_bench_name = 'Test Bench 6');

INSERT INTO test_bench (test_bench_name, last_calibration_date, location, status, technician_id)
SELECT 'Test Bench 7', CAST(GETDATE() AS DATE), 'Zone G', 'INACTIVE', NULL
WHERE NOT EXISTS (SELECT 1 FROM test_bench WHERE test_bench_name = 'Test Bench 7');

--------------------------------------
-- RECENT ACTIVITY: Technician creation
--------------------------------------
INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created technician Mike', '2025-04-26', id
FROM app_user
WHERE email = 'mike.tech@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'mike.tech@example.com'
    )
);

