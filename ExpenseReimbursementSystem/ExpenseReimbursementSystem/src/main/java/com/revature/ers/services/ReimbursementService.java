package com.revature.ers.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.ReimbursementDao;
import com.revature.ers.models.Reimbursement;

public class ReimbursementService {
	private static Logger log = Logger.getLogger(ReimbursementService.class);

	private ReimbursementDao rDao = new ReimbursementDao();

	public List<Reimbursement> getAllReimbursements() {
		return rDao.getAll();
	}

	public Reimbursement getReimbursementById(int reimbId) {
		return rDao.getById(reimbId);
	}

	public Reimbursement addReimbursement(Reimbursement newReimb) {
		// get amount
		if (newReimb.getAmount() == 0 || newReimb.getReimbDescription().equals("") || newReimb.getAuthorId() == 0
				|| newReimb.getTypeId() == 0) {
			return null;
		}
		return rDao.add(newReimb);
	}

	public Reimbursement updateReimbursement(Reimbursement updatedReimb) {
		if (updatedReimb.getAmount() != 0 || updatedReimb.getReimbDescription().equals("")
				|| updatedReimb.getAuthorId() != 0 || updatedReimb.getTypeId() != 0) {
			return null;
		}
		Reimbursement persistedReimb = rDao.update(updatedReimb);

		if (persistedReimb != null) {
			return updatedReimb;
		}

		return null;
	}

	// update reimbursement status from pending(1) to approved (2)
	public Reimbursement updateReimbStatus(Reimbursement updatedReimbStatus) {
		log.info("inside updateReimbStatus method()");

		if(updatedReimbStatus.getAmount() != 0 || updatedReimbStatus.getReimbDescription().equals("") || updatedReimbStatus.getAuthorId() != 0 || updatedReimbStatus.getAuthorId() != 0) {
//			return null;
			return rDao.updateReimbStatus(updatedReimbStatus);
		}
//		if (updatedReimbStatus != null & updatedReimbStatus.getStatusId() == 1) {
////			log.info("inside updateReimbStatus method()");
//			return rDao.updateReimbStatus(updatedReimbStatus);
//		}
		return null;

	}

	// getting reimbursements by author id
	public List<Reimbursement> getReimbByAuthorId(int getReimbByAuthorId) {
		return rDao.reimbByAuthorId(getReimbByAuthorId);
	}
}
