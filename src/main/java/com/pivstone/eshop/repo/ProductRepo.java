package com.pivstone.eshop.repo;

import com.pivstone.eshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
public interface ProductRepo extends JpaRepository<Product, UUID> {
    Collection<Product> findByCategoryId(UUID categoryID);
}
