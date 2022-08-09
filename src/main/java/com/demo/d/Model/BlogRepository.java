package com.demo.d.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends CrudRepository<Blog,Long>{
    List findAll();

    String findByTitle(String title);
}
