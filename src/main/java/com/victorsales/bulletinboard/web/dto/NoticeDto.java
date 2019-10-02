package com.victorsales.bulletinboard.web.dto;

import java.time.LocalDateTime;

import com.victorsales.bulletinboard.domain.Notice;

public class NoticeDto {
	private long id;
	private String title;
	private LocalDateTime viewedAt;

	public NoticeDto() {
	}

	public NoticeDto(long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public NoticeDto(Notice notice) {
		this.id = notice.getId();
		this.title = notice.getTitle();
		this.viewedAt = notice.getViewedAt();
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

	public LocalDateTime getViewedAt() {
		return viewedAt;
	}

	public void setViewedAt(LocalDateTime viewedAt) {
		this.viewedAt = viewedAt;
	}
	
}
