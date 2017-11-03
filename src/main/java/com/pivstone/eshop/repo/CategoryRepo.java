package com.pivstone.eshop.repo;

import com.pivstone.eshop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
public interface CategoryRepo extends JpaRepository<Category, Long> {
}
