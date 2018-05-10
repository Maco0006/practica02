package com.ingenieria.model.producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;;

/**
 * 
 * @author Pablo
 *
 */
public class ProductoMapper implements RowMapper<DtoProducto>{
	
	/**
	 * Método que mapea el objeto de la clase DtoProducto con los atributos de la tabla de la base de datos
	 */
	public DtoProducto mapRow(ResultSet rs, int rowNum) throws SQLException{
		DtoProducto p = new DtoProducto();
		
		p.setId(rs.getInt("ref"));
		p.setImagen(rs.getString("imagen"));
		p.setNombre(rs.getString("nombre"));
		p.setConcepto(rs.getString("concepto"));
		p.setUnidades(rs.getInt("unidades"));
		p.setImporte(rs.getDouble("importe"));
		
		return p;
	}
}
