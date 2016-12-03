package fr.squareprod.vrranking.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "ranks")
public class Rank {

	public Rank() {
		super();
	}

	public Rank(String userId, String ranking, Date date) {
		super();
		this.userId = userId;
		this.ranking = ranking;
		this.date = date;
	}

	@Id
	private String id;
	@Indexed
	private String userId;
	private String ranking;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
