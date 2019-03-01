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
	FOREIGN KEY (reimb_author) -- is the foreign key referencing itself?
	REFERENCES ers_users (ers_user_id) -- is the reference?
	
	CONSTRAINT ers_users_fk_reslvr
	FOREIGN KEY (reimb_resolver)
	REFERENCES ers_users_roles (ers_user_role_id) -- resolver must be a specific type of user? 
												  --how do you know this user has authorization to "resolve"

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