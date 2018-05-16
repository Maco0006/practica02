package com.ingenieria.model.user;


public class DtoUsuario {
	private String nombre;
	private String password;
	private String apellidos;
	private String email;
	private String direccion;
	private String telefono;
	private boolean admin; 

	public DtoUsuario (String nombre, String password, String apellidos, String email, String direccion, String telefono, boolean admin) {
		
		this.nombre = nombre;
		this.password = password;
		this.apellidos = apellidos;
		this.email = email;
		this.direccion = direccion;
		this.telefono = telefono;
		this.admin = false;
		
	}
	
	
	public DtoUsuario (){
		this.nombre = "";
		this.password = "";
		this.apellidos = "";
		this.email = "";
		this.direccion = "";
		this.telefono = "";
		this.admin = false;
	}
	

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre; 
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public boolean getAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}

