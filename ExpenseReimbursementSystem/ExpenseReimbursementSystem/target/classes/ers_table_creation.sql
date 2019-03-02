
-- should the users (employee/manager) be able to sign up with the system?
	-- up for discussion on various methods 
	-- templates vs angular templates
	-- servlets handling register/login logic
	
-- ************************************************
-- table creation
-- ************************************************
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
	FOREIGN KEY (ers_user_role_id)
	REFERENCES ers_user_roles (ers_user_role_id)
);


CREATE TABLE ers_user_roles(
	ers_user_role_id	NUMBER,
	user_role 			VARCHAR2(10),
	
	CONSTRAINT ers_user_role_pk
	PRIMARY KEY (ers_user_role_id)
);


CREATE TABLE ers_reimbursement(
	reimb_id		NUMBER,
	reimb_amount	NUMBER,
	reimb_submitted	TIMESTAMP,
	reimb_resolved	TIMESTAMP,
	reimb_description VARCHAR2(250),
	reimb_receipt		BLOB,
	reimb_author		NUMBER,
	reimb_resolver		NUMBER,
	reimb_status_id		NUMBER,
	reimb_type_id		NUMBER,
	
	CONSTRAINT ers_reimbursement_pk
	PRIMARY KEY (reimb_id),
	
	CONSTRAINT ers_users_fk_auth
	FOREIGN KEY (reimb_author) 
	REFERENCES ers_users (ers_user_id) 
	
	CONSTRAINT ers_users_fk_reslvr
	FOREIGN KEY (reimb_resolver)
	REFERENCES ers_users_roles (ers_user_role_id)  
	-- typical roles would be employee (1) and manager (2)
	-- resolver will be the user tied to a specific user_role_id defining the "privileges"
	
	CONSTRAINT ers_reimbursement_status_fk
	FOREIGN KEY (reimb_status_id)
	REFERENCES ers_reimbursement_status (reimb_status_id)
	
	CONSTRAINT ers_reimbursement_type_tk
	FOREIGN KEY (reimb_type_id)
	REFERENCES ers_reimbursement_type (reimb_type_id)
);


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
	PRIMARY (reimb_type_id)
);

-- ************************************************
-- trigger creation
-- ************************************************
CREATE OR REPLACE TRIGGER ers_users_pk_trigger BEFORE
    INSERT ON ers_users
    FOR EACH ROW
BEGIN
    SELECT
        ers_users_pk_seq.NEXTVAL
    INTO :new.ers_user_id
    FROM
        dual;
END;
/


CREATE OR REPLACE TRIGGER ers_roles_pk_trigger BEFORE
    INSERT ON ers_user_roles
    FOR EACH ROW
BEGIN
    SELECT
        ers_user_roles_pk_seq.NEXTVAL
    INTO :new.user_role
    FROM
        dual;
END;
/


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


-- ************************************************
-- sequence creation
-- we'll come back to the status and type
-- ************************************************
CREATE SEQUENCE ers_users_pk_seq 
MINVALUE 1
MAXVALUE 999999999
INCREMENT BY 1
START WITH 1;

CREATE SEQUENCE ers_user_roles_pk_seq
MINVALUE 1
MAXVALUE 999999999
INCREMENT BY 1
START WITH 1;

CREATE SEQUENCE ers_reimb_id_pk_seq
MINVALUE 1
MAXVALUE 999999999
INCREMENT BY 1
START WITH 1;

-- ************************************************
-- populating ers_user_roles tables 
-- ************************************************
INSERT INTO ers_user_roles(1, 'ADMIN');
INSERT INTO ers_user_roles(2, 'EMPLOYEE');

-- ************************************************
-- populating tables 
-- ************************************************
INSERT INTO ers_users VALUES(

	0, 'admin_kd', 'unicorn', 'kamaria', 'deramus', 'kderamus@gmail.com', ''
--	ers_user_id --	ers_username --	ers_password--	user_first_name --	user_last_name --	user_email--	user_role_id		


)

