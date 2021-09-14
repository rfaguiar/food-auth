package com.food.auth.domain.repository;

import com.food.auth.domain.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    @Query("""
            from Grupo u
            left join fetch u.permissoes
            where u.id = :idGrupo
            """)
    Optional<Grupo> findById(Long idGrupo);
}
