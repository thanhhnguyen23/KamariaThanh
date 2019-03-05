package com.revature.ers.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.sql.TIMESTAMP;


import com.revature.ers.models.Reimbursement;
import com.revature.ers.util.ConnectionFactory;

public class ReimbursementDao implements DAO<Reimbursement>{

	@Override
	public List<Reimbursement> getAll() {
		List<Reimbursement> reimbursements = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){

			//TODO -- testing queries requesting all reimbursements
			String sql = "SELECT * FROM ERS_REIMBURSEMENT ORDER BY REIMB_AUTHOR";
			ResultSet rs = conn.createStatement().executeQuery(sql);

		}

		return reimbursements;
	}

	@Override
	public Reimbursement getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement add(Reimbursement obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement update(Reimbursement updatedObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private List<Reimbursement> mapResultSet(ResultSet rs) throws SQLException {
		List<Reimbursement> reimbursements = new ArrayList<>();
		while(rs.next()) {
			Reimbursement reimb = new Reimbursement();
			reimb.setReimbId(rs.getInt("reimb_id"));
			reimb.setAmount(rs.getInt("reimb_amount"));

			reimb.setReimbSubmitted(rs.getTimestamp("reimb_submitted"));
			reimb.setReimbResolved(rs.getTimestamp("reimb_resolved"));
			
			
			


///			reimb_submitted		TIMESTAMP,
//			reimb_resolved		TIMESTAMP,
//			reimb_description 	VARCHAR2(250),
//			reimb_receipt		BLOB,
//			reimb_author		NUMBER,
//			reimb_resolver		NUMBER,
//			reimb_status_id		NUMBER,
//			reimb_type_id		NUMBER,
//
			
		}
		return reimbursements;
	}
}
