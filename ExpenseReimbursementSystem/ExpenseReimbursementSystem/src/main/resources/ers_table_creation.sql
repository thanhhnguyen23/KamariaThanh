
---- ************************************************
---- used to reset tables 
---- ************************************************
--DROP TABLE ers_users;       
--DROP TABLE ers_user_roles;  
--DROP TABLE ers_reimbursement;
--DROP TABLE ers_reimbursement_status;
--DROP TABLE ers_reimbursement_type;

---- ************************************************
---- used to drop sequences
---- ************************************************
--drop sequence ers_reimb_id_pk_seq;


	-------------------------------------------
	-- TODO
	-------------------------------------------
	-- templates vs angular templates
	-- servlets handling register/login logic
	-------------------------------------------

	-- drop table cascade constraints -- 
	------------------------------------

-- ************************************************
-- table creation
-- ************************************************


CREATE TABLE ers_user_roles(
	ers_user_role_id	NUMBER,
	user_role 			VARCHAR2(10),
	
	CONSTRAINT ers_user_role_pk
	PRIMARY KEY (ers_user_role_id)
);
-- WORKS

CREATE TABLE ers_users(
	ers_user_id 		NUMBER,
	ers_username		VARCHAR2(50) UNIQUE,
	ers_password		VARCHAR2(50),
	user_first_name 	VARCHAR2(100),
	user_last_name		VARCHAR2(100),
	user_email			VARCHAR2(150) UNIQUE,
	user_role_id		NUMBER,
	
	CONSTRAINT ers_users_pk
	PRIMARY KEY (ers_user_id),
	
	CONSTRAINT users_role_fk
	FOREIGN KEY (user_role_id)
	REFERENCES ers_user_roles (ers_user_role_id)
);
-- WORKS



CREATE TABLE ers_reimbursement_status( 
	reimb_status_id 	NUMBER,
	reimb_status		VARCHAR2(10),
	
	CONSTRAINT reimb_status_pk 
	PRIMARY KEY (reimb_status_id)
);


CREATE TABLE ers_reimbursement_type(
	reimb_type_id	NUMBER,
	reimb_type		VARCHAR2(10),
	
	CONSTRAINT reimb_type_pk
	PRIMARY KEY (reimb_type_id)
);



CREATE TABLE ers_reimbursement(
	reimb_id			NUMBER,
	reimb_amount		NUMBER,
	reimb_submitted		TIMESTAMP,
	reimb_resolved		TIMESTAMP,
	reimb_description 	VARCHAR2(250),
	reimb_receipt		BLOB,
	reimb_author		NUMBER,
	reimb_resolver		NUMBER,
	reimb_status_id		NUMBER,
	reimb_type_id		NUMBER,
	
	CONSTRAINT ers_reimbursement_pk
	PRIMARY KEY (reimb_id),
	
	CONSTRAINT ers_users_fk_auth
	FOREIGN KEY (reimb_author) 
	REFERENCES ers_users (ers_user_id),
	
	CONSTRAINT ers_users_fk_reslvr
	FOREIGN KEY (reimb_resolver)
	REFERENCES ers_user_roles (ers_user_role_id),
	
	CONSTRAINT ers_reimbursement_status_fk
	FOREIGN KEY (reimb_status_id)
	REFERENCES ers_reimbursement_status (reimb_status_id),
	
	CONSTRAINT ers_reimbursement_type_fk
	FOREIGN KEY (reimb_type_id)
	REFERENCES ers_reimbursement_type (reimb_type_id)
);


-- ***************************************************
-- trigger creation
-- ***************************************************
-- COME BACK TO THIS, NOT WORKING -- LET'S TEST
-- ***************************************************
CREATE OR REPLACE TRIGGER ers_reimb_pk_trigger BEFORE
    INSERT ON ers_reimbursement
    FOR EACH ROW
BEGIN
    SELECT
        ers_reimb_id_pk_seq.NEXTVAL
    INTO :new.reimb_id
    FROM
        dual;
END;
/
--****************************************************


-- ***************************************************
-- sequence creation
-- ***************************************************

CREATE SEQUENCE ers_reimb_id_pk_seq
MINVALUE 1
MAXVALUE 999999999
INCREMENT BY 1
START WITH 1;



select * from ers_user_roles;


-- ************************************************
-- populating ers_user_roles tables 
-- ************************************************
INSERT INTO ers_user_roles values (1, 'ADMIN');
INSERT INTO ers_user_roles values (2, 'EMPLOYEE');


-- ************************************************
-- populating ers_reimbursement status tables 
-- ************************************************

INSERT INTO ers_reimbursement_status values (1, 'PENDING');
INSERT INTO ers_reimbursement_status values (2, 'APPROVED');
INSERT INTO ers_reimbursement_status values (3, 'DONE');
INSERT INTO ers_reimbursement_status values (4, 'DENIED');


-- ************************************************
-- populating ers_reimbursement _type tables 
-- ************************************************
INSERT INTO ers_reimbursement_type VALUES(1, 'LODGING'); 
INSERT INTO ers_reimbursement_type VALUES(2, 'TRAVEL'); 
INSERT INTO ers_reimbursement_type VALUES(3, 'FOOD'); 
INSERT INTO ers_reimbursement_type VALUES(4, 'OTHER');


-- ************************************************
-- populating admin users to ers_users tables 
-- ************************************************
INSERT INTO ers_users VALUES( 1, 'admin_kd', 'unicorn', 'Kamaria', 'DeRamus', 'kderamus@gmail.com', 1);
INSERT INTO ers_users VALUES( 2, 'admin_tn', 'p4ssword', 'Thanh', 'Nguyen', 'tnguyen@gmail.com', 1 );

-- ************************************************
-- populating employee users to ers_users tables 
-- ************************************************
INSERT INTO ers_users VALUES( 3, 'jsmith', 'p4ssword', 'Jane', 'Smith', 'jsmith@gmail.com', 2);
INSERT INTO ers_users VALUES( 4, 'jdoe', 'deer', 'John', 'Doe', 'jdoe@gmail.com', 2);
INSERT INTO ers_users VALUES( 5, 'cjones', 'p4ssword', 'Chris', 'Jones', 'cjones@gmail.com', 2);
INSERT INTO ers_users VALUES( 6, 'jwall', 'p4ssword', 'Josh', 'Wall', 'jwall@gmail.com', 2);
INSERT INTO ers_users VALUES( 7, 'amartin', 'p4ssword', 'Amy', 'Martin', 'amartin@gmail.com', 2);


-- ************************************************
-- procedures for roles
-- ************************************************

CREATE OR replace PROCEDURE get_all_roles 
(
	my_cursor OUT sys_refcursor
)
IS 
BEGIN 
	OPEN my_cursor FOR
	SELECT * FROM ers_user_roles
	ORDER BY ers_user_role_id;
END;
/






COMMIT;
