package com.kata.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kata.entity.TareasEntity;

public interface ITareasRepo extends JpaRepository<TareasEntity, Long> {

}
