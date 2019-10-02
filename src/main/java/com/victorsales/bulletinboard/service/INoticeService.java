package com.victorsales.bulletinboard.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.victorsales.bulletinboard.domain.Notice;
import com.victorsales.bulletinboard.web.dto.NoticeDto;

public interface INoticeService {

	Notice save(Notice notice);

	Notice update(Notice notice);

	Page<NoticeDto> findAll(Pageable pageable);

	Notice visualize(long id);

	Optional<Notice> find(long id);

	boolean existsById(long id);

	boolean existsTitle(String title);

	boolean delete(long id);

}
