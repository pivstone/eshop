package com.pivstone.eshop.jpa;

import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.UUID;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/6.
 */
@NoRepositoryBean
@Scope(WebApplicationContext.SCOPE_REQUEST)
public interface RestRepo<E>  extends PagingAndSortingRepository<E, UUID> {
    Optional<E> findByName(String name);
    Optional<E> findById(UUID id);

    boolean existsByName(String name);
    boolean existsById(UUID id);

}
