package com.ingenieria.model.producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;;

public class ProductoMapper implements RowMapper<DtoProducto>{
	
	public DtoProducto mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		DtoProducto producto = new DtoProducto();
		
		producto.setIdproducto(rs.getInt("idproducto"));
		producto.setImagenproducto(rs.getString("imagenproducto"));
		producto.setNombreprod(rs.getString("nombreproducto"));
		producto.setDescripcion(rs.getString("descripcion"));
		producto.setCantidad(rs.getInt("cantidad"));
		producto.setPrecio(rs.getDouble("precio"));
		
		return producto;
		
	}
	
}
