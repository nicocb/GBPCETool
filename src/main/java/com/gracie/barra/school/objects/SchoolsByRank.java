package com.gracie.barra.school.objects;

import java.util.ArrayList;
import java.util.List;

import com.gracie.barra.admin.objects.CertificationCriterion.CertificationCriterionRank;

public class SchoolsByRank {
	private CertificationCriterionRank rank;
	private List<ScoredSchool> schools = new ArrayList<>();

	public CertificationCriterionRank getRank() {
		return rank;
	}

	public void setRank(CertificationCriterionRank rank) {
		this.rank = rank;
	}

	public List<ScoredSchool> getSchools() {
		return schools;
	}

	public void setSchools(List<ScoredSchool> schools) {
		this.schools = schools;
	}

}
