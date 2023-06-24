package com.bikkadit.electronicstroe.repositories;

import com.bikkadit.electronicstroe.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , String> {

}
