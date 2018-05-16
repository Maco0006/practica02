package com.ingenieria.model.user;

import java.util.List;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;


public class DaoUsuario implements DaoUsuario_Interface {
	
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	
	
	public void setDataSource(DataSource dataSource) {
	   this.dataSource = dataSource;
	   this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	//Para insertar usuarios en la base de datos.
	public void insertaUsuario(DtoUsuario usuario){

		String sql = "insert into usuarios values(?,?,?,?,?,?,?)";
		Object[] parametros ={usuario.getNombre(), usuario.getPassword(), usuario.getApellidos(), usuario.getEmail(), usuario.getDireccion(), usuario.getTelefono(), usuario.getAdmin()};
		this.jdbcTemplate.update(sql,parametros);
		
	}
	

	//Comprueba si existe un usuario con un determinado nombre en la base de datos.
	public Boolean buscaNombre(String nombreuser){

		String sql = "select * from usuarios where nombre = ?";
		Object[ ] parametros = {nombreuser}; 
		UsuarioMapper mapper = new UsuarioMapper();

		List<DtoUsuario> usuarios = this.jdbcTemplate.query(sql, parametros, mapper);

		if (usuarios.isEmpty()) return false;
		else return true;
		
	}
	

	//Comprueba si existe un usuario a través del email.
	public Boolean buscaEmail(String emailuser){
		
		String sql = "select * from usuarios where email = ?";
		Object[ ] parametros = {emailuser}; 
		UsuarioMapper mapper = new UsuarioMapper();

		List<DtoUsuario> usuarios = this.jdbcTemplate.query(sql, parametros, mapper);
		
		if (usuarios.isEmpty()) return false;
		else return true;
	}
	

	//Busca un usuario por su email y, si existe en la base de datos, devuelve sus datos.
	public DtoUsuario extraerUsuario(String email){

		String sql= "select * from usuarios where email = ? ";
		Object[] parametros = {email}; //Necesaria para query
		UsuarioMapper mapper = new UsuarioMapper();

		List<DtoUsuario> usu = this.jdbcTemplate.query(sql,parametros, mapper);
		
		if (usu.isEmpty()) return null;
		else return usu.get(0); //En caso de que exista el usuario, devolverá el primer objeto de la lista, que será el usuario buscado.

	}

	
	//Actualiza los datos del usuario.
	public void modificaDatos(DtoUsuario usuario, String email){
		
		String sql = "update usuarios set  email= ?, nombre= ?,  apellidos= ?, direccion= ?, telefono=?, password= ? where email= ?";
        this.jdbcTemplate.update(sql, usuario.getEmail(), usuario.getNombre(), usuario.getApellidos(), usuario.getDireccion(), usuario.getTelefono(), usuario.getPassword(), email);
        
	}

	
	//Obtiene los usuarios y administradores de la base de datos.
	public List<DtoUsuario> leeUsuarios(){
		
		String sql = "select * from usuarios";
		UsuarioMapper mapper = new UsuarioMapper();
		List<DtoUsuario> usuarios = this.jdbcTemplate.query(sql, mapper);
		
		return usuarios;
		
	}
	
}