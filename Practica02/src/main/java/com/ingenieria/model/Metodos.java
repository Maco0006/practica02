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
	 * M�todo para inicializar la sesi�n

	 * 
	 * @param request Petici�n del cliente

	 * @param usuarioDB Objeto de la clase Usuario a incluir en la sesi�n

	 * @param listaCompra Lista de la compra a incluir en la sesi�n
	 */


	public static void CrearSession(HttpServletRequest request, DtoUsuario usuarioDB,List<DtoProducto> listaCompra){

		Date today = new Date();
	//Creamos la sesi�n


		HttpSession session = request.getSession(true);
		//Obtenemos fecha de creaci�n de la sesi�n


		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:SS");

    		String date = DATE_FORMAT.format(today);

    		System.out.println("Conexion: " + date);
    	
		
		//A�adimos la fecha en la sesion

		session.setAttribute("date", date);

		
		
		//A�adimos el usuario en la sesi�n

		session.setAttribute ("usuario",usuarioDB);


		//Establecemos la lista de productos a null

		session.setAttribute("lista", listaCompra);


		//Inicializamos el total de la sesi�n.

		Double total=0.0;
		
		session.setAttribute("total", total);


		//Establecemos la expiraci�n de la sesi�n a 1 hora

		session.setMaxInactiveInterval(60*60);

	}

}
