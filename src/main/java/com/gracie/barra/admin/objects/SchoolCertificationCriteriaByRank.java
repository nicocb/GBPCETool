package com.gracie.barra.admin.objects;

import java.util.ArrayList;
import java.util.List;

import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;

public class SchoolCertificationCriteriaByRank {
	private CertificationCriterionRank rank;
	private List<SchoolCertificationCriterion> criteria = new ArrayList<>();
	private Long score = 0L;
	private Long actualScore = 0L;
	private Boolean available = false;

	public CertificationCriterionRank getRank() {
		return rank;
	}

	public void setRank(CertificationCriterionRank rank) {
		this.rank = rank;
	}

	public List<SchoolCertificationCriterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<SchoolCertificationCriterion> criteria) {
		this.criteria = criteria;
	}

	public void incScore(Long score) {
		this.score += score;
	}

	public void incActualScore(Long score) {
		this.actualScore += score;
	}

	public Long getScore() {
		return score;
	}

	public Long getActualScore() {
		return actualScore;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Boolean getValidated() {
		return score == actualScore;
	}

	public boolean topScore() {
		return actualScore == score;
	}
}
