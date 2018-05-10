package com.ingenieria.model.producto;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DaoProducto implements DaoProducto_Interface {	
	
	//Para trabajar con JDBC que nos proporciona spring
	private JdbcTemplate jdbcTemplate;
	//Hace referencia al datasource de la base de datos que sera sostenida en la tabla
	private DataSource dataSource;
	
	/**
	 * Recibe un dataSource, el cual, es suministrado por inyección, dado que este objeto esta precargado
	 * en el spring bean configuration.
	 * Este método asigna el valor a la variable privada, y genera una instancia de la template que nos 
	 * proporciona spring pasandole la conexión con la base de datos.
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
	   this.dataSource = dataSource;
	   this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/**
	 * Método para devolver la lista de productos
	 */
	public List<DtoProducto> read(){
		//Sentencia SQL
		String sql = "select * from productos";
		
		//Inicio mapeo
		ProductoMapper mapper = new ProductoMapper();
		
		//Consulta mediante jdbcTemplate
		List<DtoProducto> lista = this.jdbcTemplate.query(sql, mapper);
		
		return lista;
	}
	
	/**
	 * Metodo para actualizar el número de unidades de un producto
	 */
	public void actualizar (int unidades,int id){
		//Sentencial SQL
		String sql = "update productos set  unidades= ? where ref= ?";
		
		//Consulta mediante jdbcTemplate
        this.jdbcTemplate.update(sql,unidades,id);
	}




}