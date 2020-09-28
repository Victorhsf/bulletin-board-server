package com.victorsales.bulletinboard.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Entity Notice")
@Entity
@Table(name = "notice")
public class Notice {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;

	@ApiModelProperty(notes = "Title", example = "Notice bla bla", required = true)
	@Column(name = "title", nullable = false, unique = true)
	@Size(min = 1, max = 255)
	private String title;

	@ApiModelProperty(notes = "Title", example = "This notice ... bla ...", required = true)
	@Column(name = "description", nullable = false)
	@Size(min = 1, max = 255)
	private String description;

	@ApiModelProperty(notes = "createdAt", example = "created date, automatically generated", required = false)
	@Column(name = "createdAt", updatable = false)
	private LocalDateTime createdAt;

	@ApiModelProperty(notes = "viewedAt", example = "visualization date, automatically updated", required = false)
	@Column(name = "viewedAt")
	private LocalDateTime viewedAt;

	public Notice() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getViewedAt() {
		return viewedAt;
	}

	public void setViewedAt(LocalDateTime viewedAt) {
		this.viewedAt = viewedAt;
	}

	public void update(Notice notice) {
		this.title = notice.getTitle();
		this.description = notice.getDescription();
		this.viewedAt = null;
	}

}
