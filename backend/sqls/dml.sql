USE rental_house;
INSERT INTO user (id, email, name, password, phone_number, role, status, created_at, updated_at) 
VALUES (1000, 'admin@gmail.com', 'Admin', '$2a$10$kEw.ip.VcctOYgsHrRldEexoLybDptm92EWnPGs19rQYCh72n6L46', '08099992222', 'admin', 'active', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());