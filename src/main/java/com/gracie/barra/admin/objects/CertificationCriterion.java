package com.gracie.barra.admin.objects;

public class CertificationCriterion {
	private Long id;
	private String description;
	private String comment;
	private String action;
	private Long rank;
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
		private Long rank;
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

		public Builder rank(Long rank) {
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

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

}
