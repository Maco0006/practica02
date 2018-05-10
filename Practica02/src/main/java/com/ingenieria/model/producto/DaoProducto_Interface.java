package com.ingenieria.model.producto;

import java.util.List;

public interface DaoProducto_Interface {
	
	/**
	 * Método de lectura de los productos de la base de datos
	 * 
	 * @return lista con los objetos de la clase producto
	 */
	public List<DtoProducto> read();
	
	/**
	 * Método para actualizar las unidades de un producto en la base de datos
	 * 
	 * @param unidades Nuevas unidades del producto
	 * @param id Id del producto a modificar
	 */
	public void actualizar (int unidades,int id);
}
