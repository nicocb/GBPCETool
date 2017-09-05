package com.gracie.barra.admin.objects;

import java.util.ArrayList;
import java.util.List;

import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;

public class CertificationCriteriaByRank {
	private CertificationCriterionRank rank;
	private List<CertificationCriterion> criteria = new ArrayList<>();
	private Long score = 0L;

	public CertificationCriterionRank getRank() {
		return rank;
	}

	public void setRank(CertificationCriterionRank currentRank) {
		this.rank = currentRank;
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
