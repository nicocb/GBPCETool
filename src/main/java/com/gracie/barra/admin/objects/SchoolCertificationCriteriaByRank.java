package com.gracie.barra.admin.objects;

import java.util.ArrayList;
import java.util.List;

public class SchoolCertificationCriteriaByRank {
	private Long rank;
	private List<SchoolCertificationCriterion> criteria = new ArrayList<>();
	private Long score = 0L;
	private Long actualScore = 0L;
	private Boolean available = false;

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
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

	public boolean topScore() {
		return actualScore == score;
	}
}
