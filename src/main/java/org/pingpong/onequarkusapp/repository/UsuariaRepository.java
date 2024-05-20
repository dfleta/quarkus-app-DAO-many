package org.pingpong.onequarkusapp.repository;

import jakarta.enterprise.context.ApplicationScoped;

import org.pingpong.onequarkusapp.dominio.Usuaria;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class UsuariaRepository implements PanacheRepositoryBase<Usuaria, String> {
    
}
