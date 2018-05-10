package com.ingenieria.model.user;

import java.util.List;

/**
 * Interface de la clase Usuario
 * Esta clase indica que metodos debe implementar cada objeto Usuario
 * 
 * @author Pablo Castillo Segura y Andrés Ruiz Peñuela
 * 
 */

public interface DaoUsuario_Interface {
	
	/**
	 * Método para introducir un usuario en la base de datos, tras el formulario de registro
	 * 
	 * @param usuarios Usuario a introducir en la base de datos
	 * @see Homecontroller Clase Registro
	 */
	public void create(DtoUsuario usuarios);

	/**
	 * Método que devuelve si existe ya un usuario en la base de datos con el nombre dado
	 * 
	 * @param idName Nombre a comprobar si existe
	 * @return Booleano que indica si existe el usuario ya
	 */
	public Boolean existsName(String idName);
	
	/**
	 * Método que devuelve si existe ya un usuario en la base de datos con el email dado
	 * 
	 * @param idEmail Email a comprobar si existe
	 * @return Booleano que indica si existe el usuario ya
	 */
	public Boolean existsEmail(String idEmail);
	
	/**
	 * Método que devuelve el usuario de la base de datos dado su email
	 * 
	 * @param email Email con el que obtendremos el usuario
	 * @return Usuario de la base de datos con el email dado
	 */
	public DtoUsuario obtenerUsu (String email);
	
	/**
	 * Método que conecta con la base da datos para cambiar cualquier dato del usuario,
	 * neceisa un objeto de tipo DtoUsuario, donde el atributo email no se modifica para
	 * consultar la BBDD, por lo que se le pasa el nuevo email.
	 * 
	 * @param user de tipo DtoUsuario
	 * @param email de tipo String
	 */
	public void actualizar(DtoUsuario user,String email);
	/**
	 * Método para obtener los usuarios y administradores (miembros) almacenados en la BBDD
	 * 
	 * @return miembros de tipo List<DtoUsuario>
	 */
	public List<DtoUsuario> read();	
}

