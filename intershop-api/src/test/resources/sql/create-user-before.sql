-- password: a
INSERT INTO users(
	name, password, email, image_url, provider, provider_id, role, username)
	VALUES ('admin', '$2a$10$GChOhed69wgE.mrntbR.rO3wtWQvVhK0/7ObCjTYobHVAGtfHu0Gy', 'admin@mycompany.com', null, 'LOCAL', '1','ADMIN', 'admin');
-- password: u
INSERT INTO users(
	name, password, email, image_url, provider, provider_id, role, username)
	VALUES ('user', '$2a$10$bFwKPxtUMmYOvtDeB2RRYu6lxVKVjE/xAlbWWiHuek1iLAimKAh3C', 'user@mycompany.com', null, 'LOCAL', '1','USER', 'user');

-- password: admin123
--insert into users(id, email, name, username, city, address, phone_number, post_index, activation_code, active, password, password_reset_code, provider)
--    values(1, 'admin@gmail.com', 'Admin', 'Admin', null, null, null, null, null, true, '$2a$08$kS2f5m8eYoNpc.ZECzndGuXiqaWmCaFfOGMQquodP48qrD7.cQG4y', null, 'LOCAL');

-- password: admin123
--insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code, provider)
--    values(122, 'test123@test.com', 'John', 'Doe', 'New York', 'Wall Street 1', '1234567890', '1234567890', null, true, '$2a$08$kS2f5m8eYoNpc.ZECzndGuXiqaWmCaFfOGMQquodP48qrD7.cQG4y', null, 'LOCAL');

--insert into users(id, email, first_name, last_name, city, address, phone_number, post_index, activation_code, active, password, password_reset_code, provider)
--    values (126, 'helloworld@test.com', 'John2', 'Doe2', 'New York', 'Wall Street1', '1234567890', '1234567890','8e97dc37-2cf5-47e2-98e0',false,'$2a$08$kS2f5m8eYoNpc.ZECzndGuXiqaWmCaFfOGMQquodP48qrD7.cQG4y','3f9bcdb0-2241-4c34-803e-598b497d571f', 'LOCAL');


