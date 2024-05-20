package org.pingpong.onequarkusapp.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.pingpong.onequarkusapp.dominio.Orden;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrdenRepository implements PanacheRepository<Orden> {

    public List<Orden> findByUserName(String usuaria_nombre) {
        List<Orden> ordenes = this.listAll()
										.stream()
										.filter(o -> o.getUser().getNombre().equalsIgnoreCase(usuaria_nombre))
										.collect(Collectors.toList());
		return ordenes.isEmpty()? List.of(): ordenes;
    }
}
