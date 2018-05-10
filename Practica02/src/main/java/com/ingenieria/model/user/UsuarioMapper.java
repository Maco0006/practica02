package com.ingenieria.model.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ingenieria.model.user.DtoUsuario;

public class UsuarioMapper implements RowMapper<DtoUsuario>{
	
	/**
	 * Método para mapear los atributos del Objeto DtoUsuario con los de la tabla de la base de datos
	 */
	public DtoUsuario mapRow(ResultSet rs, int rowNum) throws SQLException{
		DtoUsuario u = new DtoUsuario();
		
		u.setNombre(rs.getString("nombre"));
		u.setEmail(rs.getString("email"));
		u.setApellidos(rs.getString("apellidos"));
		u.setClave(rs.getString("clave"));
		u.setDireccion(rs.getString("direccion"));
		u.setTelf(rs.getString("telefono"));
		u.setAdmin(rs.getBoolean("admin"));

		return u;
	}
}
