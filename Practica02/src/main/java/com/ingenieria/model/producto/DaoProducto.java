package com.ingenieria.model.producto;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DaoProducto implements DaoProducto_Interface {	
	
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;

	
	public void setDataSource(DataSource dataSource) {
		
	   this.dataSource = dataSource;
	   this.jdbcTemplate = new JdbcTemplate(dataSource);
	   
	}
	

	//Para mostrar la lista de productos
	public List<DtoProducto> leeProductos(){
		
		String sql = "select * from productos";
		ProductoMapper mapper = new ProductoMapper();
		List<DtoProducto> lista = this.jdbcTemplate.query(sql, mapper);
		
		return lista;
		
	}
	
	
	//Para actualizar las cantidades de un producto
	public void actualizaPrecio (int cantidadprod, int idprod){

		String sql = "update productos set  unidades= ? where ref= ?";
        this.jdbcTemplate.update(sql, cantidadprod, idprod);
        
	}

}