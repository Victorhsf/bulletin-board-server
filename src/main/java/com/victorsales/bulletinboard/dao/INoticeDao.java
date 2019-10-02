package com.victorsales.bulletinboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorsales.bulletinboard.domain.Notice;

public interface INoticeDao extends JpaRepository<Notice, Long> {

	boolean existsByTitle(String title);
	
}
