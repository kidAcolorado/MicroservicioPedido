package com.viewnext.kidaprojects.microservicepedido.service;

import java.util.List;

import com.viewnext.kidaprojects.microservicepedido.model.Pedido;

import java.util.List;

/**
 * La interfaz {@code PedidoService} define los métodos para gestionar pedidos y consultar información relacionada
 * con los pedidos.
 *
 * <p>
 * Los métodos en esta interfaz permiten dar de alta pedidos, consultar todos los pedidos registrados, y realizar
 * otras operaciones relacionadas con la gestión de pedidos.
 * </p>
 *
 * <p>
 * El autor de esta interfaz es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 04 de Octubre de 2023
 */
public interface PedidoService {

    /**
     * Crea y registra un nuevo pedido con el código y la cantidad especificados.
     *
     * @param codigo   El código del producto para el pedido.
     * @param cantidad La cantidad de unidades que se desean pedir.
     * @return El pedido creado y registrado.
     */
    public Pedido darDeAltaPedido(int codigo, int cantidad);

    /**
     * Consulta y devuelve una lista de todos los pedidos registrados.
     *
     * @return Una lista de pedidos.
     */
    public List<Pedido> showAll();
}

