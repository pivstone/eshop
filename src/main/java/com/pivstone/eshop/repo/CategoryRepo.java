package com.pivstone.eshop.repo;

import com.pivstone.eshop.domain.Category;
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
}
