package com.revature.ers.models;

import oracle.sql.TIMESTAMP;

public class Reimbursement {
	private int reimbId;
	private int amount;
	private TIMESTAMP reimbSubmitted;
	private TIMESTAMP reimbResolved;
	private String reimbDescription;
//	private BlobDBAccess vs BLOB 
	private int authorId;
	private int resolverId;
	private int statusId;
	private int typeId;


	public Reimbursement() {
		super();
	}
	
	
	public Reimbursement(int reimbId, int amount, TIMESTAMP reimbSubmitted, TIMESTAMP reimbResolved,
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


	public TIMESTAMP getReimbSubmitted() {
		return reimbSubmitted;
	}


	public void setReimbSubmitted(TIMESTAMP reimbSubmitted) {
		this.reimbSubmitted = reimbSubmitted;
	}


	public TIMESTAMP getReimbResolved() {
		return reimbResolved;
	}


	public void setReimbResolved(TIMESTAMP reimbResolved) {
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
