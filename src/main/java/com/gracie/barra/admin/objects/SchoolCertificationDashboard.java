package com.gracie.barra.admin.objects;

import java.util.List;

public class SchoolCertificationDashboard {

	private List<SchoolCertificationCriteriaByRank> criteria;
	private Long overallScore = 0L;
	private Long nbPending = 0L;
	private Long nbMissing = 0L;

	public List<SchoolCertificationCriteriaByRank> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<SchoolCertificationCriteriaByRank> criteria) {
		this.criteria = criteria;
	}

	public Long getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(Long overallScore) {
		this.overallScore = overallScore;
	}

	public Long getNbPending() {
		return nbPending;
	}

	public void setNbPending(Long nbPending) {
		this.nbPending = nbPending;
	}

	public Long getNbMissing() {
		return nbMissing;
	}

	public void setNbMissing(Long nbMissing) {
		this.nbMissing = nbMissing;
	}

	public void incNbMissing() {
		this.nbMissing++;

	}

	public void incNbPending() {
		this.nbPending++;

	}

}
