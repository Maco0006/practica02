package com.ingenieria.model.user;

/**
 * Objeto de la clase Usuario.
 * Esta clase transporta todos los atributos de cada persona.
 * 
 * @author Pablo Castillo Segura y Andrés Ruiz Peñuela
 * 
 */
public class DtoUsuario  {
	private String nombre;
	private String clave;
	private String apellidos;
	private String email;
	private String direccion;
	private String telf;
	private boolean admin; //Por defecto false.

	/**
	 * Constructor de la clase DtoUsuario
	 * 
	 * @param nombre Nombre de usuario
	 * @param clave Contraseña de Usuario
	 * @param apellidos Apellidos del Usuario
	 * @param email Email del Usuario
	 * @param direccion Dirección del Usuario
	 * @param telf Teléfono del Usuario
	 */
	public DtoUsuario (String nombre,String clave,String apellidos,String email, String direccion, String telf){
		this.nombre=nombre;
		this.apellidos=apellidos;
		this.email=email;
		this.clave=clave;
		this.direccion=direccion;
		this.telf=telf;
		this.admin=false;
	}
	
	//Constuctor por defecto
	public DtoUsuario (){
		this.nombre="";
		this.clave="";
		this.apellidos="";
		this.email="";
		this.direccion="";
		this.telf="";
		this.admin=false;
	}
	
	//Metodos get
	public String getNombre() {
		return nombre;
	}
	public String getClave() {
		return clave;
	}
	public String getApellidos() {
		return apellidos;
	}
	public String getEmail() {
		return email;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getTelf() {
		return telf;
	}
	public boolean getAdmin() {
		return admin;
	}

	//Métodos set
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setTelf(String telf) {
		this.telf = telf;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}

