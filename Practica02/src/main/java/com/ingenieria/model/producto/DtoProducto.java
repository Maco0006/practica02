package com.ingenieria.model.producto;



public class DtoProducto {

	private int idproducto;
	private String nombreprod;
	private double precio;
	private String descripcion;
	private String imagenproducto;
	private int cantidad;


	public DtoProducto (int idproducto, String nombreprod, double precio, String descripcion, String imagenproducto, int cantidad){
	
			this.idproducto = idproducto;
			this.nombreprod = nombreprod;
			this.precio = precio;
			this.descripcion = descripcion;
			this.imagenproducto = imagenproducto;
			this.cantidad = cantidad;
			
	}
	
	public DtoProducto (){
	
			this.idproducto = 0;
			this.nombreprod = "";
			this.precio = 0.0;
			this.descripcion = "";
			this.imagenproducto = "";
			this.cantidad = 0;
			
	}
	
	
	public int getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	
	
	public String getNombreprod() {
		return nombreprod;
	}
	public void setNombreprod(String nombreprod) {
		this.nombreprod = nombreprod;
	}
	
	
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	public String getImagenproducto() {
		return imagenproducto;
	}
	public void setImagenproducto(String imagenproducto) {
		this.imagenproducto = imagenproducto;
	}
	
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
