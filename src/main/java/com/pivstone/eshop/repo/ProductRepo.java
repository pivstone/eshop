package com.pivstone.eshop.repo;

import com.pivstone.eshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
public interface ProductRepo extends PagingAndSortingRepository<Product, UUID> {
    Collection<Product> findByCategoryId(UUID categoryID);

    Page<Product> findByCategoryId(UUID categoryID, Pageable pageable);
}
