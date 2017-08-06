package com.gracie.barra.admin.objects;

import java.util.ArrayList;
import java.util.List;

public class CertificationCriteriaByRank {
	private Long rank;
	private List<CertificationCriterion> criteria = new ArrayList<>();
	private Long score = 0L;

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public List<CertificationCriterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<CertificationCriterion> criteria) {
		this.criteria = criteria;
	}

	public void incScore(Long score) {
		this.score += score;
	}

	public Long getScore() {
		return score;
	}

}
