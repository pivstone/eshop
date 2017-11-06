package com.pivstone.eshop.jpa;

import com.pivstone.eshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Repository
public interface ProductRepo extends RestRepo<Product> {
    Collection<Product> findByCategory_Id(UUID id);

    Page<Product> findByCategory_Id(UUID categoryID, Pageable pageable);

    boolean existsByIdAndCategory_Id(UUID productId, UUID categoryID);
}
