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

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created technician Malcolm', '2025-04-25', id
FROM app_user
WHERE email = 'bob.brown@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'bob.brown@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'UPDATED_USER', 'Updated technician Mike', '2025-04-27', id
FROM app_user
WHERE email = 'bob.brown@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'UPDATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'bob.brown@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'FAILED_TEST', 'Failed manual test', '2025-05-03', id
FROM app_user
WHERE email = 'mike.tech@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'FAILED_TEST' AND user_id = (
        SELECT id FROM app_user WHERE email = 'mike.tech@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'DELETED_USER', 'Deleted technician Malcolm', '2025-04-26', id
FROM app_user
WHERE email = 'bob.brown@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'DELETED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'bob.brown@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created bike owner Tony', '2025-03-26', id
FROM app_user
WHERE email = 'andrew@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'andrew@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'DELETED_USER', 'Deleted bike owner Tony', '2025-04-01', id
FROM app_user
WHERE email = 'andrew@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'DELETED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'andrew@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created bike owner Ollie', '2025-03-26', id
FROM app_user
WHERE email = 'mike.tech@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'mike.tech@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'INITIALIZED_TEST', 'Fast test initiated', '2024-12-26', id
FROM app_user
WHERE email = 'nathaniel@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'INITIALIZED_TEST' AND user_id = (
        SELECT id FROM app_user WHERE email = 'nathaniel@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'BIKE_ADDED', 'Added bike bq-1234', '2025-03-20', id
FROM app_user
WHERE email = 'nathaniel@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'BIKE_ADDED' AND user_id = (
        SELECT id FROM app_user WHERE email = 'nathaniel@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created bike owner Piet', '2025-04-21', id
FROM app_user
WHERE email = 'jeremy@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'jeremy@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'FAILED_TEST', 'Failed fast test', '2025-05-15', id
FROM app_user
WHERE email = 'jeremy@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'FAILED_TEST' AND user_id = (
        SELECT id FROM app_user WHERE email = 'jeremy@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'INITIALIZED_TEST', 'Fast test run', '2025-04-20', id
FROM app_user
WHERE email = 'jean@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'INITIALIZED_TEST' AND user_id = (
        SELECT id FROM app_user WHERE email = 'jean@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created bike owner Suzane', '2025-01-26', id
FROM app_user
WHERE email = 'riko@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'riko@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'APPROVED_USER', 'Approved technician Lori', '2024-11-13', id
FROM app_user
WHERE email = 'abby@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'APPROVED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'abby@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'APPROVED_USER', 'Approved technician Horace', '2025-02-11', id
FROM app_user
WHERE email = 'abby@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'APPROVED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'abby@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'APPROVED_USER', 'Approved technician Holly', '2025-02-27', id
FROM app_user
WHERE email = 'betsy@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'APPROVED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'betsy@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'REJECTED_APPROVAL', 'Rejected technician Liam', '2025-04-22', id
FROM app_user
WHERE email = 'betsy@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'REJECTED_APPROVAL' AND user_id = (
        SELECT id FROM app_user WHERE email = 'betsy@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created technician Miller', '2025-04-19', id
FROM app_user
WHERE email = 'betsy@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'betsy@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'UPDATED_USER', 'Updated technician Miller', '2025-04-26', id
FROM app_user
WHERE email = 'betsy@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'UPDATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'betsy@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created technician Holmes', '2025-04-20', id
FROM app_user
WHERE email = 'wymack@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'wymack@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'BIKE_ADDED', 'Added a new bike', '2025-04-21', id
FROM app_user
WHERE email = 'wymack@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'BIKE_ADDED' AND user_id = (
        SELECT id FROM app_user WHERE email = 'wymack@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'BIKE_REMOVED', 'Removed a bike', '2025-02-26', id
FROM app_user
WHERE email = 'wymack@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'BIKE_REMOVED' AND user_id = (
        SELECT id FROM app_user WHERE email = 'wymack@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'BIKE_REMOVED', 'Removed a bike', '2025-04-05', id
FROM app_user
WHERE email = 'wymack@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'BIKE_REMOVED' AND user_id = (
        SELECT id FROM app_user WHERE email = 'wymack@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'BIKE_ADDED', 'Added a new bike', '2025-05-26', id
FROM app_user
WHERE email = 'wymack@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'BIKE_ADDED' AND user_id = (
        SELECT id FROM app_user WHERE email = 'wymack@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created technician Laila', '2025-04-17', id
FROM app_user
WHERE email = 'jane.admin@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'jane.admin@example.com'
    )
);

INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created technician Gigi', '2025-04-01', id
FROM app_user
WHERE email = 'jane.admin@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM recent_activity
    WHERE activity = 'CREATED_USER' AND user_id = (
        SELECT id FROM app_user WHERE email = 'jane.admin@example.com'
    )
);
-- Test Reports for mike.tech@example.com
INSERT INTO test_reports (id, expiry_date, state, type, battery_capacity, max_support, engine_power_max, engine_power_nominal, engine_torque, bike_QR, technician_username)
VALUES
    ('rpt-mike-001', '2025-12-31', 'COMPLETED', 'Fast', 420, 25, 250, 200, 70, '123e4567-e89b-12d3-a456-426614174001', 'mike.tech@example.com'),
    ('rpt-mike-002', '2025-12-31', 'COMPLETED', 'Fast', 430, 25, 250, 200, 70, '123e4567-e89b-12d3-a456-426614174001', 'mike.tech@example.com'),
    ('rpt-mike-003', '2025-12-31', 'COMPLETED', 'Manual', 440, 25, 250, 200, 70, '123e4567-e89b-12d3-a456-426614174001', 'mike.tech@example.com');

-- Visual Inspections for Mike
INSERT INTO visual_inspection (
    tires, bell, cranks, electric_wiring, front_fork, handles, chain_belt, pedals, reflectors,
    brake_pads, brake_handles, brake_cables, brake_discs, mudguards, handle_bar, rear_sprocket,
    front_sprocket, rims_spokes, rear_suspension, suspension_front, saddle, test_report_id
) VALUES (
            NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, 'rpt-mike-001'
         );

INSERT INTO visual_inspection (
    tires, bell, cranks, electric_wiring, front_fork, handles, chain_belt, pedals, reflectors,
    brake_pads, brake_handles, brake_cables, brake_discs, mudguards, handle_bar, rear_sprocket,
    front_sprocket, rims_spokes, rear_suspension, suspension_front, saddle, test_report_id
) VALUES (
            NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, 'rpt-mike-002'
         );

INSERT INTO visual_inspection (
    tires, bell, cranks, electric_wiring, front_fork, handles, chain_belt, pedals, reflectors,
    brake_pads, brake_handles, brake_cables, brake_discs, mudguards, handle_bar, rear_sprocket,
    front_sprocket, rims_spokes, rear_suspension, suspension_front, saddle, test_report_id
) VALUES (
            NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, 'rpt-mike-003'
         );

-- Test Entries for Mike
INSERT INTO test_report_entries (test_report_id, timestamp, battery_voltage, battery_current, battery_capacity, battery_temperature_celsius, charge_status, assistance_level, torque_crank_nm, bike_wheel_speed_kmh, cadance_rpm, engine_rpm, engine_power_watt, wheel_power_watt, roll_torque, loadcell_n, rol_hz, horizontal_inclination, vertical_inclination, load_power, status_plug)
VALUES
-- rpt-mike-001
('rpt-mike-001', '2025-04-30T10:00:00', 36.5, 1.5, 420, 25.0, 1, 3, 30.0, 22.5, 80, 90, 200.0, 180.0, 2.0, 15.0, 1.2, 0.5, 0.3, 250.0, true),
('rpt-mike-001', '2025-04-30T10:00:00', 36.8, 1.8, 420, 26.0, 1, 3, 31.0, 22.8, 82, 91, 205.0, 185.0, 2.1, 15.5, 1.25, 0.5, 0.3, 255.0, true),
('rpt-mike-001', '2025-04-30T10:00:00', 36.8, 1.7, 420, 26.0, 1, 3, 30.1, 22.9, 81, 89, 204.0, 186.0, 2.0, 15.8, 1.35, 0.5, 0.3, 255.0, true),
('rpt-mike-001', '2025-04-30T10:00:00', 36.8, 1.6, 420, 26.0, 1, 3, 31.2, 22.7, 80, 87, 202.0, 182.0, 2.2, 15.9, 1.45, 0.5, 0.3, 255.0, true),
('rpt-mike-001', '2025-04-30T10:00:00', 36.8, 1.5, 420, 26.0, 1, 3, 31.3, 22.6, 83, 88, 201.0, 180.0, 2.3, 16.1, 1.39, 0.5, 0.3, 255.0, true),
('rpt-mike-001', '2025-04-30T10:00:00', 36.8, 1.7, 420, 26.0, 1, 3, 30.0, 22.4, 82, 91, 203.0, 180.0, 2.7, 15.9, 1.51, 0.5, 0.3, 255.0, true),
('rpt-mike-001', '2025-04-30T10:00:00', 36.8, 1.8, 420, 26.0, 1, 3, 30.5, 22.6, 80, 93, 208.0, 186.0, 2.5, 15.8, 1.50, 0.5, 0.3, 255.0, true),
('rpt-mike-001', '2025-04-30T10:00:00', 36.8, 1.8, 420, 26.0, 1, 3, 30.9, 22.5, 79, 94, 205.0, 189.0, 2.7, 15.9, 1.47, 0.5, 0.3, 255.0, true),
('rpt-mike-001', '2025-04-30T10:00:00', 36.8, 1.7, 420, 26.0, 1, 3, 31.0, 22.9, 82, 90, 202.0, 187.0, 2.5, 15.6, 1.44, 0.5, 0.3, 255.0, true),
-- rpt-mike-002
('rpt-mike-002', '2025-05-15T10:00:00', 36.5, 1.5, 420, 25.0, 1, 3, 30.0, 22.5, 80, 90, 200.0, 180.0, 2.0, 15.0, 1.2, 0.5, 0.3, 250.0, true),
('rpt-mike-002', '2025-05-15T10:00:00', 36.8, 1.8, 420, 26.0, 1, 3, 30.0, 21.8, 82, 91, 205.0, 185.0, 2.1, 15.5, 1.25, 0.5, 0.3, 255.0, true),
('rpt-mike-002', '2025-05-15T10:00:00', 36.8, 1.8, 420, 26.0, 1, 3, 30.1, 21.9, 81, 89, 204.0, 186.0, 2.0, 15.8, 1.30, 0.5, 0.3, 255.0, true),
('rpt-mike-002', '2025-05-15T10:00:00', 36.8, 1.6, 420, 26.0, 1, 3, 30.2, 22.3, 80, 88, 201.0, 180.0, 2.1, 15.9, 1.45, 0.5, 0.3, 255.0, true),
('rpt-mike-002', '2025-05-15T10:00:00', 36.8, 1.6, 420, 26.0, 1, 3, 30.3, 22.6, 85, 86, 203.0, 180.0, 2.3, 16.2, 1.39, 0.5, 0.3, 255.0, true),
('rpt-mike-002', '2025-05-15T10:00:00', 36.8, 1.7, 420, 26.0, 1, 3, 30.0, 22.4, 83, 91, 204.0, 180.0, 2.5, 15.9, 1.40, 0.5, 0.3, 255.0, true),
('rpt-mike-002', '2025-05-15T10:00:00', 36.8, 1.7, 420, 26.0, 1, 3, 30.5, 22.6, 82, 92, 205.0, 186.0, 2.5, 15.7, 1.40, 0.5, 0.3, 255.0, true),
('rpt-mike-002', '2025-05-15T10:00:00', 36.8, 1.9, 420, 26.0, 1, 3, 30.9, 22.5, 79, 91, 205.0, 189.0, 2.7, 15.9, 1.41, 0.5, 0.3, 255.0, true),
('rpt-mike-002', '2025-05-15T10:00:00', 36.8, 1.7, 420, 26.0, 1, 3, 31.0, 22.9, 80, 90, 202.0, 186.0, 2.5, 15.6, 1.44, 0.5, 0.3, 255.0, true),

-- rpt-mike-003
('rpt-mike-003', '2025-05-30T10:00:00', 36.5, 1.5, 420, 25.0, 1, 3, 30.0, 23.5, 80, 90, 200.0, 180.0, 3.0, 16.0, 2.2, 0.6, 0.2, 240.0, true),
('rpt-mike-003', '2025-05-30T10:05:00', 36.6, 1.2, 420, 26.0, 1, 3, 32.0, 23.8, 82, 92, 205.0, 180.0, 3.1, 16.5, 2.25, 0.6, 0.2, 245.0, true),
('rpt-mike-003', '2025-05-30T10:05:00', 36.7, 1.5, 420, 26.0, 1, 3, 32.1, 23.9, 79, 88, 204.0, 182.0, 3.0, 16.8, 2.35, 0.6, 0.2, 255.0, true),
('rpt-mike-003', '2025-05-30T10:05:00', 36.2, 1.6, 420, 26.0, 1, 3, 32.2, 23.7, 78, 87, 200.0, 182.0, 3.2, 16.9, 2.45, 0.6, 0.2, 245.0, true),
('rpt-mike-003', '2025-05-30T10:05:00', 36.1, 1.4, 420, 26.0, 1, 3, 32.3, 23.6, 83, 88, 200.0, 180.0, 3.3, 16.1, 2.39, 0.6, 0.2, 245.0, true),
('rpt-mike-003', '2025-05-30T10:05:00', 36.3, 1.6, 420, 26.0, 1, 3, 32.0, 23.4, 80, 90, 200.0, 180.0, 3.7, 16.9, 2.51, 0.6, 0.2, 245.0, true),
('rpt-mike-003', '2025-05-30T10:05:00', 36.5, 1.8, 420, 26.0, 1, 3, 32.5, 23.6, 80, 92, 204.0, 181.0, 3.5, 16.8, 2.50, 0.6, 0.2, 245.0, true),
('rpt-mike-003', '2025-05-30T10:05:00', 36.8, 1.8, 420, 26.0, 1, 3, 32.9, 23.5, 79, 94, 202.0, 182.0, 3.7, 16.9, 2.47, 0.6, 0.2, 245.0, true),
('rpt-mike-003', '2025-05-30T10:05:00', 36.9, 1.7, 420, 26.0, 1, 3, 32.0, 23.9, 82, 90, 202.0, 182.0, 3.5, 16.6, 2.44, 0.6, 0.2, 245.0, true);

-- Test Reports for riko@example.com
INSERT INTO test_reports (id, expiry_date, state, type, battery_capacity, max_support, engine_power_max, engine_power_nominal, engine_torque, bike_QR, technician_username)
VALUES
    ('rpt-riko-001', '2026-06-01', 'COMPLETED', 'Manual', 395, 23, 230, 190, 60, 'e8a1a1d2-7c55-4b34-b8c1-4f5b6e9d9a12', 'riko@example.com'),
    ('rpt-riko-002', '2026-06-01', 'COMPLETED', 'Fast', 400, 23, 230, 190, 60, 'e8a1a1d2-7c55-4b34-b8c1-4f5b6e9d9a12', 'riko@example.com');

-- Visual Inspections for Riko
INSERT INTO visual_inspection (
    tires, bell, cranks, electric_wiring, front_fork, handles, chain_belt, pedals, reflectors,
    brake_pads, brake_handles, brake_cables, brake_discs, mudguards, handle_bar, rear_sprocket,
    front_sprocket, rims_spokes, rear_suspension, suspension_front, saddle, test_report_id
) VALUES (
            NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, 'rpt-riko-001'
         );

INSERT INTO visual_inspection (
    tires, bell, cranks, electric_wiring, front_fork, handles, chain_belt, pedals, reflectors,
    brake_pads, brake_handles, brake_cables, brake_discs, mudguards, handle_bar, rear_sprocket,
    front_sprocket, rims_spokes, rear_suspension, suspension_front, saddle, test_report_id
) VALUES (
            NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, NULL, NULL,
             NULL, NULL, NULL, NULL, NULL, 'rpt-riko-002'
         );

-- Test Entries for Riko
INSERT INTO test_report_entries (test_report_id, timestamp, battery_voltage, battery_current, battery_capacity, battery_temperature_celsius, charge_status, assistance_level, torque_crank_nm, bike_wheel_speed_kmh, cadance_rpm, engine_rpm, engine_power_watt, wheel_power_watt, roll_torque, loadcell_n, rol_hz, horizontal_inclination, vertical_inclination, load_power, status_plug)
VALUES
-- rpt-riko-001
('rpt-riko-001', '2025-05-30T13:00:00', 35.8, 1.3, 395, 28.0, 0, 2, 28.0, 20.0, 75, 88, 180.0, 160.0, 1.8, 14.0, 1.1, 0.4, 0.1, 230.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.9, 1.4, 395, 28.5, 0, 2, 29.0, 20.5, 76, 89, 182.0, 162.0, 1.7, 14.2, 1.10, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 36.2, 1.3, 395, 28.4, 0, 2, 29.1, 21.5, 74, 87, 181.0, 161.0, 1.7, 14.3, 1.10, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.9, 1.2, 395, 28.0, 0, 2, 29.3, 21.5, 74, 87, 183.0, 161.0, 1.6, 14.3, 1.12, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.8, 1.1, 395, 28.1, 0, 2, 29.3, 20.9, 74, 85, 181.0, 161.0, 1.7, 14.2, 1.12, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.7, 1.0, 395, 28.1, 0, 2, 29.3, 20.9, 73, 86, 181.0, 162.0, 1.6, 14.2, 1.13, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.8, 1.2, 395, 28.1, 0, 2, 29.3, 20.9, 73, 85, 181.0, 162.0, 1.5, 14.1, 1.13, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.9, 1.1, 395, 28.3, 0, 2, 29.5, 20.8, 72, 85, 183.0, 162.0, 1.6, 14.1, 1.13, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 36.0, 1.3, 395, 28.2, 0, 2, 29.5, 20.8, 72, 84, 183.0, 163.0, 1.7, 14.1, 1.13, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.9, 1.4, 395, 28.2, 0, 2, 29.6, 20.7, 71, 84, 182.0, 163.0, 1.9, 14.0, 1.12, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.7, 1.4, 395, 28.2, 0, 2, 29.6, 20.7, 71, 84, 182.0, 163.0, 1.8, 14.0, 1.12, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.6, 1.4, 395, 28.3, 0, 2, 29.6, 20.7, 70, 83, 182.0, 164.0, 1.8, 14.0, 1.11, 0.4, 0.1, 232.0, false),
('rpt-riko-001', '2025-05-30T13:10:00', 35.4, 1.3, 395, 28.3, 0, 2, 29.6, 20.4, 72, 82, 183.0, 164.0, 1.8, 14.2, 1.11, 0.4, 0.1, 232.0, false),
-- rpt-riko-002
('rpt-riko-002', '2025-05-30T14:00:00', 36.0, 1.2, 400, 27.5, 0, 2, 27.0, 21.0, 74, 87, 178.0, 158.0, 1.7, 13.8, 1.05, 0.3, 0.2, 228.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.3, 400, 28.0, 0, 2, 28.5, 21.4, 75, 88, 181.0, 160.0, 1.81, 14.1, 1.09, 0.3, 0.2, 229.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.3, 400, 27.8, 0, 2, 28.0, 21.3, 75, 87, 181.0, 161.0, 1.81, 14.1, 1.10, 0.3, 0.2, 231.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.3, 400, 28.0, 0, 2, 28.0, 21.3, 76, 87, 181.0, 162.0, 1.81, 15.1, 1.08, 0.3, 0.2, 231.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.2, 400, 28.0, 0, 2, 28.0, 21.3, 76, 87, 181.0, 163.0, 1.82, 14.6, 1.06, 0.3, 0.2, 231.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.2, 400, 28.1, 0, 2, 28.0, 21.3, 76, 87, 181.0, 162.0, 1.81, 14.9, 1.03, 0.3, 0.2, 232.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.2, 400, 28.1, 0, 2, 28.1, 21.3, 76, 87, 182.0, 162.0, 1.81, 14.6, 1.02, 0.3, 0.2, 232.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.3, 400, 28.1, 0, 2, 28.1, 21.4, 75, 86, 182.0, 161.0, 1.81, 14.6, 1.01, 0.3, 0.2, 231.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.3, 400, 28.3, 0, 2, 28.1, 21.4, 75, 86, 182.0, 161.0, 1.82, 14.6, 1.03, 0.3, 0.2, 231.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.4, 400, 28.3, 0, 2, 28.1, 21.4, 75, 86, 182.0, 162.0, 1.82, 14.5, 1.04, 0.3, 0.2, 230.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.4, 400, 28.3, 0, 2, 28.1, 21.4, 75, 88, 180.0, 162.0, 1.83, 14.4, 1.05, 0.3, 0.2, 230.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.4, 400, 28.2, 0, 2, 28.4, 21.4, 74, 88, 180.0, 163.0, 1.83, 14.3, 1.07, 0.3, 0.2, 230.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.3, 400, 28.2, 0, 2, 28.4, 21.4, 74, 88, 180.0, 162.0, 1.84, 14.0, 1.08, 0.3, 0.2, 231.0, false),
('rpt-riko-002', '2025-05-30T14:10:00', 36.2, 1.3, 400, 28.2, 0, 2, 28.4, 21.4, 74, 87, 181.0, 163.0, 1.85, 14.1, 1.09, 0.3, 0.2, 231.0, false);

