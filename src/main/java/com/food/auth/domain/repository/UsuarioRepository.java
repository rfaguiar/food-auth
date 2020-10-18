package com.food.auth.domain.repository;

import com.food.auth.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("""
            from Usuario u
            inner join fetch u.grupos 
            where u.id = :usuarioId
            """)
    Optional<Usuario> findUsuarioWithGrupos(Long usuarioId);
}
