package com.ae.apis.repository;

import com.ae.apis.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
