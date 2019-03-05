package com.revature.ers.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.models.Reimbursement;
import com.revature.ers.util.ConnectionFactory;

public class ReimbursementDao implements DAO<Reimbursement>{

	private static Logger log = Logger.getLogger(ReimbursementDao.class);

	@Override
	public List<Reimbursement> getAll() {
		List<Reimbursement> reimbursements = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){

			//TODO -- testing queries requesting all reimbursements -- NOT TESTED
			String sql = "SELECT * FROM ERS_REIMBURSEMENT ORDER BY REIMB_AUTHOR";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			
			reimbursements = this.mapResultSet(rs);
			
		}
		catch(SQLException e) {
			log.error(e.getMessage());
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
			reimb.setReimbDescription(rs.getString("reimb_description"));
			reimb.setAuthorId(rs.getInt("reimb_author"));
			reimb.setResolverId(rs.getInt("reimb_resolver"));
			reimb.setStatusId(rs.getInt("reimb_status_id"));
			reimb.setTypeId(rs.getInt("reimb_type_id"));
			
			reimbursements.add(reimb);
		}
		return reimbursements;
	}
<<<<<<< HEAD
}
=======
}
>>>>>>> 5de1f29fbf77556a0d516b44f873697cd810c394
