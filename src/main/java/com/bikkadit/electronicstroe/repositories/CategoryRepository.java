package com.bikkadit.electronicstroe.repositories;

import com.bikkadit.electronicstroe.entities.Category;
import com.bikkadit.electronicstroe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {

    List<Category> findByNameContaining(String keywords);

}
