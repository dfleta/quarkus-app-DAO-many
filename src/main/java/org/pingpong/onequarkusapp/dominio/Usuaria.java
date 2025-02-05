package org.pingpong.onequarkusapp.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="t_users")
public class Usuaria {

    @Id
	@Column(name="user_nom", unique = true)
	private String nombre = "";
	
	@Column(name="user_prop")
	private int destreza = 0;
	
	public Usuaria() {}

	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDestreza() {
		return this.destreza;
	}
	public void setDestreza(int valor) {
		this.destreza = valor;
	} 
}
