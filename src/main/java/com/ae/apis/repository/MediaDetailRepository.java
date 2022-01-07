package com.ae.apis.repository;

import com.ae.apis.entity.MediaDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaDetailRepository extends JpaRepository<MediaDetail, Long> {
  List<MediaDetail> findByMediaId(Long mediaId);
}
