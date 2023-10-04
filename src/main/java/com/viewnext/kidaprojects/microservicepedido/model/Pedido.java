package com.viewnext.kidaprojects.microservicepedido.model;


import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/**
 * La clase {@code Pedido} representa un pedido que puede ser almacenado en una base de datos.
 * Contiene información como el código del pedido, la cantidad de unidades, el total y la fecha del pedido.
 *
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 04 de Octubre de 2023
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int codigo;
	private int unidades;
	private double total;
	
	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime fecha;
	
	
	public Pedido(int codigo, int unidades, double total, LocalDateTime fecha) {
		super();
		
		this.codigo = codigo;
		this.unidades = unidades;
		this.total = total;
		this.fecha = fecha;
	}
	
	


	public Pedido() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public int getUnidades() {
		return unidades;
	}


	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public LocalDateTime getFecha() {
		return fecha;
	}


	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return "Pedido [id=" + id + ", codigo=" + codigo + ", unidades=" + unidades + ", total=" + total + ", fecha="
				+ fecha + "]";
	}
	
	
	
}
