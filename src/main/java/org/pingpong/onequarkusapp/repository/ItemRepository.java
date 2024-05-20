package org.pingpong.onequarkusapp.repository;

import org.pingpong.onequarkusapp.dominio.Item;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemRepository implements PanacheRepositoryBase<Item, String>{
    
}
