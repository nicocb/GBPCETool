package com.gracie.barra.admin.objects;

public class CertificationCriterion {
	private Long id;
	private String description;
	private String comment;
	private String action;
	private CertificationCriterionRank rank;
	private Long score;

	public static final String ID = "id";
	public static final String DESCRIPTION = "descrition";
	public static final String COMMENT = "comment";
	public static final String ACTION = "action";
	public static final String RANK = "rank";
	public static final String SCORE = "score";

	public static class Builder {
		private String description;
		private String comment;
		private String action;
		private CertificationCriterionRank rank;
		private Long score;
		private Long id;

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder comment(String comment) {
			this.comment = comment;
			return this;
		}

		public Builder action(String action) {
			this.action = action;
			return this;
		}

		public Builder rank(CertificationCriterionRank rank) {
			this.rank = rank;
			return this;
		}

		public Builder score(Long score) {
			this.score = score;
			return this;
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public CertificationCriterion build() {
			return new CertificationCriterion(this);
		}
	}

	private CertificationCriterion(Builder builder) {
		this.id = builder.id;
		this.description = builder.description;
		this.comment = builder.comment;
		this.action = builder.action;
		this.rank = builder.rank;
		this.score = builder.score;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertificationCriterionRank getRank() {
		return rank;
	}

	public void setRank(CertificationCriterionRank rank) {
		this.rank = rank;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public enum CertificationCriterionRank {
		TRAINING_CENTER("Training Center"), OFFICIAL_SCHOOL("Official School"), PREMIUM_SCHOOL("Premium School");

		private String description;

		CertificationCriterionRank(String descrition) {
			this.description = descrition;
		}

		public String getDescription() {
			return description;
		}

		public static CertificationCriterionRank fromId(Long id) {
			return CertificationCriterionRank.values()[id.intValue() - 1];
		}

		public Long getId() {
			return Long.valueOf(ordinal() + 1L);
		}

	}

}
