package org.pingpong.onequarkusapp;

import java.util.List;

import org.pingpong.onequarkusapp.dominio.Item;
import org.pingpong.onequarkusapp.dominio.Orden;
import org.pingpong.onequarkusapp.dominio.Usuaria;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class ResourcesOlli {

    @Inject
    ServiceOlli service;

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/wellcome")
    public String wellcome() {
        return "Wellcome Ollivanders!";
    }

    @GET
    @Path("/usuaria/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    // curl -w "\n" http://localhost:8080/usuaria/Doobey -v
    // curl -w "\n" http://localhost:8080/usuaria/Severus -v
    public Response gUsuaria(@PathParam("nombre") String nombre) {
        Usuaria usuaria = service.cargaUsuaria(nombre);
        return usuaria.getNombre().isEmpty()? 
            Response.status(Response.Status.NOT_FOUND).build():
            Response.status(Response.Status.OK).entity(usuaria).build();
    }

    @POST
    @Path("/ordena")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    // curl -d '{"user": {"nombre": "Hermione"}, "item": {"nombre": "AgedBrie"}}' 
    // -H "Content-Type: application/json" -X POST http://localhost:8080/ordena -v
    public Response post(@Valid Orden orden) {
        Orden pedido = service.comanda(orden.getUser().getNombre(), orden.getItem().getNombre());
        return pedido != null?
            Response.status(Response.Status.CREATED).entity(pedido).build():
            Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/pedidos/{usuaria}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // curl -w "\n" http://localhost:8080/pedidos/Hermione -v
    public List<Orden> list(@PathParam("usuaria") String usuaria) {
        return service.cargaOrden(usuaria);
    }

    @GET
    @Path("/pedidos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // curl -w "\n" http://localhost:8080/pedidos -v
    public List<Orden> list() {
        return service.ordenes();
    }

    @GET
    @Path("/item/{nombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // curl -w "\n" http://localhost:8080/item/AgedBrie -v
    public Response gItem(@PathParam("nombre") String nombre) {
        Item item = service.cargaItem(nombre);
        return item.getNombre().isEmpty()? 
            Response.status(Response.Status.NOT_FOUND).build():
            Response.status(Response.Status.OK).entity(item).build();
    }

    @POST
    @Path("/alta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    // curl -d '{"nombre":"Severus", "destreza":"200"}'
    // -H "Content-Type: application/json" -X POST http://localhost:8080/alta -v
    public Response add(@Valid Usuaria usuaria) {
        Usuaria user = service.creaUsuaria(usuaria);
        return user != null?
            Response.status(Response.Status.CREATED).entity(user).build():
            Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/baja/{usuaria}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    // curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/baja/Severus   
    public List<Orden> delete(@PathParam("usuaria") String nombre) {
        service.eliminaUsuaria(nombre);
        return service.ordenes();
    }
}