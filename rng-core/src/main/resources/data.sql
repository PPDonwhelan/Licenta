
delete from role_permission;
delete from  user_role;
delete from  permissions;
delete from  roles;
delete from  users;

delete from  subjects;
delete from  tests;
delete from  test_category;
delete from  results;
delete from  samples;

INSERT INTO permissions (id, name) VALUES 
(1, 'MANAGE_TEST_CATEGORIES'),
(2, 'MANAGE_SUBJECTS'),
(3, 'MANAGE_SAMPLES'),
(4, 'MANAGE_USERS'),
(5, 'MANAGE_ROLES'),
(6, 'MANAGE_PERMISSIONS'),
(7, 'MANAGE_SETTINGS'),
(8, 'MANAGE_TESTS')
;

INSERT INTO roles (id, name) VALUES 
(1, 'ROLE_SUPER_ADMIN'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_CMS_ADMIN'), 
(4, 'ROLE_USER')
;

INSERT INTO users (id, email, password, name) VALUES 
(1, 'superadmin@gmail.com', '$2a$10$p3PHnpoBAnzZiL8mr3gMieMhVVSb585ajC2ZsBB0kwb4KvZKFSdNi', 'Super Admin'),
(2, 'admin@gmail.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Admin'),
(3, 'cms@gmail.com', '$2y$10$sYgjsgbWchrpontJNLWY3eW7A5yr2ayiSfOg0sAG2.Yw7bjWTBY4W', 'CMSGuy'),
(4, 'anto@gmail.com', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'Anto'),
(5, 'user@gmail.com', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'DemoUser')

;

insert into role_permission(role_id, perm_id) values
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),
(2,1),(2,2),(2,3),(2,4),(2,5),(2,8),
(3,1),(3,2),(3,8)
;

insert into user_role(user_id, role_id) values
(1,1),
(2,2),
(3,3),
(4,2),(4,3),
(5,4)
;

insert into test_category(id, name, description) values
(1,'Mostre lingvistice','Mostre obtinute intr-o limba straina'),
(2,'Mostre web','Mostre obtinute folosind un mijloc web'),
(3,'Mostre verbale','Mostre obtinute prin rostirea numerelor')
;

insert into tests(id, name, description) values
(1,'FreqTest','Frequency Test'),
(2,'SerialTest','Serial Test'),
(3,'GapTest','Gap Test'),
(4,'PokerTest','Poker Test'),
(5,'CouponTest','Coupon Test'),
(6,'PermutationTest','Permutation Test'),
(7,'RunsUpDTest','Runs up Test'),
(8,'RunsUpIndep1Test','Runs up Independent Test'),
(9,'RunsUpIndep2Test','Runs up Independent Test 2'),
(10,'MaxOfTTest','Max of T Test'),
(11,'SerialCorrelationTest','Serial Correlation Test')
;

insert into tests_to_category(test_id, category_id) VALUES
(1,1),(1,2),(1,3),
(2,1),(2,2),(2,2),
(3,1),(3,2),
(4,1),(4,2),(4,3),
(5,2),
(6,1),(6,2),(6,3),
(7,3),
(8,1),(8,3),
(9,1),(9,2),(9,3),
(10,2),
(11,1),(11,2),(11,3)
;

INSERT INTO subjects (id, email, age, gender) VALUES ('1', 'ana@gmail.ro', '22', 'female');
INSERT INTO subjects (id, email, age, gender) VALUES ('2', 'ion@gmail.ro', '23', 'male');
