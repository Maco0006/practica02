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



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	//Vista que cambia el recurso jsp
	final String [] vistas = {"index","registro","admin","bienvenido","compra","carrito","terminos"};
	
	//Busca en servlet-content un bean que implemente un DaoUsuario_Interface,
	//para poder acceder a la base de datos
	@Autowired
	DaoUsuario_Interface daou; 
	
	//Busca en servlet-content un bean que implemente un DaoProducto_Interface, 
	//para poder acceder a la base de datos
	@Autowired
	DaoProducto_Interface daop;
	
	
	/**
	 * M�todo para resolver petici�n http://localhost:8080/practica02/
	 * Permite el acceso de un usuario para registrarse o administrar la web
	 * 
	 * @param request Petici�n del cliente
	 * @param response Respuesta hacia el cliente en la que se env�a la cookie
	 * @param model Objeto para pasar par�metros a la vista
	 * @return Cadena de caracteres que indican el nombre de la vista
	 */
	@RequestMapping(value ={"/", "/index"}, method = {RequestMethod.GET,RequestMethod.POST} )
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		
		Boolean haycookies = true;
		//Fecha del sistema
    		Date today = new Date();
		
		//Para almacenar los productos de compra
		List<DtoProducto> listaCompra = null;
		//Variable para guardar una vista jsp 
		String vista = null;
		
		//Permite asociar una sesi�n a un usuario
		HttpSession session;
		
    		//Contiene los datos del usuario que realiz� la petici�n
		DtoUsuario usuarioDB = null;
		

		Boolean esuser=false;
		Boolean esadmin=false;
		
		//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
		session = request.getSession(false);
		
		DtoUsuario user;
		
		//Aviso de errores
		String [] errores ={"Contrase&ntilde;a err&oacute;rena","Error en el usuario o no existe"};
		String err = null; //Por defecto sin errores.
		
		if(session!=null){
			//Extraemos el objeto usuario de la sesi�n
			user = (DtoUsuario) session.getAttribute("usuario");
		}else{
			user = null;
		}
		

		//Si existe usuario en la sesi�n
		if(user!=null){
			System.out.println("Existe sesi�n");
			//Mostramos nombre
			System.out.println("Nombre: "+user.getNombre());
			
			//Comprobamos si existe en la BD el usuario con email de la cookie
			Boolean existeusuario = daou.existsName(user.getNombre());
			
			if(existeusuario){
				
				if(user.getAdmin()){
					esadmin = true;
					
					//Obtenemos la lista de usuario y la agregamos a la vista.
					model.addAttribute("listaUsuario",daou.read());
					
					//Mostramos la p�gina del administrador
					vista = vistas[2];
					
				}else{
					esuser=true;
					//Mostramos la p�gina de bienvenido
					vista = vistas[3];
				}
			}else{
				//Mostramos la p�gina de inicio
				vista = vistas[0];
				System.out.println("No existe usuario de sesi�n en base de datos");
			}
			
			
		}else{//Si no existe sesi�n
			System.out.println("NO Existe sesi�n");
			//Extraemos las cookies de la petici�n
			Cookie[] cookies = request.getCookies();
			
			//Si existen las cookies
			if(cookies!=null){
				
				//Recorremos las cookies
				for (Cookie cookie: cookies){
					//Hasta que encontramos la que se llama email
					if ("email".equals(cookie.getName())){
						System.out.println("Existen cookies");
						
						//Actualizamos el tiempo de la cookie que devolveremos a 1 dia
						cookie.setMaxAge(60*60*24);
						
						//Obtiene el valor del campo email
						String valorCookie = cookie.getValue();
						//Comprobamos si existe en la BD el usuario con email de la cookie
						Boolean existeUsu = daou.existsEmail(valorCookie);
						
						if(existeUsu){
							System.out.println("Existe usuario de cookies");
							
							//Sacamos objeto de la clase Usuario
							usuarioDB=daou.obtenerUsu(valorCookie);
							
							//Enviamos el objeto de la clase Usuario al jsp
							model.addAttribute("usuario", usuarioDB);
							
							DtoUsuario usuario = daou.obtenerUsu(valorCookie);
							
							if(usuario.getAdmin()){
								esadmin=true;
								System.out.println("entra1");
								
							}else if(!usuario.getAdmin()){
								esuser=true;
								
								try{
									//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
									session = request.getSession(false);
									
									//Extraemos el objeto usuario de la sesi�n
									DtoUsuario usuar = (DtoUsuario) session.getAttribute("usuario");
									System.out.println("Hay sesion");
									//Mostramos nombre
									System.out.println("Nombre: "+usuar.getNombre());
									
									//Mostramos la vista bienvenido.jsp
									vista=vistas[3];
									
									
								}catch(NullPointerException e){//Si no hay sesi�n
									System.out.println("No hay sesi�n");
									
									//Obtenemos fecha de creaci�n de la sesi�n
									SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:SS");
						        	String date = DATE_FORMAT.format(today);
						        	System.out.println("Conexion: " + date);
									
						        	
									//Creamos la sesi�n
									session = request.getSession(true);
									//A�adimos el usuario en la sesi�n
									session.setAttribute ("usuario",usuarioDB);
						        	//A�adimos la fecha en la sesion
									session.setAttribute("date", date);
									//Establecemos la lista de productos a null
									session.setAttribute("lista", listaCompra);
									//Inicializamos el total de la sesi�n.
									Double total=0.0;
									session.setAttribute("total", total);
									//Establecemos la expiraci�n de la sesi�n a 1 hora
									session.setMaxInactiveInterval(60*60);							

									
									//Enviamos la lista de productos al jsp
									model.addAttribute("lista", listaCompra);
									
									//Mostramos bienvenido.jsp
									vista=vistas[3];
								}
								
								
							}
							
							//Agregamos la cookie a la respuesta
							response.addCookie(cookie);
							
							//Enviamos el objeto de la clase Usuario al jsp
							model.addAttribute("usuario", usuarioDB);
							
						}else{
							System.out.println("No existe usuario de la cookie");
						}
						
					}
				}
				
				if(esadmin==false && esuser==false){
					//Si tampoco hay cookies
					System.out.println("NO Existen cookies");
					
					haycookies=false;
				}
				
			}
			if(cookies==null || haycookies==false){
				//Petici�n con par�metros vac�os
				if(request.getParameter("email")==null || request.getParameter("key")==null){
					//Mostramos index.jsp
					vista = vistas[0];
					
				}else{//Si hay datos en la petici�n
					
					//Leemos par�metros del formulario index.jsp
					String id_user = request.getParameter("email");
					String id_key = request.getParameter("key");
					
					//Imprimimos por consola del servidor el usuario y la contrase�a
					System.out.println("name= "+id_user+" key= "+id_key);
					
					//Comprobamos si existe en la base de datos
					Boolean existeUsu = daou.existsEmail(id_user);
					
					//Si no existe
					if (!existeUsu){
						System.out.println("Error en el usuario o no existe");
						
						//Pasamos error a la vista
						err = errores[1];
						
						//Web para que se registre como usuario (registro.jsp)
						vista = vistas[0];
						
					}else{//Si existe
						System.out.println("Usuario existe");
						
						//Obtenemos los datos del usuario
						usuarioDB = daou.obtenerUsu(id_user); 
						
						//Si no coincide la clave de la base de datos
						if(!usuarioDB.getClave().equals(id_key)){
							System.out.println("Error en la pass.");
							
							//Pasamos error a la vista
							err = errores[0];
							
							vista = vistas[0];
							
						}else{//La clave coincide
							
							//Creamos la cookie.
							System.out.println("Creamos cookie y sesion");
							Cookie cookie = new Cookie("email",usuarioDB.getEmail());
							// Tiempo de la cookie 1 dia
						    cookie.setMaxAge(60*60*24);
							
							//Si el usuario es administrador
							if(usuarioDB.getAdmin()){
								
								esadmin = true;
								
							}else{//Si es usuario
								
								esuser = true;
								
								model.addAttribute("usuario", usuarioDB);
							}

							//La agregamos a la respuesta
							response.addCookie(cookie);
						}
					}
				}
			}
		}
			
		
		
		
		if(esadmin){
        	//Creamos la sesi�n
		    session = request.getSession(true);
			
			//Obtenemos fecha de creaci�n de la sesi�n
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:SS");
        	String date = DATE_FORMAT.format(today);//Realizamos el formateo de la fecha.
        	
			//A�adimos el usuario en la sesi�n
			session.setAttribute ("usuario",usuarioDB);
			//A�adimos la fecha en la sesi�n
			session.setAttribute("date", date);
			//Establecemos la expiraci�n de la sesi�n a 60 minutos
			session.setMaxInactiveInterval(30*60);
			
			//Obtenemos la lista de usuario y la agregamos a la vista.
			model.addAttribute("listaUsuario",daou.read());
			
			//Mostramos la vista del administrador admin.jsp
			vista = vistas[2];
			
		}else if(esuser){
			
			try{
				System.out.println("try");
				//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
				session = request.getSession(true);
				
				//Extraemos el objeto usuario de la sesi�n
				DtoUsuario usuario = (DtoUsuario) session.getAttribute("usuario");
				
				//Mostramos nombre
				System.out.println("Nombre: "+usuario.getNombre());
				
				//Accedemos a la lista de compra almacenada en la sesi�n
				listaCompra = (List<DtoProducto>) session.getAttribute("lista");
				
				//Mostramos la vista bienvenido.jsp
				vista=vistas[3];
					
			}catch(NullPointerException e){
				System.out.println("catch");
				
				Metodos.CrearSession(request, usuarioDB, listaCompra);
				
				//Enviamos la lista de productos al jsp
				model.addAttribute("lista", listaCompra);
				
				//Mostramos la vista bienvenido.jsp
				vista=vistas[3];
			}
		}
		
		//Mostramos la vista correspondiente en funic�n del resultado.
		model.addAttribute("err", err);
		return vista;
	}


	/**
	 * M�todo para resolver petici�n http://localhost:8080/practica02/registro
	 * Permite el acceso de un usuario para registrarse
	 * 
	 * @param request Petici�n del cliente
	 * @param model Objeto para pasar par�metros a la vista
	 * @return Cadena de caracteres que indican el nombre de la vista
	 */
	@RequestMapping(value = "/registro", method = {RequestMethod.GET,RequestMethod.POST})
	public String registro(HttpServletRequest request,Model model) {
		
		String vista;
		String err=null;
		
		
		//Lista que contiene los productos
		List<DtoProducto> lista = new ArrayList<DtoProducto> ();
		
		//Si alguno de los campos del formulario es null
		if(request.getParameter("name")==null|| request.getParameter("key")==null ||
				request.getParameter("apellidos")==null || request.getParameter("email")==null ||
				request.getParameter("dir")==null || request.getParameter("telf")==null){
			
			//Volvemos a cargar la p�gina registro.jsp
			vista = vistas[1];
			
		}else{//Si no hay fallo al introducir un nuevo usuario
			
			//Extraemos los datos del formulario
			String nombre = request.getParameter("name");
			String email = request.getParameter("email");
			
			if(daou.existsName(nombre) || daou.existsEmail(email)){
				
				//Volvemos a cargar la p�gina de usuario (registro.jsp)
				vista = vistas[1];
				err= "Nombre o email en uso.";
				System.out.println("Nombre o email duplicado");
				
			}else{
				
				//Devolvemos la vista "bienvenido.jsp"
				vista = vistas[3];
				err=null;
				
				// Extraemos del formulario el resto de datos
				String apellidos = request.getParameter("apellidos");
				String key = request.getParameter("key");
				String direccion =request.getParameter("dir");
				String telf= request.getParameter("telf");
				
				//Se crea el objeto de la clase Usuario
				DtoUsuario usuario = new DtoUsuario (nombre, key, apellidos, email, direccion, telf);
							
				//A�adimos al usario en la base de datos.
				daou.create(usuario);
				
				Metodos.CrearSession(request, usuario, lista);

				//Enviamos el objeto de la clase Usuario al jsp
				model.addAttribute("usuario", usuario);//request.setAttribute("usuario", usuario);
				//Enviamos la lista de productos al jsp
				model.addAttribute("lista", lista);//request.setAttribute("lista", lista);

			}
			
		}
		
		model.addAttribute("err", err);

		return vista;
	}
	
	
	/**
	 * M�todo para resolver petici�n http://localhost:8080/practica02/productos
	 * Permite el acceso de un usuario para ver los productos a comprar
	 * 
	 * @param request Petici�n del cliente
	 * @param model Objeto para pasar par�metros a la vista
	 * @return Cadena de caracteres que indican el nombre de la vista
	 */
	@RequestMapping(value = "/productos", method = {RequestMethod.GET,RequestMethod.POST})
	public String productos(HttpServletRequest request,Model model) {
		
		//Variable para cambiar la vista
		String vista;
		
		try{
			System.out.println("Entro en productos, Si hay sesion");
			
			//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
			HttpSession session = request.getSession(false);
			
			//Extraemos el objeto usuario de la sesi�n
			DtoUsuario user = (DtoUsuario) session.getAttribute("usuario");
			
			//Mostramos nombre
			System.out.println(user.getNombre());
			
			//Accedemos a la base de datos de productos.
			List<DtoProducto> listaProductos = daop.read();
			
			//Enviamos lista al jsp
			model.addAttribute("lista", listaProductos);
			
			//Cambiamos a vista de compra.jsp
			vista = vistas[4];


		}catch(NullPointerException e){//Si no se ha creado la sesi�n correctamente
			
			System.out.println("Sesi�n no creada, volver a registrar. ");
			
			//Cambiamos a index, problemas al inicio de sesion.
			vista = vistas[0];

		}
		
		return vista;
	}

	/**
	 * M�todo para resolver petici�n http://localhost:8080/practica02/carrito
	 * Permite el acceso de un usuario para ver los productos ya comprados
	 * 
	 * @param request Petici�n del cliente
	 * @param model Objeto para pasar par�metros a la vista
	 * @return Cadena de caracteres que indican el nombre de la vista
	 */
	@RequestMapping(value = "/carrito", method = {RequestMethod.GET,RequestMethod.POST})
	public String carrito(HttpServletRequest request, Model model) {
				
		//Variable para cambiar la vista
		String vista;
		
		String err = null;
		
		int contador=0;
		
		
		String [] opcion=null,cantidad=null;


		try{
			
			//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
			HttpSession session = request.getSession(false);
			
			//Extraemos el objeto usuario de la sesi�n
			DtoUsuario user = (DtoUsuario) session.getAttribute("usuario");
			Double total = (Double) session.getAttribute("total");
			//Extraemos la lista de objetos de la sesi�n 
			List<DtoProducto> listaCompra = (List<DtoProducto>) session.getAttribute("lista");
			
			//Accedemos a la base de datos de productos.
			List<DtoProducto> listaProductos = (ArrayList<DtoProducto>) daop.read();
			
			//Si opci�n (de compra.jsp) es distinto de null
			if(request.getParameter("opcion")!=null){
				
				//Recogemos opci�n
				opcion = request.getParameterValues("opcion");//Obtenemos el id del producto seleccionado. Hemos hecho que coindica el id con el valor de la opcion.
				cantidad= request.getParameterValues("uni");//Obtenemos las cantidades de los productos seleccionados.
				
				
				//Recorremos las posibles opciones
				for(int i=0; i<opcion.length;i++){
					//Recorremos los productos
					for(int j=0; j<listaProductos.size();j++){
						
						//Si coincide el id de la opci�n, con el id del producto
						if(Integer.parseInt(opcion[i])== listaProductos.get(j).getId()){
							
							//Si la cantidad es ""
							if(cantidad[j].equals("")){
								System.out.println("No ha indicado ninguna cantidad.");
								err="Introduce una cantidad.";
								
							}else{
								
								//Si la cantidad es 0
								if(Integer.parseInt(cantidad[j])==0){
									//No se ha insertado ninguna cantidad.
									System.out.println("Ningun producto.");
									err="Indica cantidad del producto selecionado";
									
								}else{
									//Si la cantidad es menor que 0
									if(Integer.parseInt(cantidad[j])<0){//|| Integer.parseInt(cantidad[j])>listaProductos.get(j).getUnidades()
										//Cantidad no v�lida
										System.out.println("Cantidad no v�lida.");
										err="Cantidad no v�lida.";
										
									}else{//Si es v�lida la cantidad
										
										int cantid = Integer.parseInt(cantidad[j]);
										
										if(cantid>listaProductos.get(j).getUnidades()){
											//Productos insuficiente.
											System.out.println("Productos insuficientes.");
											err="Productos insuficientes.";
											
										}else{
											//Si no se ha realizado ninguna compra todav�a
											if(listaCompra == null){
												listaCompra = new ArrayList();
											}
											
											if(listaCompra.isEmpty()){//Esta vac�a
												
												cantid = Integer.parseInt(cantidad[j]); //Extraemos la cantidad solicitada.
												
												//Calculamos lo que debe
												total = total + Integer.parseInt(cantidad[j]) * listaProductos.get(j).getImporte(); 
												//Indicamos la cantidad
												listaProductos.get(j).setUnidades(cantid);
												listaCompra.add(listaProductos.get(j));
												
												
											}else{//Ya se realiz� una compra anteriormente
												System.out.println("Ya hay compra");
												
												//Recorremos la lista de la compra
												for(int o=0;o<listaCompra.size();o++){
													
													//Si el id de la lista coincide con el del producto elegido
													if(listaCompra.get(o).getId() == listaProductos.get(j).getId()){
														
														//Extraemos la cantidad existente
														cantid = Integer.parseInt(cantidad[j]); 
														
														if(cantid+listaCompra.get(o).getUnidades()>listaProductos.get(j).getUnidades()){
															//Productos insuficiente.
															System.out.println("Productos insuficientes.");
															err="Productos insuficientes.";
															
														}else{
															//Calculamos lo que debe
															total = total + Integer.parseInt(cantidad[j]) * listaProductos.get(j).getImporte(); 
															listaProductos.get(j).setUnidades(Integer.parseInt(cantidad[j]));
															listaCompra.get(o).setUnidades(cantid+listaCompra.get(o).getUnidades());
															
															//Indicamos que ya se ha introducido el producto
															contador=0;
															//Salimos del for
															o=listaCompra.size();
														}
													}else{
														//Indicamos que el producto seleccionado no esta en la lista de compra
														contador=2;
													}
												}
											}
											
											//Si el contador es 2
											if(contador==2){//A�adimos el nuevo producto a la lista de compra
												//Extraemos la cantidad solicitada
												cantid = Integer.parseInt(cantidad[j]);
												
												if(cantid>listaProductos.get(j).getUnidades()){
													//Productos insuficiente.
													System.out.println("Productos insuficientes.");
													err="Productos insuficientes.";
													
												}else{
													//Calculamos lo que debe
													total = total + Integer.parseInt(cantidad[j]) * listaProductos.get(j).getImporte();
													listaProductos.get(j).setUnidades(cantid);//Indicamos la cantidad solicidata
													listaCompra.add(listaProductos.get(j));
													contador=0;
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
				
				//Si se insert� alg�n objeto al carrito
				if(contador!=0){
					
					//Muestro vista de carrito
					vista=vistas[5];

					//Actualizamos la sesi�n con la nueva compra
					session.setAttribute ("lista",listaCompra);
					session.setAttribute("total", total);
					
				}else{//No se insert� nig�n objeto al carrito
					
					//Muestro vista de compra
					vista=vistas[4];
					
					//Enviamos lista de productos al jsp
					model.addAttribute("lista", daop.read());
					model.addAttribute("err",err);
				}
				
			}else{ //Si no elige ning�n producto

				vista=vistas[4];
				err="No has seleccionado ningun producto";
				
				//Enviamos lista de productos al jsp
				model.addAttribute("lista", daop.read());
				model.addAttribute("err",err);
			}
			
		}catch(NullPointerException e){//Si no se ha creado la sesi�n correctamente
			
			//Vista para que inicie sesion index.jps
			vista=vistas[0];
			System.out.println("Sesion no creada, volver a registrar.");
		}
		
		return vista;
	}

	/**
	 * M�todo para resolver petici�n http://localhost:8080/practica02/eliminar
	 * Permite a un usuario, borrar de la sesi�n, alg�n producto anteriormente seleccionado
	 * 
	 * @param idDelete Id del producto a borrar
	 * @param request Petici�n del cliente
	 * @param model Objeto para pasar par�metros a la vista
	 * @return Cadena que indica el jsp a mostrar
	 */
	@RequestMapping(value = "/eliminar", method = {RequestMethod.GET})
	public String eliminar(@RequestParam(value="name",required=true) int idDelete,HttpServletRequest request, Model model) {
		
		String vista;
		
		try{
			//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
			HttpSession session = request.getSession(false);
						
			//Extraemos la lista de objetos de la sesi�n 
			List<DtoProducto> listaCompra = (List<DtoProducto>) session.getAttribute("lista");
			//Extraemos total de la sesi�n
			 double total = (Double) session.getAttribute("total");
			 
			 int i=0;
			 //Recorremos la lista de la compra
			 while(i<listaCompra.size()){
				 
				 //Si el id del producto a borrar es igual al de la lista
				 if(listaCompra.get(i).getId() == idDelete){
					 
					 //Se descuenta del total, las unidades por el precio de cada una
					 total=total - listaCompra.get(i).getImporte()*listaCompra.get(i).getUnidades();
					 //Eliminamos el producto de la lista
					 listaCompra.remove(i);
					 
					 //Salimos de la lista
					 i=listaCompra.size();
				 }
				 i++;
			 }
			 
			 if(total<0) total=0.0; //Por problemas de decimales.
			 
		 	//Actualizamos la sesi�n con la nueva lista y el nuevo precio	
			session.setAttribute ("lista",listaCompra);
			session.setAttribute("total", total);
			
			//Mostramos la lista de compra.
			vista=vistas[5];
			
		}catch(NullPointerException e){//Si no se ha creado la sesi�n correctamente
			
			//Vista para que inicie sesion index.jps
			vista=vistas[0];
			System.out.println("Sesion no creada, volver a registrar.");
		}
		
		return vista;
	}
	
	/**
	 * M�todo para resolver la petici�n http://localhost:8080/practica02/fin
	 * Se accede una vez vuelva el cliente de la p�gina de paypal,
	 * y permite actualizar la base de datos con los productos comprados
	 * 
	 * @param request Petici�n del cliente
	 * @param model Objeto para pasar par�metros a la vista
	 * @return Cadena que indica el jsp a mostrar
	 */
	@RequestMapping(value = "/fin", method = RequestMethod.GET)
	public String finCompra(HttpServletRequest request, Model model) {
		
		//Variable para cambiar la vista
		String vista;
		
		Double total;
		
		List<DtoProducto> listaProductos = daop.read();
		
		try{
			//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
			HttpSession session = request.getSession(false);
						
			//Extraemos la lista de objetos de la sesi�n 
			List<DtoProducto> listaCompra = (List<DtoProducto>) session.getAttribute("lista");
			//Extraemos total de la sesi�n
			total = (Double) session.getAttribute("total");
			
			//Recorremos la lista de la compra
			for(int i=0;i<listaCompra.size();i++){
				int j=0;
				//Recorremos la lista de productos
				while(j<listaProductos.size()){
					//Si el id del carrito y el id de la lista de productos coincide
					if(listaCompra.get(i).getId()==listaProductos.get(j).getId()){
						
						//Se restan las unidades de la base de datos
						daop.actualizar(listaProductos.get(i).getUnidades()-listaCompra.get(i).getUnidades(), listaProductos.get(j).getId());
						j=listaProductos.size();
					}
					j++;
				}
			}
			 
			total=0.0; //Por problemas de decimales.
			listaCompra = new ArrayList();
			
			//Actualizamos la sesi�n con lista y total vac�os
			session.setAttribute ("lista",listaCompra);
			session.setAttribute("total", total);
			//Mostramos la lista de compra.
			vista=vistas[5];
			
		}catch(NullPointerException e){//Si no se ha creado la sessi�n correctamente
			
			//Vista para que inicie sesion index.jps
			vista=vistas[0];
			System.out.println("Sesion no creada, volver a registrar.");
		}
		return vista;
	}
	
	/**
	 * M�todo para resolver la petici�n http://localhost:8080/practica02/logout
	 * Permite al usuario eliminar sesi�n y cookies para utilizar otra cuenta
	 * 
	 * @param request Petici�n del cliente
	 * @param model Objeto para pasar par�metros a la vista
	 * @return Cadena que indica el jsp a mostrar
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpServletResponse response, Model model) {
		//Variable para cambiar la vista
		String vista;
		
		try{
			//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
			HttpSession session = request.getSession(false);
			
			//Invalidamos la sesi�n
			session.invalidate();
			
			//Extraemos las cookies de la peticion.
			Cookie[] cookies = request.getCookies();

			int i=0;
			//Recorremos las cookies
			while(i<cookies.length){
				//Si coincide con la de nombre email
				if(cookies[i].getName().equals("email")){
					//Borramos la cookie
					cookies[i].setValue(" ");
					
					//Agregamos la cookie a la respuesta
					response.addCookie(cookies[i]);
					
					//Salimos del while
					i=cookies.length;
				}
				i++;
			}
			 
			//Mostramos la lista de index.jsp.
			vista=vistas[0];
			
			
		}catch(NullPointerException e){//Si no se ha creado la sessi�n correctamente
			
			//Vista para que inicie sesion index.jps
			vista=vistas[0];
			System.out.println("Sesion no creada, volver a registrar.");
		}
		
		return vista;
	}
	
	/**
	 * M�todo para resolver la petici�n http://localhost:8080/practica02/nameDisponible
	 * Permite a la vista de registro, comprobar si existe un usuario ya registrado con el nombre
	 * 
	 * @param idName Nombre del par�metro de petici�n, del formulario
	 * @return Cadena que indica el jsp a mostrar
	 */
	@RequestMapping(value = "/nameDisponible", method = RequestMethod.GET)
	public @ResponseBody String nameDisponible(@RequestParam(value="name",required=true) String idName) {
		String msg=null;
		
		//Variable que indica si existe el nombre de usuario
		boolean existe = daou.existsName(idName);
		
		//Si existe, cambia el mensaje de la vista
		if(existe)	msg = "<strong> Usuario ya existe </strong>";
		
		return msg;
	}
	
	/**
	 * M�todo para resolver la petici�n http://localhost:8080/practica02/emailDisponible
	 * Permite a la vista de registro, comprobar si existe un usuario ya registrado con el email
	 * 
	 * @param idEmail email del par�metro de petici�n, del formulario
	 * @return Cadena que indica el jsp a mostrar
	 */
	@RequestMapping(value = "/emailDisponible", method = RequestMethod.GET)
	public @ResponseBody String emailDisponible(@RequestParam(value="email",required=true) String idEmail) {
		String msg=null;
		
		//Variable que indica si existe el nombre de usuario
		boolean existe = daou.existsEmail(idEmail);
		
		//Si existe, cambia el mensaje de la vista
		if(existe)	msg = "<strong> Email ya existe </strong>";
		
		return msg;
	}
	/**
	 * M�todo para ir al perfil (bienvenido.jsp) del usuario
	 * 
	 * @param request de tipo HttpServletRequest
	 * @param model de tipo Model
	 * @return vista de tipo String
	 */
	@RequestMapping(value = "/perfil", method = RequestMethod.GET)
	public String perfil(HttpServletRequest request,Model model) {
		String vista;
		try{
			//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
			HttpSession session = request.getSession(false);
			
			//Extraemos el objeto usuario de la sesi�n
			DtoUsuario user = null;
			user = (DtoUsuario) session.getAttribute("usuario");
			
			if(user!=null){ //Produce un NullPointerException si no hay session.
				model.addAttribute("usuario", user);	
			}
			
			vista= vistas[3];
			
			
		}catch(NullPointerException e){//Si no se ha creado la sessi�n correctamente
			//Vista para que inicie sesion index.jsp
			vista=vistas[0];
			System.out.println("Sesion no creada o caducada, volver a registrar.");
		}
		return vista;
	}
	
	/**
	 * M�todo para resolver la petici�n http://localhost:8080/practica02/cambiar
	 * Permite al usuario cambiar cualquier campo de datos de su perfil.
	 * 
	 * @param request de tipo HttpServletRequest
	 * @param model de tipo Model
	 * @return vista de tipo String
	 */
	@RequestMapping(value = "/cambiar", method = RequestMethod.POST)
	public String cambiar(HttpServletRequest request, Model model,HttpServletResponse response) {
		String vista;
		String errores[] = {"Nombre ya en uso","Email ya en uso"};
		String emailAntiguo;
		String err=null;
		try{
			
			//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
			HttpSession session = request.getSession(false);
			
			//Extraemos el objeto usuario de la sesi�n
			DtoUsuario user = (DtoUsuario) session.getAttribute("usuario");
			System.out.println("User session: "+user.getEmail());
			//Obtenemos los datos solicitados a cambiar
			String usr = request.getParameter("name");
			String email = request.getParameter("email");
			String telf = request.getParameter("telf");
			String apll = request.getParameter("apellidos");
			String dir = request.getParameter("dir");
			String pass = request.getParameter("pass");
			
			//Extremos el e-email del propietario de la sesion y cookie.
			emailAntiguo=user.getEmail();
			
			//Actualizamos campos.
			if(!usr.equals("")){
				if(!daou.existsName(usr)){
					user.setNombre(usr);
				}else{
					err=errores[0];
				}
				
			}
			
			if(!email.equals(""))
			{
				System.out.println(email);
				if(!daou.existsEmail(email)){
					user.setEmail(email);
						
					//Modificamos la cooki del servicio.
					
					
					//Extraemos las cookies de la peticion.
					Cookie[] cookies = request.getCookies();

					int i=0;
					//Recorremos las cookies
					while(i<cookies.length){
						//Si coincide con la de nombre email
						if(cookies[i].getName().equals("email")){
							//Borramos la cookie
							cookies[i].setValue(email);
							
							//Agregamos la cookie a la respuesta
							response.addCookie(cookies[i]);
								
							//Salimos del while
							i=cookies.length;
						}
						i++;
					}
				}else{
					err=errores[1];
				}

				
			}
			
			if(!telf.equals("")){
				user.setTelf(telf);
			}
			
			if(!apll.equals("")){
				user.setApellidos(apll);
			}
			
			if(!pass.equals("")){
				user.setClave(pass);
			}
			
			if(!dir.equals("")){
				user.setDireccion(dir);
			}

			//Realizamos el cambio
			daou.actualizar(user,emailAntiguo);
			
			//Actualizamos el objeto DtoUsuario de la sesion
			session.setAttribute ("usuario",user);
			
			//Devolvemos el usuario a la lista.
			model.addAttribute("usuario", user);
			model.addAttribute("err", err);
			
			//Vista bienvenido.jsp
			vista= vistas[3];
		}catch(NullPointerException e){//Si no se ha creado la sessi�n correctamente
			//Vista para que inicie sesion index.jsp
			vista=vistas[0];
			System.out.println("Sesion no creada, volver a registrar.");
		}
		return vista;
	}
	/**
	 * M�todo para mostrar la lista de productos comprados en la sesion
	 * 
	 * @param request de tipo HttpServletRequest
	 * @param model de tipo Model
	 * @return vista de tipo String
	 */
	@RequestMapping(value = "/mostrarCarrito", method = RequestMethod.GET)
	public String mostrarCarrito(HttpServletRequest request, Model model) {
		String vista;
		
		String [] errores = {"Lista de compra no inicializada","Lista de compra vac&iacute;a"};
		String err=null;
		
		List<DtoProducto> listaCompra =null;
		try{
			//M�todo que obtiene la sesi�n de la petici�n, y si no existe da error
			HttpSession session = request.getSession(false);
			
			//Obtenemos la lista de compra
			listaCompra = (List<DtoProducto>) session.getAttribute("lista");
			
			
			if(listaCompra==null){
				System.out.println("Lista de Compra no inicializada");
				
				//Avisamos de error.
				err=errores[0];
				model.addAttribute("err",err);
				
				//Extremos los producos y los pasamos a la vista
				model.addAttribute("lista",daop.read());
				
				//Mostramos bienvendio.jsp
				vista = vistas[4];
			}else{
				if(listaCompra.isEmpty())
				{
					System.out.println("Lista de Compra vac�a");
				
					//Avisamos de error.
					err=errores[1];
					model.addAttribute("err",err);
					
					//Extremos los producos y los pasamos a la vista
					model.addAttribute("lista",daop.read());
					
					//Mostramos bienvendio.jsp
					vista = vistas[4];
					
					
				}else{
					model.addAttribute("lista",listaCompra);
					
					//Mostramos carrito.jsp
					vista = vistas[5];
				}
			}
		}catch(NullPointerException e){//Si no se ha creado la sessi�n correctamente
			//Vista para que inicie sesion index.jsp
			vista=vistas[0];
			System.out.println("Sesion no creada, volver a registrar.");
		}
		return vista;
	}
	/**
	 * M�todo para mostrar los terminos y condiciones del servicio.
	 * 
	 * @param request de tipo HttpServletRequest
	 * @param model de tipo Model
	 * @return vista de tipo String
	 */
	@RequestMapping(value = "/terminos", method = RequestMethod.GET)
	public String terminos(HttpServletRequest request, Model model) {
		String vista;
		vista=vistas[6];
		return vista;
	}
	
	
	
}