package com.revature.ers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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

			String sql = "SELECT * FROM ers_reimbursement JOIN ers_users ON (reimb_author = ers_user_id)";
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
		
		Reimbursement reimb = new Reimbursement();
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			PreparedStatement pstate = conn.prepareStatement("SELECT * FROM ers_reimbursement WHERE reimb_id = ?");
			pstate.setInt(1, id);
			
			ResultSet rs = pstate.executeQuery();
			reimb = this.mapResultSet(rs).get(0);
			
		} catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		return reimb;
	}

	
	
	@Override
	public Reimbursement add(Reimbursement newReimb) {
	
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false); //look into why we set autocommit to false before we actually call the statement
			
			String sql = "INSERT INTO ers_reimbursement VALUES (0, ?, ?, ?, ?, null, ?, 1, 1, ?)";
			//null value here represents the BLOB receipt value
			
			String[] keys = new String[1];
			keys[0] = "reimb_id";

			
			PreparedStatement pstate = conn.prepareStatement(sql, keys);
			
//			pstate.setInt(1, newReimb.getReimbId());
			pstate.setInt(1, newReimb.getAmount());
			pstate.setTimestamp(2, newReimb.getReimbSubmitted());
			pstate.setTimestamp(3, newReimb.getReimbResolved());
			pstate.setString(4, newReimb.getReimbDescription());
			//here, this represents the null value for BLOB

			// find out how to set this as the current logged in user id (maybe principal/jwt/claims)

			pstate.setInt(5, newReimb.getAuthorId()); 
			// below, we removed the parameter and forced the resolverId to be 1 (ADMIN)
			// pstate.setInt(7, newReimb.getResolverId());

			// below, we removed the parameter and forced default statusId to be 1 (PENDING)
			// pstate.setInt(8, newReimb.getStatusId());
			// typeId should correlate to the databsae
			pstate.setInt(6, newReimb.getTypeId());		
			
			int rowsInserted = pstate.executeUpdate();
			
			ResultSet rs = pstate.getGeneratedKeys();
			
			if (rowsInserted != (0)) {
				while (rs.next()) {
					newReimb.setReimbId(rs.getInt(1));
				}
				
				conn.commit();
			}
		} 
		
		catch(SQLIntegrityConstraintViolationException sivce) {
			log.warn("Reimbursement already exists!");
			log.warn(sivce.getMessage());
		}
		catch(SQLException e) {
			log.error(e.getMessage());
		}
		
		if (newReimb.getReimbId() == 0) return null;
		
		return newReimb;
	}

	
	
	@Override
	public Reimbursement update(Reimbursement updatedReimb) {
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = ("UPDATE ers_reimbursements SET reimb_amount = ?, reimb_status_id = ?, reimb_type_id = ? WHERE reimb_id = ?");
			PreparedStatement pstate = conn.prepareStatement(sql);
			
			pstate.setInt(1, updatedReimb.getAmount());
			pstate.setInt(2, updatedReimb.getStatusId());
			pstate.setInt(3, updatedReimb.getTypeId());
			pstate.setInt(4, updatedReimb.getReimbId());
			pstate.setInt(5, updatedReimb.getReimbId());
			
			int rowsUpdated = pstate.executeUpdate();
			if (rowsUpdated != 0) {
				conn.commit();
				return updatedReimb;
			}
					
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}

	
	@Override
	public boolean delete(int id) {
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
}
