package com.revature.ers.models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Reimbursement {

	@JsonIgnore 						// may be needed to match JSON files for transmitting to js
	private int reimbId; 					
	private int amount;						

	@JsonIgnore 						// may be needed to match JSON files for transmitting to js
	private Timestamp reimbSubmitted; 		

	@JsonIgnore 						// may be needed to match JSON files for transmitting to js
	private Timestamp reimbResolved; 		
	private String reimbDescription;		

	// authorId --> current logged in user submitting reimbursement
	private int authorId;					

	/*
	 ---------------------------------------------------------------------------------------------------
	 resolverId --> manager typically resolves reimbursements with statusId: 1 with value of 'PENDING
	 ---------------------------------------------------------------------------------------------------
	 1. managers make changes to reimbursements of employees
	 2. manager (mgr1) cannot make updates to reimbursements mgr1 submitted
		 2.1 however, mgr1 submitted reimbursements can be updated/modified by other manager (e.g. mgr2)
	*/
	private int resolverId;					
	
	/*
	 ------------------------
	 status of reimbursement
	 ------------------------
	 1 -> 'PENDING'
	 2 -> 'APPROVED'
	 3 -> 'DONE'
	 4 -> 'DENIED'
	*/
	private int statusId;											

	/*
	 -----------------------
	 types of reimbursement
	 -----------------------
	 1 -> 'LODGING'
	 2 -> 'TRAVEL'
	 3 -> 'FOOD'
	 4 -> 'OTHER'
	*/
	private int typeId;

	public Reimbursement() {
		super();
	}
	
	
	public Reimbursement(int reimbId, int amount, Timestamp reimbSubmitted, Timestamp reimbResolved,
			String reimbDescription, int authorId, int resolverId, int statusId, int typeId) {
		super();
		this.reimbId = reimbId;
		this.amount = amount;
		this.reimbSubmitted = reimbSubmitted;
		this.reimbResolved = reimbResolved;
		this.reimbDescription = reimbDescription;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.statusId = statusId;
		this.typeId = typeId;
	}
	
	public Reimbursement(int amount,String reimbDescription, int authorId, int statusId, int typeId) {
		super();
		this.amount = amount;
		this.reimbDescription = reimbDescription;
		this.authorId = authorId;
		this.statusId = statusId;
		this.typeId = typeId;
	}


	public int getReimbId() {
		return reimbId;
	}


	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public Timestamp  getReimbSubmitted() {
		return reimbSubmitted;
	}


	public void setReimbSubmitted(Timestamp reimbSubmitted) {
		this.reimbSubmitted = reimbSubmitted;
	}


	public Timestamp getReimbResolved() {
		return reimbResolved;
	}


	public void setReimbResolved(Timestamp reimbResolved) {
		this.reimbResolved = reimbResolved;
	}


	public String getReimbDescription() {
		return reimbDescription;
	}


	public void setReimbDescription(String reimbDescription) {
		this.reimbDescription = reimbDescription;
	}


	public int getAuthorId() {
		return authorId;
	}


	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}


	public int getResolverId() {
		return resolverId;
	}


	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}


	public int getStatusId() {
		return statusId;
	}


	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}


	public int getTypeId() {
		return typeId;
	}


	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + authorId;
		result = prime * result + ((reimbDescription == null) ? 0 : reimbDescription.hashCode());
		result = prime * result + reimbId;
		result = prime * result + ((reimbResolved == null) ? 0 : reimbResolved.hashCode());
		result = prime * result + ((reimbSubmitted == null) ? 0 : reimbSubmitted.hashCode());
		result = prime * result + resolverId;
		result = prime * result + statusId;
		result = prime * result + typeId;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (amount != other.amount)
			return false;
		if (authorId != other.authorId)
			return false;
		if (reimbDescription == null) {
			if (other.reimbDescription != null)
				return false;
		} else if (!reimbDescription.equals(other.reimbDescription))
			return false;
		if (reimbId != other.reimbId)
			return false;
		if (reimbResolved == null) {
			if (other.reimbResolved != null)
				return false;
		} else if (!reimbResolved.equals(other.reimbResolved))
			return false;
		if (reimbSubmitted == null) {
			if (other.reimbSubmitted != null)
				return false;
		} else if (!reimbSubmitted.equals(other.reimbSubmitted))
			return false;
		if (resolverId != other.resolverId)
			return false;
		if (statusId != other.statusId)
			return false;
		if (typeId != other.typeId)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", amount=" + amount + ", reimbSubmitted=" + reimbSubmitted
				+ ", reimbResolved=" + reimbResolved + ", reimbDescription=" + reimbDescription + ", authorId="
				+ authorId + ", resolverId=" + resolverId + ", statusId=" + statusId + ", typeId=" + typeId + "]";
	}
}