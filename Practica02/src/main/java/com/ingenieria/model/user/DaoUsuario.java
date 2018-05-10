package com.ingenieria.model.user;

import java.util.List;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Acceso a los datos del usuario.
 * 
 * @author Pablo Castillo Segura y Andrés Ruiz Peñuela
 */
public class DaoUsuario implements DaoUsuario_Interface {
	
	private JdbcTemplate jdbcTemplate;//Para trabajar con JDBC que nos proporciona spring
	private DataSource dataSource;//Hace referencia al datasource de la base de datos que sera sostenida en la tabla.
	
	/**
	 * Recibe un dataSource, el cual, es suministrado por inyección, dado que este objeto esta precargado
	 * en el sppring bean configuration.
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
	 * Método para insertar usuarios en la base de datos
	 */
	public void create(DtoUsuario usuarios){
		//Sentencia SQL
		String sql = "insert into usuarios values(?,?,?,?,?,?,?)";
		
		//Indicamos los parametros (tipo de objeto generico), para la consulta
		//Cuidado con el orden de parámetros, ya que la base de datos puede interpretar mal
		Object[] parametros ={usuarios.getNombre(),usuarios.getEmail(),
								usuarios.getApellidos(),usuarios.getClave(),
								usuarios.getDireccion(),usuarios.getTelf(),
								usuarios.getAdmin()};

		this.jdbcTemplate.update(sql,parametros);
	}
	
	/**
	 * Método para devolver si existe usuario en la base de datos, dado su nombre
	 */
	public Boolean existsName(String idName){
		//Sentencia SQL
		String sql = "select * from usuarios where nombre = ?";
		
		//Parámetros para la consulta
		Object[ ] parametros = {idName}; 
		
		//Instancia de la clase de mapeo del usuario
		UsuarioMapper mapper = new UsuarioMapper();
		
		//Devuelve los usuarios con el nombre
		List<DtoUsuario> usuarios = this.jdbcTemplate.query(sql, parametros, mapper);
		
		//Si devuelve un usuario vacío, no existe
		if (usuarios.isEmpty()) return false;
		else return true;
	}
	
	/**
	 * Método para devolver si existe usuario en la base de datos, dado su email
	 */
	public Boolean existsEmail(String idEmail){
		//Sentencia SQL
		String sql = "select * from usuarios where email = ?";
		
		//Parámetros para la consulta
		Object[ ] parametros = {idEmail}; 
		
		//Instancia de la clase de mapeo del usuario
		UsuarioMapper mapper = new UsuarioMapper();
		
		//Devuelve los usuarios con el nombre
		List<DtoUsuario> usuarios = this.jdbcTemplate.query(sql, parametros, mapper);
		
		//Si devuelve un usuario vacío, no existe
		if (usuarios.isEmpty()) return false;
		else return true;
	}
	
	/**
	 * Método para devolver el usuario de la base de datos, dado su email
	 */
	public DtoUsuario obtenerUsu (String email){
		//Sentencia SQL
		String sql= "select * from usuarios where email = ? ";
		
		//Parámetros para la consulta
		Object[] parametros = {email}; //Necesaria para query
		
		//Instancia de la clase de mapeo del usuario
		UsuarioMapper mapper = new UsuarioMapper();
		
		//Devuelve los usuarios con el nombre
		List<DtoUsuario> usu = this.jdbcTemplate.query(sql,parametros, mapper);
		
		
		//Si no hay usuarios
		if (usu.isEmpty()){
			//Devuelve null
			return null;
			
		}else{//Si hay
			//Devolvemos el primer objeto de la lista, que será el buscado
			return usu.get(0);
		}
	}
	/**
	 * Método para modificar cualquier dato del usuario.
	 */
	public void actualizar(DtoUsuario usr,String email){
		String sql = "update usuarios set  email= ?, nombre= ?,  apellidos= ?, direccion= ?, telefono=?, clave= ? where email= ?";
        this.jdbcTemplate.update(sql,usr.getEmail(),usr.getNombre(),usr.getApellidos(),usr.getDireccion(),usr.getTelf(),usr.getClave(),email);
	}
	/**
	 * Método para obtener los usuarios y administradores de la base de datos.
	 */
		public List<DtoUsuario> read(){
			String sql = "select * from usuarios";
			UsuarioMapper mapper = new UsuarioMapper();
			List<DtoUsuario> miembros = this.jdbcTemplate.query(sql, mapper);
			return miembros;
		}
}