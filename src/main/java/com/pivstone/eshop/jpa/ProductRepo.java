package com.pivstone.eshop.jpa;

import com.pivstone.eshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Repository
public interface ProductRepo extends PagingAndSortingRepository<Product, UUID> {
    Collection<Product> findByCategory_Id(UUID id);

    Optional<Product> findByName(String name);

    Page<Product> findByCategory_Id(UUID categoryID, Pageable pageable);

    Optional<Product> findById(UUID id);
}
