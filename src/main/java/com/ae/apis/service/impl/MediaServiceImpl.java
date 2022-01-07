package com.ae.apis.service.impl;

import static java.util.stream.Collectors.toList;

import com.ae.apis.controller.dto.MediaRequest;
import com.ae.apis.controller.dto.MediaResponse;
import com.ae.apis.entity.Media;
import com.ae.apis.entity.QMedia;
import com.ae.apis.repository.MediaDetailRepository;
import com.ae.apis.repository.MediaRepository;
import com.ae.apis.service.MediaService;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService {

  @Autowired
  private MediaRepository mediaRepository;

  @Autowired
  private MediaDetailRepository mediaDetailRepository;

  @Autowired
  private EntityManager em;

  @Override
  @Transactional
  public Media createMedia(List<MediaRequest> mediaRequests) {
    return saveMedia(mediaRequests);
  }

  private Media saveMedia(List<MediaRequest> mediaRequests) {
    var media = mediaRepository.save(buildMedia());
    var mediaDetails = buildMediaDetails(mediaRequests, media);
    mediaDetailRepository.saveAll(mediaDetails);
    return media;
  }

  @Override
  @Transactional
  public Media updateMedia(Media media, List<MediaRequest> mediaRequests) {
    if (media == null) {
      return createMedia(mediaRequests);
    }
    var mediaDetails = buildMediaDetails(mediaRequests, media);
    var existingMediaDetails = mediaDetailRepository.findByMediaId(media.getId());
    var additionalMediaDetails = mediaDetails.stream()
        .filter(n -> !existingMediaDetails.contains(n)).collect(toList());
    var deletedMediaDetails = existingMediaDetails.stream().filter(o -> !mediaDetails.contains(o))
        .collect(toList());
    mediaDetailRepository.saveAll(additionalMediaDetails);
    mediaDetailRepository.deleteAll(deletedMediaDetails);
    return media;
  }

  @Override
  public List<MediaResponse> getMediaDetails(Long mediaId) {
    if (mediaId == null) {
      return Collections.emptyList();
    }
    return buildMediaQuery(em).where(QMedia.media.id.eq(mediaId)).fetch();
  }
}
