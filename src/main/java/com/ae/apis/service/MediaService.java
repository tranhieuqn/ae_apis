package com.ae.apis.service;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

import com.ae.apis.controller.dto.MediaRequest;
import com.ae.apis.controller.dto.MediaResponse;
import com.ae.apis.entity.Media;
import com.ae.apis.entity.MediaDetail;
import com.ae.apis.entity.QMedia;
import com.ae.apis.entity.QMediaDetail;
import com.ae.apis.entity.enums.MediaDetailStatus;
import com.ae.apis.entity.enums.MediaStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import org.springframework.util.CollectionUtils;

public interface MediaService {

  Media createMedia(List<MediaRequest> mediaRequests);

  Media updateMedia(Media media, List<MediaRequest> mediaRequests);

  List<MediaResponse> getMediaDetails(Long mediaId);

  QMediaDetail mediaDetail = QMediaDetail.mediaDetail;
  QMedia media = QMedia.media;

  default JPAQuery<MediaResponse> buildMediaQuery(EntityManager em) {
    return new JPAQuery<MediaResponse>(em)
        .select(buildMediaBean())
        .from(mediaDetail)
        .innerJoin(media).on(mediaDetail.media.eq(media));
  }

  default QBean<MediaResponse> buildMediaBean() {
    return Projections.bean(
        MediaResponse.class,
        mediaDetail.id,
        mediaDetail.name,
        mediaDetail.description,
        mediaDetail.url
    );
  }

  default Media buildMedia() {
    return Media.builder()
        .status(MediaStatus.ACTIVE)
        .build();
  }

  default Set<MediaDetail> buildMediaDetails(List<MediaRequest> mediaRequests, Media media) {
    if (CollectionUtils.isEmpty(mediaRequests)) {
      return emptySet();
    }
    return mediaRequests.stream()
        .map(request -> MediaDetail.builder()
            .media(media)
            .name(request.getName())
            .description(request.getDescription())
            .url(request.getUrl())
            .status(MediaDetailStatus.ACTIVE)
            .build())
        .collect(toSet());
  }
}
