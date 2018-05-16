package com.ingenieria.model.producto;

import java.util.List;

public interface DaoProducto_Interface {

	public List<DtoProducto> leeProductos();
	public void actualizaPrecio(int cantidadprod,int idprod);
	
}
