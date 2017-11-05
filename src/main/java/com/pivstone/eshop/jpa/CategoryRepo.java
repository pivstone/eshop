package com.pivstone.eshop.jpa;

import com.pivstone.eshop.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Repository
public interface CategoryRepo extends PagingAndSortingRepository<Category, UUID> {
    Optional<Category> findByName(String name);
    Optional<Category> findById(UUID id);
    boolean existsByName(String name);
}
