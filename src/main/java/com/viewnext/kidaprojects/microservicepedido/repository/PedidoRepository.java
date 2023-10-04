package com.viewnext.kidaprojects.microservicepedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.kidaprojects.microservicepedido.model.Pedido;
/**
 * La interfaz {@code PedidoRepository} es un repositorio de Spring Data JPA que se utiliza para
 * interactuar con la entidad {@code Pedido} en la base de datos.
 *
 * <p>
 * Proporciona métodos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en los
 * objetos de tipo {@code Pedido}.
 * </p>
 *
 * <p>
 * El autor de esta interfaz es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 04 de Octubre de 2023
 */
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
