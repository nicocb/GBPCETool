package com.gracie.barra.school.objects;

import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;

public class ScoredSchool {
	private CertificationCriterionRank rank;
	private School school;
	private Long score;
	private Long nbPending;

	public CertificationCriterionRank getRank() {
		return rank;
	}

	public void setRank(CertificationCriterionRank rank) {
		this.rank = rank;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getNbPending() {
		return nbPending;
	}

	public void setNbPending(Long nbPending) {
		this.nbPending = nbPending;
	}

}
