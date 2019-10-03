package com.victorsales.bulletinboard.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.victorsales.bulletinboard.dao.INoticeDao;
import com.victorsales.bulletinboard.domain.Notice;
import com.victorsales.bulletinboard.web.dto.NoticeDto;
import com.victorsales.bulletinboard.web.exception.NoticeNotFoundException;
import com.victorsales.bulletinboard.web.exception.NoticeNotUpdatableException;

@Service
@Transactional
public class NoticeService implements INoticeService {

	@Autowired
	private INoticeDao noticeDao;

	@Override
	public Notice save(Notice notice) {
		if (Objects.isNull(notice.getCreatedAt())) {
			notice.setCreatedAt(LocalDateTime.now());
		}
		return this.noticeDao.save(notice);
	}

	@Override
	public Notice update(Notice notice) {
		if (Objects.nonNull(notice) && notice.getId() > 0) {
			Optional<Notice> optNotice = this.find(notice.getId());
			return optNotice.map(noticeUpdate -> {
				noticeUpdate.update(notice);
				return noticeUpdate;
			}).orElseThrow(() -> new NoticeNotUpdatableException(
					String.format("Problem updating notice with id: %d", notice.getId())));
		}
		throw new NoticeNotUpdatableException(String.format("Problem updating notice with id: %d", notice.getId()));
	}

	@Override
	public Page<NoticeDto> findAll(Pageable pageable) {
		if (Objects.isNull(pageable)) {
			pageable = PageRequest.of(0, 10);
		}
		return this.noticeDao.findAll(pageable).map(NoticeDto::new);
	}

	@Override
	public Notice visualize(long id) {
		Optional<Notice> optNotice = this.noticeDao.findById(id);
		if (optNotice.isPresent()) {
			Notice notice = optNotice.get();
			notice.setViewedAt(LocalDateTime.now());
			return this.noticeDao.save(notice);
		}

		return optNotice.orElseThrow(() -> new NoticeNotFoundException(
				String.format("Problem updating viewed date with notice.id: %d", id)));
	}

	@Override
	public Optional<Notice> find(long id) {
		Optional<Notice> optNotice = this.noticeDao.findById(id);
		return optNotice;
	}

	@Override
	public boolean existsById(long id) {
		return this.noticeDao.existsById(id);
	}

	@Override
	public boolean existsTitle(String title, long id) {
		boolean exists = this.noticeDao.existsByTitle(title);
		if (id > 0 && exists) {
			return !this.noticeDao.findByTitleAndId(title, id).isPresent();
		}
		return exists;
	}

	@Override
	public boolean delete(long id) {
		if (this.noticeDao.existsById(id)) {
			this.noticeDao.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

}
