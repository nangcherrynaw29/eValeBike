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
SELECT TOP 1 id
FROM app_user
WHERE email = 'bob.brown@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM super_admin WHERE id = (SELECT TOP 1 id FROM app_user WHERE email = 'bob.brown@example.com')
);
--------------------------------------
-- Example BikeOwner
--------------------------------------
INSERT INTO app_user (name, email, password, role, user_status, created_by_id, company_id)
SELECT 'John Doe', 'john.doe@example.com', '$2a$10$kU6clDpBtcQAiul4xa9GP.Liy2GmP3QCPXHeZNjBSLj240YukGx7K', 'BIKE_OWNER', 'APPROVED', 4, 1
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE email = 'john.doe@example.com');

INSERT INTO bike_owner (id, phone_number, birth_date)
SELECT id, '555-1234', '1990-05-20'
FROM app_user
WHERE email = 'john.doe@example.com'
  AND NOT EXISTS (
    SELECT 1 FROM bike_owner WHERE id = (SELECT id FROM app_user WHERE email = 'john.doe@example.com')
);

--------------------------------------
-- Insert bikes
--------------------------------------
INSERT INTO bike (bikeqr, brand, model, production_year, bike_size, mileage, gear_type, engine_type, power_train,
                  accu_capacity, max_support, max_engine_power, nominal_engine_power, engine_torque, last_test_date)
SELECT '123e4567-e89b-12d3-a456-426614174001', 'Giant', 'Talon', 2022, 'M', 1000, 'Shimano', 'Electric', 'Chain Drive',
       500.0, 25.0, 750.0, 500.0, 85.0, '2024-01-01'
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE bikeqr = '123e4567-e89b-12d3-a456-426614174001');

--------------------------------------
-- Assign bikes to owners
--------------------------------------
INSERT INTO bike_owner_bike (bike_owner_id, bike_qr)
SELECT 1, '123e4567-e89b-12d3-a456-426614174001'
WHERE NOT EXISTS (
    SELECT 1 FROM bike_owner_bike WHERE bike_owner_id = 1 AND bike_qr = '123e4567-e89b-12d3-a456-426614174001'
);

--------------------------------------
-- Insert into technician / admin tables
--------------------------------------
INSERT INTO technician (id)
SELECT 2 WHERE NOT EXISTS (SELECT 1 FROM technician WHERE id = 2);

INSERT INTO administrator (id)
SELECT 3 WHERE NOT EXISTS (SELECT 1 FROM administrator WHERE id = 3);

--------------------------------------
-- Initialize test benches
--------------------------------------
INSERT INTO test_bench (test_bench_name, last_calibration_date, location, status, technician_id)
SELECT 'Test Bench 1', CAST(GETDATE() AS DATE), 'Zone A', 'INACTIVE', NULL
WHERE NOT EXISTS (SELECT 1 FROM test_bench WHERE test_bench_name = 'Test Bench 1');

INSERT INTO test_bench (test_bench_name, last_calibration_date, location, status, technician_id)
SELECT 'Test Bench 2', CAST(GETDATE() AS DATE), 'Zone B', 'INACTIVE', NULL
WHERE NOT EXISTS (SELECT 1 FROM test_bench WHERE test_bench_name = 'Test Bench 2');

--------------------------------------
-- Insert recent_activity
--------------------------------------
INSERT INTO recent_activity (activity, description, date, user_id)
SELECT 'CREATED_USER', 'Created technician Mike', '2025-04-26', 4
WHERE NOT EXISTS (
    SELECT 1 FROM recent_activity WHERE activity = 'CREATED_USER' AND user_id = 4
);

--------------------------------------
-- Reset identity seeds didnt find a way to make it work in azure sql
--------------------------------------

