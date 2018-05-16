package com.ingenieria.model.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ingenieria.model.user.DtoUsuario;

public class UsuarioMapper implements RowMapper<DtoUsuario>{

	public DtoUsuario mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		
		DtoUsuario usuario = new DtoUsuario();
		
		usuario.setNombre(rs.getString("nombre"));
		usuario.setEmail(rs.getString("email"));
		usuario.setApellidos(rs.getString("apellidos"));
		usuario.setPassword(rs.getString("password"));
		usuario.setDireccion(rs.getString("direccion"));
		usuario.setTelefono(rs.getString("telefono"));
		usuario.setAdmin(rs.getBoolean("admin"));

		return usuario;
		
	}
	
}
