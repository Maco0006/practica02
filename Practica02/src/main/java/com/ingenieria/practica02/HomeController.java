package com.ingenieria.practica02;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ingenieria.model.Metodos;
import com.ingenieria.model.producto.*;
import com.ingenieria.model.user.*;


@Controller
public class HomeController {
	
	
	@Autowired //Busca en servlet-content un bean que implemente DaoUsuario_Interface para el acceso a la base de datos.
	DaoUsuario_Interface daousuario; 

	@Autowired //Busca en servlet-content un bean que implemente DaoProducto_Interface para el acceso a la base de datos.
	DaoProducto_Interface daoproducto;
	
	
	//Método que permite el acceso de un usuario a la página de registro de la web. Puede ser un usuario normal o administrador.
	@RequestMapping(value ={"/", "/index"}, method = {RequestMethod.GET,RequestMethod.POST} )
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		
		Boolean existencookies = true;
		
    	Date today = new Date(); //Fecha del sistema.
		
		List<DtoProducto> listaCompra = null; //Lista donde se almacenan los productos de la compra.
		
		//Variable para guardar una vista jsp 
		//String vista = null;
		
		HttpSession sesion; //Para asociar una sesión a un usuario.
		
		DtoUsuario userdatab = null; //Aquí se encuentran los datos del usuario que realiza la petición.
		
		Boolean esuser=false;
		Boolean esadmin=false;
		

		sesion = request.getSession(false); //Obtenemos la sesión de la petición. En caso de que no exista, dará error.
		
		DtoUsuario usuario;

		String error = ""; //Aviso de errores.		
		
		if(sesion!=null) usuario = (DtoUsuario) sesion.getAttribute("usuario"); //Extraemos el objeto usuario de la sesión
		else usuario = null;

		
		if(usuario!=null) { //Comprobamos si existe un usuario en la sesión.
			
			System.out.println("Existe sesión");
			System.out.println("Nombre: " + usuario.getNombre()); //Mostramos nombre del usuario.
			Boolean existeusuario = daousuario.buscaNombre(usuario.getNombre()); //Comprobamos si existe en la base de datos el usuario con email de la cookie.
			
			if(existeusuario) {
				
				if(usuario.getAdmin()) {
					
					esadmin = true;					
					model.addAttribute("listaUsuario", daousuario.leeUsuarios()); //Obtenemos la lista de usuario y la pasamos a la vista.
					return "admin";
					
				} else {
					
					esuser=true;
					return "bienvenido";
					
				}
				
			} else {

				System.out.println("No existe usuario de sesión en base de datos");
				return "index";
				
				
			}
			
			
		} else { //Si no existe sesión
			
			System.out.println("NO Existe sesión");

			Cookie[] cookies = request.getCookies(); //Extraemos las cookies de la petición.
			
			if(cookies!=null) { //Si existen cookies.
				
				for(Cookie cookie : cookies) { //Recorremos las cookies. Este tipo de bucle for recorre todos los valores dentro de cookies hasta encontrar
												//la cookie que se llama email.
					if("email".equals(cookie.getName())) {
						
						System.out.println("Existen cookies");
						
						cookie.setMaxAge(60*60*24); //Establecemos el tiempo de la cookie (1 día).
						String valorCookie = cookie.getValue(); //Obtenemos el valor de email.
						Boolean existeusuario = daousuario.buscaEmail(valorCookie); //Comprobamos si existe el usuario en la base de datos a través de su email.
						
						if(existeusuario) {
							
							System.out.println("Existe usuario de cookies");
							
							userdatab = daousuario.extraerUsuario(valorCookie); //Extraemos el usuario de la clase usuario.
							model.addAttribute("usuario", userdatab); //Y lo enviamos a la vista.
							DtoUsuario userdto = daousuario.extraerUsuario(valorCookie);
							
							if(userdto.getAdmin()) {
								
								esadmin=true;
								
								System.out.println("entra1");
								
							} else if(!userdto.getAdmin()) {
								
								esuser = true;
								
								try {

									sesion = request.getSession(false); //Obtenemos la sesión de la petición. En caso de que no exista, dará error.
									DtoUsuario dtouser = (DtoUsuario) sesion.getAttribute("usuario"); //Extraemos el usuario de la sesión.
									
									System.out.println("Hay sesion");
									System.out.println("Nombre: " + dtouser.getNombre()); //Mostramos el nombre del usuario.
									
									return "bienvenido";									
									
								} catch(NullPointerException e) { //Si no existe sesión.
									
									System.out.println("No hay sesión");
									
									SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z"); //Obtenemos la fecha del inicio de la sesión.
						        	String fecha = DATE_FORMAT.format(today); //Formateo de la fecha.
						        	System.out.println("Conexion: " + fecha);

									sesion = request.getSession(true); //Creamos sesión.
									sesion.setAttribute ("usuario", userdatab); //Añadimos el usuario a la sesión.
									sesion.setAttribute("fecha", fecha); //Añadimos la fecha a la sesión.
									sesion.setAttribute("lista", listaCompra); //Establecemos la lista de los productos.
									Double total = 0.0; //Total de la sesión.
									sesion.setAttribute("total", total);
									sesion.setMaxInactiveInterval(60*60); //La sesión expirará al cabo de una hora.					

									model.addAttribute("lista", listaCompra); //Enviamos la lista a la vista.
									
									return "bienvenido";

								}
								
							}
							
							response.addCookie(cookie); //Añadimos la cookie a la respuesta.
							model.addAttribute("usuario", userdatab); //Enviamos el objeto usuario a la vista.
							
						} else System.out.println("No existe usuario de la cookie");
													
					}
				}
				
				if(esadmin == false && esuser == false) { //Si no existen cookies.

					System.out.println("NO Existen cookies");
					existencookies=false;
					
				}
				
			}
			
			if(cookies == null || existencookies == false) {

				if(request.getParameter("email") == null || request.getParameter("pass") == null) return "index"; //Si no hay datos en la petición.
				else { //Si hay datos en la petición
					
					String idusuario = request.getParameter("email"); //Leemos parámetros del formulario index.jsp
					String idpass = request.getParameter("pass");
					
					System.out.println("name= " + idusuario + "pass= " + idpass);
					
					Boolean existeusuario = daousuario.buscaEmail(idusuario); //Comprobamos que el usuario existe en la base de datos.

					if (!existeusuario) { //Si no existe el usuario.
						
						System.out.println("Error en el usuario o no existe");

						error = "Error en el usuario o no existe";
						
						return "registro";
						
					} else { //Si el usuario existe.
						
						System.out.println("Usuario existe");
						
						userdatab = daousuario.extraerUsuario(idusuario); //Extraemos los datos del usuario.

						if(!userdatab.getPassword().equals(idpass)) { //Si no coincide la contraseña de la base de datos.
							
							System.out.println("Error en la pass.");
							
							error = "Contraseña incorrecta"; //Pasamos el error a la vista.
							return "index";
							
						} else { //Si la contraseña coincide.

							System.out.println("Creamos cookie y sesion"); //Creamos la cookie.
							
							Cookie cookie = new Cookie("email", userdatab.getEmail());

						    cookie.setMaxAge(60*60*24); //Tiempo que dura la cookie (1 día).

							if(userdatab.getAdmin()) { //Si el usuario es administrador.
								
								esadmin = true;
								
							} else { //Si es usuario
								
								esuser = true;
								model.addAttribute("usuario", userdatab);
								
							}

							response.addCookie(cookie); //Agregamos a la cookie de respuesta.
							
						}
					}
				}
			}
		}
			
		if(esadmin){

		    sesion = request.getSession(true); //Se crea la sesión.

			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z"); //Obtenemos la fecha de creación de la sesión.
        	String date = DATE_FORMAT.format(today); //Formateo de la fecha.
        	
			sesion.setAttribute ("usuario", userdatab); //Añadimos el usuario a la sesión.
			sesion.setAttribute("date", date); //Añadimos la fecha en la sesión.
			sesion.setMaxInactiveInterval(30*60); //Expiración de la sesión: 60 minutos.
			
			model.addAttribute("listaUsuario", daousuario.leeUsuarios()); //Obtenemos la lista de usuario y la pasamos a la vista.
			
			return "admin";
			
		} else if(esuser) {
			
			try {
				
				System.out.println("try");

				sesion = request.getSession(true); //Obtenemos la sesión de la petición. Si no existe, da error.
				
				DtoUsuario userdto = (DtoUsuario) sesion.getAttribute("usuario"); //Extraemos el usuario de la sesión.

				System.out.println("Nombre: "+ userdto.getNombre());

				listaCompra = (List<DtoProducto>) sesion.getAttribute("lista"); //Accedemos a la lista de la compra que se ha almacenado en la sesión.
				
				return "bienvenido";
					
			} catch(NullPointerException e) {
				
				System.out.println("catch");
				
				Metodos.CrearSession(request, userdatab, listaCompra);
				
				model.addAttribute("lista", listaCompra); //Mandamos la laista de productos a la vista.
				
				return "bienvenido";
				
			}
			
		}
		
		model.addAttribute("error", error);
		return null;
		
	}


	//Método que permite el registro de un usuario.
	@RequestMapping(value = "/registro", method = {RequestMethod.GET, RequestMethod.POST})
	public String registro(HttpServletRequest request, Model model) {
		
		String error = "";

		List<DtoProducto> lista = new ArrayList<DtoProducto> (); //Lista de los productos.
		
		if(request.getParameter("name") == null || request.getParameter("pass") == null || request.getParameter("apellidos") == null || request.getParameter("email") == null ||
				request.getParameter("dir") == null || request.getParameter("telf") == null) { //Si alguno de los campos es null.
			
			return "registro";
			
		} else { //Si no hay fallo al introducir un nuevo usuario.
			
			String nombre = request.getParameter("name"); //Extraemos los datos del formulario.
			String email = request.getParameter("email");
			
			if(daousuario.buscaNombre(nombre) || daousuario.buscaEmail(email)) {
								
				return "registro"; //Cargamos la página de registro de nuevo.

				error = "Nombre o email en uso.";
				System.out.println("Nombre o email duplicado");
				
			} else {
				
				return "bienvenido";
				
				String apellidos = request.getParameter("apellidos"); //Extraemos del formulario el resto de datos.
				String pass = request.getParameter("pass");
				String direccion = request.getParameter("dir");
				String telf = request.getParameter("telf");				
				boolean admin;
				
				DtoUsuario userdto = new DtoUsuario (nombre, pass, apellidos, email, direccion, telf, admin); //Creamos el objeto de la clase usuario.
							
				//Añadimos al usario en la base de datos.
				daousuario.insertaUsuario(userdto);
				
				Metodos.CrearSession(request, userdto, lista);

				model.addAttribute("usuario", userdto); //Enviamos el usuario a la vista.
				model.addAttribute("lista", lista); //Enviamos la lista a la vista.

			}
			
		}
		
		model.addAttribute("error", error);
		
		return null;
		
	}
	
	
	//Método que permite al usuario ver los productos de la tienda.
	@RequestMapping(value = "/productos", method = {RequestMethod.GET,RequestMethod.POST})
	public String productos(HttpServletRequest request, Model model) {
	
		try {
			
			System.out.println("Entro en productos, Si hay sesion");
			
			HttpSession sesion = request.getSession(false); //Comprobamos si existe la sesión. Si no existe, da error.
			DtoUsuario usuario = (DtoUsuario) sesion.getAttribute("usuario"); //Extraemos el usuario de la sesión.

			System.out.println(usuario.getNombre());
			
			List<DtoProducto> listaProductos = daoproducto.leeProductos(); //Accedemos a la base de datos de los productos.

			model.addAttribute("lista", listaProductos); //Enviamos la lista a la vista.

			return "compra";

		} catch(NullPointerException e) { //Si no se ha creado la sesión correctamente
			
			System.out.println("Sesión no creada, volver a registrar. ");

			return "index";

		}
		
	}

	
	//Método que permite al usuario ver los productos que se encuentran en el carrito de la compra.
	@RequestMapping(value = "/carrito", method = {RequestMethod.GET, RequestMethod.POST})
	public String carrito(HttpServletRequest request, Model model) {
						
		String error = "";
		
		int contador = 0;
		
		String [] opcion = null, cantidad = null;

		try{

			HttpSession sesion = request.getSession(false); //Comprobamos si existe la sesión de la petición. Si no existe, da error.

			DtoUsuario usuario = (DtoUsuario) sesion.getAttribute("usuario"); //Extraemos el usuario de la sesión.
			Double total = (Double) sesion.getAttribute("total");

			List<DtoProducto> listaCompra = (List<DtoProducto>) sesion.getAttribute("lista"); //Obtenemos la lista de objetos de la sesión.

			List<DtoProducto> listaProductos = (ArrayList<DtoProducto>) daoproducto.leeProductos(); //Accedemos a la base de datos de los productos.
			
			if(request.getParameter("opcion")!=null) {

				opcion = request.getParameterValues("opcion"); //Obtenemos el id del producto seleccionado. El id coincide con el valor de la opción.
				cantidad= request.getParameterValues("unidades"); //Obtenemos las cantidades de los productos seleccionados.

				for(int i=0; i<opcion.length; i++) { //Recorremos las posibles opciones.

					for(int j=0; j<listaProductos.size(); j++) { //Recorremos los productos.

						if(Integer.parseInt(opcion[i]) == listaProductos.get(j).getIdproducto()) { //Si la id de la opción coincide con la id del producto.

							if(cantidad[j].equals("")) { //Si no se ha introducido ninguna cantidad.
								
								System.out.println("No ha indicado ninguna cantidad.");
								error = "Introduce una cantidad.";
								
							} else {

								if(Integer.parseInt(cantidad[j]) == 0) { //Si no hay ninguna cantidad (es 0).
									
									System.out.println("Ningun producto.");
									error = "Indica cantidad del producto selecionado";
									
								} else {

									if(Integer.parseInt(cantidad[j])<0){ //Si la cantidad es menor que cero (negativo).

										System.out.println("Cantidad no válida.");
										error = "Cantidad no válida.";
										
									} else { //Si la cantidad introducida es válida.
										
										int cantidades = Integer.parseInt(cantidad[j]);
										
										if(cantidades > listaProductos.get(j).getCantidad()) { //Si se piden más productos de los que hay.

											System.out.println("Productos insuficientes.");
											error = "Productos insuficientes.";
											
										} else {

											if(listaCompra == null) { //Si todavía no se ha realizado ninguna compra.
												
												listaCompra = new ArrayList();
												
											}
											
											if(listaCompra.isEmpty()) { //Si la lista de la compra está vacía.
												
												cantidades = Integer.parseInt(cantidad[j]); //Extraemos la cantidad solicitada.
												total = total + Integer.parseInt(cantidad[j]) * listaProductos.get(j).getPrecio(); //Calculamos el importe.
												listaProductos.get(j).setCantidad(cantidades); //Indicamos la cantidad de producto solicitada.
												listaCompra.add(listaProductos.get(j));
												
												
											} else { //Si ya se ha hecho una compra antes.
												
												System.out.println("Ya hay compra");
												
												for(int o=0; o<listaCompra.size(); o++) { //Recorremos la lista de la compra.

													if(listaCompra.get(o).getIdproducto() == listaProductos.get(j).getIdproducto()) { //Si el id del producto coincide con el producto elegido.

														cantidades = Integer.parseInt(cantidad[j]); //Obtenemos la cantidad existente.
														
														if(cantidades + listaCompra.get(o).getCantidad() > listaProductos.get(j).getCantidad()) { //Si no hay productos suficientes.

															System.out.println("Productos insuficientes."); 
															error = "Productos insuficientes.";
															
														} else {
															
															total = total + Integer.parseInt(cantidad[j]) * listaProductos.get(j).getPrecio(); //Calculamos el importe.
															listaProductos.get(j).setCantidad(Integer.parseInt(cantidad[j]));
															listaCompra.get(o).setCantidad(cantidades + listaCompra.get(o).getCantidad());
															
															contador=0; //Ya se ha introducido el producto.

															o = listaCompra.size();
															
														}
														
													} else {

														contador = 2; //El producto que se ha elegido no está en la lista de la compra.
														
													}
													
												}
												
											}

											if(contador == 2) { //Añadimos el nuevo producto a la lista de compra.

												cantidades = Integer.parseInt(cantidad[j]); //Extraemos la cantidad solicidtada.
												
												if(cantidades > listaProductos.get(j).getCantidad()) { //Si no hay productos suficientes.

													System.out.println("Productos insuficientes.");
													error = "Productos insuficientes.";
													
												} else {
													
													total = total + Integer.parseInt(cantidad[j]) * listaProductos.get(j).getPrecio(); //Calculamos el importe.
													listaProductos.get(j).setCantidad(cantidades);//Indicamos la cantidad solicidata
													listaCompra.add(listaProductos.get(j));
													
													contador = 0;
													
												}
												
											}
											
											contador++;
											
										}
										
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
				if(contador != 0) { //Si hay algún objeto en el carrito.
					

					sesion.setAttribute ("lista",listaCompra); //Se actualiza la sesión con la compra nueva.
					sesion.setAttribute("total", total);
					
					return "carrito";

					
				} else { //Si no hay ningún objeto en el carrito.

					model.addAttribute("lista", daoproducto.leeProductos()); //Pasamos la lista de productos a la vista.
					model.addAttribute("error", error);
					
					return "compra";
					
				}
				
			} else { //Si no se ha elegido ningún producto.

				error = "No has seleccionado ningun producto";
				
				model.addAttribute("lista", daoproducto.leeProductos()); //Pasamos la lista de productos a la vista.
				model.addAttribute("error",error);
				
				return "compra";

			}
			
		} catch(NullPointerException e) { //Si no se ha creado la sesión correctamente.
			
			System.out.println("Sesion no creada, volver a registrar.");
			
			return "index";
		
		}
		
	}

	
	//Método que permite al usuario eliminar un producto seleccionado durante la sesión.
	@RequestMapping(value = "/eliminar", method = {RequestMethod.GET})
	public String eliminar(@RequestParam(value = "name", required=true) int delete, HttpServletRequest request, Model model) {
		
		try {

			HttpSession sesion = request.getSession(false);
			List<DtoProducto> listaCompra = (List<DtoProducto>) sesion.getAttribute("lista");
			double total = (Double) sesion.getAttribute("total"); //Obtenemos el total de la sesión.
			 
			int i = 0;

			while(i<listaCompra.size()) { //Recorremos la lista de la compra.

				 if(listaCompra.get(i).getIdproducto() == delete) { //Si el id del producto que se desea borrar es igual al de la lista.
					 
					 total = total - listaCompra.get(i).getPrecio()*listaCompra.get(i).getCantidad(); //Se descuenta del total.
					 listaCompra.remove(i); //Eliminamos el producto de la lista.

					 i = listaCompra.size(); //Salimos de la lista.
				 }
				 
				 i++;
				 
			}
			 
			if(total<0) total = 0.0; //Por problemas de decimales.

			sesion.setAttribute ("lista",listaCompra); //Actualizamos la sesión con la lista nueva y el precio nuevo.
			sesion.setAttribute("total", total);

			return "carrito";
			
		} catch(NullPointerException e) { //Si no se ha creado la sesión correctamente.

			System.out.println("Sesion no creada, volver a registrar.");
			
			return "index";
			
		}
		
	}
	

	//Método que lleva al cliente a la página de paypal y permite actualizar la base de datos con los productos comprados.
	@RequestMapping(value = "/fin", method = RequestMethod.GET)
	public String finCompra(HttpServletRequest request, Model model) {
		
		Double total;
		
		List<DtoProducto> listaProductos = daoproducto.leeProductos();
		
		try {

			HttpSession session = request.getSession(false);
			List<DtoProducto> listaCompra = (List<DtoProducto>) session.getAttribute("lista"); //Extraemos la lista de la sesión.
			total = (Double) session.getAttribute("total"); //Extraemos el total de la sesión.

			for(int i=0; i<listaCompra.size(); i++) { //Recorremos la lista de la compra.
				
				int j=0;

				while(j<listaProductos.size()){ //Recorremos la lista de productos.

					if(listaCompra.get(i).getIdproducto()==listaProductos.get(j).getIdproducto()){ //Si la id del carrito y la id de la lista de productos coincide.

						daoproducto.actualizaPrecio(listaProductos.get(i).getCantidad() - listaCompra.get(i).getCantidad(), listaProductos.get(j).getIdproducto()); //Restamos las unidades de la base de datos.
						j=listaProductos.size();
					}
					
					j++;
					
				}
				
			}
			 
			total=0.0; //Por problemas de decimales.
			
			listaCompra = new ArrayList();
			
			session.setAttribute ("lista",listaCompra); //Actualizamos la sesión con la lista y el valor del total vacíos.
			session.setAttribute("total", total);
			
			return "carrito";
			
		} catch(NullPointerException e) { //Si no se ha creado la sesión correctamente.

			System.out.println("Sesion no creada, volver a registrar.");
			
			return "index";
			
		}

	}
	
	
	//Método que permite eliminar la sesión y las cookies para utilizar una cuenta diferente.
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		try {

			HttpSession session = request.getSession(false);

			session.invalidate(); //Invalidamos la sesión.

			Cookie[] cookies = request.getCookies(); //Extraemos las cookies de la petición.

			int i = 0;
			
			while(i<cookies.length) { //Recorremos las cookies.

				if(cookies[i].getName().equals("email")){ //Si coincide con el nombre del email, borramos la cookie.

					cookies[i].setValue(" ");					
					response.addCookie(cookies[i]); //Pasamos la cookie a la respuesta.
					
					i = cookies.length; //Salimos del bucle.
				}
				
				i++;
			}

			return "index";
			
			
		} catch(NullPointerException e) { //Si no se ha creado la sesión correctamente.
			
			return "index";
			
		}
		
	}
	
	
	//Método utilizado para, en la vista de registro, comprobar si ya existe un usuario a través de un nombre.
	@RequestMapping(value = "/nombreDisponible", method = RequestMethod.GET)
	public @ResponseBody String nombreDisponible(@RequestParam(value = "name", required = true) String nombre) {
		
		String mensaje = "";

		boolean existeuser = daousuario.buscaNombre(nombre); //Indica si el nombre de usuario existe.
		
		//Si existe, cambia el mensaje de la vista
		if(existeuser) mensaje = "El usuario ya existe";
		
		return mensaje;
		
	}
	

	//Método utilizado para, en la vista de registro, comprobar si ya existe un usuario a través de un email.
	@RequestMapping(value = "/emailDisponible", method = RequestMethod.GET)
	public @ResponseBody String emailDisponible(@RequestParam(value = "email", required = true) String email) {
		
		String mensaje = "";
		
		boolean existeemail = daousuario.buscaEmail(email); //Indica si existe el usuario por su email.

		if(existeemail)	mensaje = "Email ya existe"; //Si existe, cambia el mensaje de la vista.
		
		return mensaje;
		
	}
	
	
	//Método que lleva al perfil del usuario.
	@RequestMapping(value = "/perfil", method = RequestMethod.GET)
	public String perfil(HttpServletRequest request, Model model) {

		try {

			HttpSession sesion = request.getSession(false);

			DtoUsuario usuario = null; //Extraemos el usuario de la sesión.
			usuario = (DtoUsuario) sesion.getAttribute("usuario");
			
			if(usuario != null) model.addAttribute("usuario", usuario);	//Produce una excepción si no hay session.
			
			return "bienvenido";			
			
		} catch(NullPointerException e) { //Si no se ha creado la sesión correctamente.

			System.out.println("Sesion no creada o caducada, volver a registrar.");
			
			return "index";
			
		}

	}
	
	
	//Método que permite al usuario, dentro de su perfil, modificar cualquiera de sus datos.
	@RequestMapping(value = "/cambiar", method = RequestMethod.POST)
	public String cambiar(HttpServletRequest request, HttpServletResponse response, Model model) {

		String emaildeantes;
		String error = "";
		
		try {
			
			HttpSession sesion = request.getSession(false);
			
			DtoUsuario usuario = (DtoUsuario) sesion.getAttribute("usuario"); //Extraemos el usuario de la sesión.
			
			System.out.println("User session: " + usuario.getEmail());
			
			String name = request.getParameter("name"); //Obtenemos los datos solicitados que se desean cambiar..
			String email = request.getParameter("email");
			String telefono = request.getParameter("telf");
			String apellidos = request.getParameter("apellidos");
			String direccion = request.getParameter("dir");
			String pass = request.getParameter("pass");
			
			emaildeantes = usuario.getEmail(); //Extraemos el email del usuario.
			
			//Actualizamos campos.
			if(!name.equals("")){ //Si el campo nombre no está vacío.
				
				if(!daousuario.buscaNombre(name)) usuario.setNombre(name);
				else error = "El nombre ya está en uso";
				
			}
			
			if(!email.equals("")) { //Si el campo email no está vacío.
			
				System.out.println(email);
				
				if(!daousuario.buscaEmail(email)) {
					
					usuario.setEmail(email);

					Cookie[] cookies = request.getCookies(); //Extraemos las cookies de la petición.

					int i=0;

					while(i < cookies.length) { //Recorremos las cookies.

						if(cookies[i].getName().equals("email")) { //Si coincide con el email.
							
							cookies[i].setValue(email); 
							response.addCookie(cookies[i]); //Agregamos la cookie a la respuesta.

							i=cookies.length; //Salimos del bucle.
							
						}
						
						i++;
						
					}
					
				} else {
					
					error = "El email ya está en uso";
				}

				
			}
			
			if(!telefono.equals("")) usuario.setTelefono(telefono);
			if(!apellidos.equals("")) usuario.setApellidos(apellidos);
			if(!pass.equals("")) usuario.setPassword(pass);
			if(!direccion.equals("")) usuario.setDireccion(direccion);
			
			daousuario.modificaDatos(usuario, emaildeantes); //Actualizamos los datos.
			sesion.setAttribute ("usuario", usuario); //Actualizamos el usuario.

			model.addAttribute("usuario", usuario); //Devolvemos el usuario a la lista.
			model.addAttribute("error", error);

			return "bienvenido";
			
		} catch(NullPointerException e) { //Si no se ha creado la sesión correctamente.

			System.out.println("Sesion no creada, volver a registrar.");
			
			return "index";
		}

	}

	
	//Método que muestra la lista de productos comprados.
	@RequestMapping(value = "/mostrarCarrito", method = RequestMethod.GET)
	public String mostrarCarrito(HttpServletRequest request, Model model) {

		String error = "";
		
		List<DtoProducto> listaCompra =null;
		
		try {

			HttpSession sesion = request.getSession(false);
			
			//Obtenemos la lista de compra
			listaCompra = (List<DtoProducto>) sesion.getAttribute("lista"); //Obtenemos la lista de la compra.
			
			
			if(listaCompra == null) {
				
				System.out.println("Lista de Compra no inicializada");
				
				error = "Lista de la compra no inicializada";
				
				model.addAttribute("error", error);
				model.addAttribute("lista",daoproducto.leeProductos()); //Extraemos los productos y los pasamos a la vista.
				
				return "compra";
				
			} else {
				
				if(listaCompra.isEmpty()) {
					
					System.out.println("Lista de Compra vacía");
				
					//Avisamos de error.
					error = "Lista de la compra vacía";
					model.addAttribute("error", error);

					model.addAttribute("lista", daoproducto.leeProductos()); //Extraemos los productos y los pasamos a la vista.
					
					return "compra";
					
				} else {
					
					model.addAttribute("lista", listaCompra);

					return "carrito";
					
				}
				
			}
			
		} catch(NullPointerException e) { //Si no se ha creado la sesión correctamente.

			System.out.println("Sesion no creada, volver a registrar.");
			
			return "index";
			
		}
		
	}
	
}