package com.ae.apis.service.impl;

import com.ae.apis.controller.dto.CategoryRequest;
import com.ae.apis.controller.dto.CategoryResponse;
import com.ae.apis.controller.dto.CategorySimpleResponse;
import com.ae.apis.controller.query.base.QueryPredicate;
import com.ae.apis.entity.Category;
import com.ae.apis.entity.QCategory;
import com.ae.apis.entity.enums.CategoryStatus;
import com.ae.apis.repository.CategoryRepository;
import com.ae.apis.service.CategoryService;
import com.ae.apis.service.MediaService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private MediaService mediaService;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private EntityManager em;

  @Override
  public Page<CategorySimpleResponse> getCategories(QueryPredicate queryPredicate) {
    return queryPredicate.fetch(buildCategorySimpleQuery(em));
  }

  @Override
  public List<CategorySimpleResponse> getCategories(Long parentId) {
    return buildCategorySimpleQuery(em).fetch();
  }

  @Override
  public CategoryResponse getCategory(Long id) {
    var categoryResponse = buildCategoryResQuery(em)
        .where(QCategory.category.id.eq(id))
        .fetchOne();
    if (categoryResponse == null) {
      throw new RuntimeException("Category not found");
    }
    categoryResponse.setMediaDetails(mediaService.getMediaDetails(categoryResponse.getMediaId()));
    return categoryResponse;
  }

  @Override
  @Transactional
  public void createCategory(CategoryRequest request) {
    var media = mediaService.createMedia(request.getMediaRequests());
    var category = Category.builder()
        .parent(findParentOrNull(request))
        .name(request.getName())
        .description(request.getDescription())
        .thumbnail(request.getThumbnail())
        .status(CategoryStatus.ACTIVE)
        .media(media)
        .build();
    categoryRepository.save(category);
  }

  private Category findParentOrNull(CategoryRequest request) {
    if (request.getParentId() == null) {
      return null;
    }
    return categoryRepository.findById(request.getParentId())
        .orElseThrow(() -> new RuntimeException());
  }

  @Override
  @Transactional
  public void updateCategory(Long id, CategoryRequest request) {
    var category = findCategory(id);
    category.setName(request.getName());
    category.setDescription(request.getDescription());
    category.setThumbnail(request.getThumbnail());
    mediaService.updateMedia(category.getMedia(), request.getMediaRequests());
  }

  private Category findCategory(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException());
  }
}
