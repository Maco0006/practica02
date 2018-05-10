package com.ingenieria.model;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;


import com.ingenieria.model.producto.DtoProducto;

import com.ingenieria.model.user.DtoUsuario;



public class Metodos {


	/**
	 * Método para inicializar la sesión

	 * 
	 * @param request Petición del cliente

	 * @param usuarioDB Objeto de la clase Usuario a incluir en la sesión

	 * @param listaCompra Lista de la compra a incluir en la sesión
	 */


	public static void CrearSession(HttpServletRequest request, DtoUsuario usuarioDB,List<DtoProducto> listaCompra){

		Date today = new Date();
	//Creamos la sesión


		HttpSession session = request.getSession(true);
		//Obtenemos fecha de creación de la sesión


		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:SS");

    		String date = DATE_FORMAT.format(today);

    		System.out.println("Conexion: " + date);
    	
		
		//Añadimos la fecha en la sesion

		session.setAttribute("date", date);

		
		
		//Añadimos el usuario en la sesión

		session.setAttribute ("usuario",usuarioDB);


		//Establecemos la lista de productos a null

		session.setAttribute("lista", listaCompra);


		//Inicializamos el total de la sesión.

		Double total=0.0;
		
		session.setAttribute("total", total);


		//Establecemos la expiración de la sesión a 1 hora

		session.setMaxInactiveInterval(60*60);

	}

}
