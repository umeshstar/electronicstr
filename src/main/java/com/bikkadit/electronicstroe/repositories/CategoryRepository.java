package com.bikkadit.electronicstroe.repositories;

import com.bikkadit.electronicstroe.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {

    Page<Category> findBycategoryTitleContaining(String keywords, Pageable pageable);

}
