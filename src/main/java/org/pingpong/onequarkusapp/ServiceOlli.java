package org.pingpong.onequarkusapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.pingpong.onequarkusapp.dominio.Item;
import org.pingpong.onequarkusapp.dominio.Orden;
import org.pingpong.onequarkusapp.dominio.Usuaria;
import org.pingpong.onequarkusapp.repository.ItemRepository;
import org.pingpong.onequarkusapp.repository.OrdenRepository;
import org.pingpong.onequarkusapp.repository.UsuariaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ServiceOlli {

    @Inject
    public UsuariaRepository usuariaRepo;

    @Inject
    public ItemRepository itemRepo;

    @Inject
    public OrdenRepository ordenRepo;

    public ServiceOlli() {}
    
    public Usuaria cargaUsuaria(String nombre) {
        Optional<Usuaria> usuaria = usuariaRepo.findByIdOptional(nombre);
        return usuaria.isPresent()? usuaria.get(): new Usuaria();
    }

    public Item cargaItem(String nombre) {
        Optional<Item> item = itemRepo.findByIdOptional(nombre);
        return item.isPresent()? item.get(): new Item();
    }

    public List<Orden> cargaOrden(String usuaria_nombre) {
        return ordenRepo.findByUserName(usuaria_nombre);
    }

    public Usuaria creaUsuaria(Usuaria usuaria) {
        usuariaRepo.persist(usuaria);
        return this.cargaUsuaria(usuaria.getNombre());
    }

    public void eliminaUsuaria(String nommbre_usuaria) {
        usuariaRepo.deleteById(nommbre_usuaria);
    }

    public List<Orden> ordenes() {
        return ordenRepo.listAll();
    }

    // contenido min eval: if-else
    @Transactional
    public Orden comanda(String usuaria_nombre, String item_nombre) {
        Orden orden = null;
        Optional<Usuaria> usuaria = usuariaRepo.findByIdOptional(usuaria_nombre);
        Optional<Item> item = itemRepo.findByIdOptional(item_nombre);
        if (usuaria.isPresent() && item.isPresent() 
            && usuaria.get().getDestreza() >= item.get().getQuality()) {
            orden = new Orden(usuaria.get(), item.get());
            ordenRepo.persist(orden);
        }
        return orden;
    }

    // contenido min eval: loop, if-else, colecciones
    @Transactional
    public List<Orden> comandaMultiple(String usuaria, List<String> productos) {

        Optional<Usuaria> user = usuariaRepo.findByIdOptional(usuaria);
        if (user.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<Orden> ordenes = new ArrayList<Orden>();

        Orden orden = null;
        for (String producto: productos) {
            orden = this.comanda(user.get().getNombre(), producto);
            if (orden != null) {
                ordenes.add(orden);
            }
        }
        return ordenes;      
    }
}
