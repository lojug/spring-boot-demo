insert into course (course_name, course_description) values ('Computer Science 101', 'Computer Science Fundamentals');
insert into course (course_name, course_description) values ('Computer Science 201', 'Data Structures and Algorithms');
insert into course (course_name, course_description) values ('Computer Science 301', 'Object Oriented Design and Analysis');

insert into student (name, address, email) values ('Timmy', '420 High Street', 'timmy@example.com');
insert into student (name, address, email) values ('Billy', '360 Roundabout Crescent', 'billy@example.com');
insert into student (name, address, email) values ('Bobby', '10 Downer Street', 'bobby@example.com');

insert into users values ('Timmy', 'password', true);
insert into users values ('Billy', 'password', true);
insert into users values ('Bobby', 'password', true);

insert into authorities values ('Timmy', 'ROLE_STUDENT');
insert into authorities values ('Billy', 'ROLE_STUDENT');
insert into authorities values ('Bobby', 'ROLE_STUDENT');

insert into enrollment values (1,1);
insert into enrollment values (2,2);
