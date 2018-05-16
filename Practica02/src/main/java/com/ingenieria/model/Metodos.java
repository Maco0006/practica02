package com.ingenieria.model;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;


import com.ingenieria.model.producto.DtoProducto;

import com.ingenieria.model.user.DtoUsuario;



public class Metodos {


	public static void CrearSession(HttpServletRequest request, DtoUsuario userdatab, List<DtoProducto> listaCompra){ //Inicializamos la sesi�n.

		Date today = new Date();
		HttpSession sesion = request.getSession(true); 

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:SS"); //Obtenemos la fecha de creaci�n de la sesi�n.
    	String fecha = DATE_FORMAT.format(today);
    	System.out.println("Conexion: " + fecha);

		sesion.setAttribute("fecha", fecha); //A�adimos la fecha a la sesi�n.
		sesion.setAttribute ("usuario",userdatab); //A�adimos el usuario a la sesi�n.
		sesion.setAttribute("lista", listaCompra);


		Double total=0.0;		
		sesion.setAttribute("total", total);

		sesion.setMaxInactiveInterval(60*60); //Tiempo de expiraci�n: 1 hora.

	}

}
