package com.food.auth.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public record Permissao(@Id
                         @GeneratedValue(strategy = GenerationType.IDENTITY)
                         Long id,
                        @Column(nullable = false)
                         String nome,
                        @Column(nullable = false)
                         String descricao) {

    public Permissao() {
        this(null, null, null);
    }

    @Override
    public String toString() {
        return "Permissao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permissao)) return false;
        Permissao permissao = (Permissao) o;
        return Objects.equals(id, permissao.id) &&
                Objects.equals(nome, permissao.nome) &&
                Objects.equals(descricao, permissao.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao);
    }
}
