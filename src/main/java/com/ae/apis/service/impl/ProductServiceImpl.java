package com.ae.apis.service.impl;

import com.ae.apis.config.error.NotFoundException;
import com.ae.apis.controller.dto.ProductRequest;
import com.ae.apis.controller.dto.ProductResponse;
import com.ae.apis.controller.dto.ProductSimpleResponse;
import com.ae.apis.controller.query.base.QueryPredicate;
import com.ae.apis.entity.Category;
import com.ae.apis.entity.Product;
import com.ae.apis.entity.QProduct;
import com.ae.apis.entity.enums.ProductStatus;
import com.ae.apis.repository.CategoryRepository;
import com.ae.apis.repository.ProductRepository;
import com.ae.apis.service.MediaService;
import com.ae.apis.service.ProductService;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private MediaService mediaService;

  @Autowired
  private EntityManager em;

  @Override
  public Page<ProductSimpleResponse> getProducts(QueryPredicate queryPredicate) {
    return queryPredicate.fetch(buildProductSimpleQuery(em));
  }

  @Override
  public ProductResponse getProduct(Long id) {
    var productResponse = buildProductQuery(em)
        .where(QProduct.product.id.eq(id))
        .fetchOne();
    if (productResponse == null) {
      throw new NotFoundException(Product.class, id);
    }
    productResponse.setMediaDetails(mediaService.getMediaDetails(productResponse.getMediaId()));
    return productResponse;
  }

  @Override
  @Transactional
  public void createProduct(ProductRequest request) {
    var media = mediaService.createMedia(request.getMediaRequests());
    var product = Product.builder()
        .category(findCategory(request))
        .name(request.getName())
        .description(request.getDescription())
        .thumbnail(request.getThumbnail())
        .price(request.getPrice())
        .status(ProductStatus.ACTIVE)
        .media(media)
        .build();
    productRepository.save(product);
  }

  private Category findCategory(ProductRequest request) {
    return categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new NotFoundException(Category.class, request.getCategoryId()));
  }

  @Override
  @Transactional
  public void updateProduct(Long id, ProductRequest request) {
    var product = findProduct(id);
    product.setCategory(findCategory(request));
    product.setName(request.getName());
    product.setDescription(request.getDescription());
    product.setThumbnail(request.getThumbnail());
    product.setPrice(request.getPrice());
    product.setStatus(request.getStatus());
    mediaService.updateMedia(product.getMedia(), request.getMediaRequests());
  }

  private Product findProduct(Long id) {
    return productRepository.findById(id).orElseThrow(() -> new NotFoundException(Product.class, id));
  }

  @Override
  public Product findProductById(Long productId) {
    return findProduct(productId);
  }
}
