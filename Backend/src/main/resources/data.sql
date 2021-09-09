INSERT INTO room (name, description, location, capacity) VALUES
('Oval Office', 'Good projector, great oak table', 'Main Building, Room 4.03', 20),
('Meeting Room A', 'Big flip chart.', 'Main Building, Room 0.03', 10),
('Meeting Room B', 'Big flip chart, multiple tablets', 'Main Building, Room 0.04', 12),
('Trophy Room', 'Great view, ideal for official company events', 'Main Building, Room 1.01', 25),
('Infinity Room', 'Inspirational room', 'Main Building, Room 4.01', 42),
('Dining Room', 'Conference room with dining furniture', 'Main Building, Room 0.01', 50),
('Theatre', 'Theatre for presentations', 'Theatre Building, Room 1.01', 1000),
('Private Room A', 'Small room for small meetings', 'Main Building, Room 1.12', 3),
('Private Room B', 'Small room for small meetings', 'Main Building, Room 1.13', 3),
('Private Room C', 'Small room for small meetings', 'Main Building, Room 1.14', 4),
('Conference Hall', 'Ideal for conferences', 'Conference Building, Room 1', 1200);


INSERT INTO role (name) VALUES
('admin'),
('user');

INSERT INTO email_verification (new_email) VALUES
('steve@apple.com'),
(null),
('mark@insta.com');

INSERT INTO app_user (first_name, second_name, email, password, email_verification_id_id, role_id) VALUES
('Steve', 'Jobs', null, '12345678', 1, 1),
('Larry', 'Ellison', 'larry@oracle.com',  '12345678', 2, 2),
('Mark', 'Zuckerberg', 'mark@facebook.de', '12345678', 3, 2);

INSERT INTO booking (purpose, date, start_booking, end_booking, app_user_id, room_id) VALUES
('Beat Android', "2020-09-29", "2020-09-29T09:00:00", "2020-09-29T09:30:00", 1, 1),
('Lunch Break', "2020-09-29", "2020-09-29T12:00:00", "2020-09-29T12:30:00", 2, 2),
('Present User Data to public', "2020-09-29", "2020-09-29T08:00:00", "2020-09-29T12:00:00", 3, 3),
('Key Note', "2020-09-29", "2020-09-29T08:00:00", "2020-09-29T12:00:00", 3, 7);

