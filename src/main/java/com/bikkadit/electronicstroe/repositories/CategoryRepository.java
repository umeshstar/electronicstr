package com.bikkadit.electronicstroe.repositories;

import com.bikkadit.electronicstroe.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {

}
