<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<!-- configuración-->
		<title>Bienvenido</title>
		<meta charset="utf-8">
		<!-- Para que se vea en dispositivos moviles -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- Estilos de Boostrap -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
 		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

		
		<!--  JQuery -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	
 	<style>
	    /* Remove the navbar's default rounded borders and increase the bottom margin */ 
	    .navbar {
	      margin-bottom: 50px;
	      border-radius: 0;
	    }
	    	   
	    footer {
	      background-color: #f2f2f2;
	      padding: 25px;
	    }
	
	</style>

	</head>
	
	<body onload="<c:if test="${err!=null}"> alert('${err}') </c:if>" >
		<%! int puerto = 8080; %>
		

		  <nav class="navbar navbar-inverse">
		        <div class="navbar-header">
		            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myCollapse">
		                <span class="icon-bar"></span>
		                <span class="icon-bar"></span>
		                <span class="icon-bar"></span>
		            </button>
		            <a href="index"> <img src="resources/img/logoflyunicorn.jpg" width="50" height="50"></a>
		        </div>
	
		        <div id="myNavbar" class="collapse navbar-collapse">
		            <ul class="nav navbar-nav">
		            	<li class="dropdown"> <a href="index">Home</a>
		            	<li class="dropdown"> <a href="productos">Productos</a></li>
		            </ul>
		            <ul class="nav navbar-nav navbar-right">	
		            	<li class="dropdown"><a href="mostrarCarrito">Carrito</a></li>
						<li class="dropdown"><a href="logout">Logout</a></li>
		            </ul>
		        </div>
		    </nav>
		
		<div class="container">
			<p>
				<h3>Te damos la bienvenida, <c:out value = "${usuario.nombre} ${usuario.apellidos}" />. Ahora podrás visitar nuestro catálogo de productos.</h3>
				<br/><br/>
				Si deseas cambiar tus datos, rellena los siguientes campos:
			</p>
		</div>
		
		<form method="post" action="cambiar">			
			<div class="table-responsive container">
			  <table class="table">
			    <tbody>
				  <tr>
				    <td>Nombre</td>
				    <td>${usuario.nombre}</td>
				    <td> 
				    	<input type="text" class="form-control" placeholder="Nombre" name=name id="inputName">
				    	<span id="msg1" name="msg1"> </span>
				    </td>
				  </tr>
				  
				  <tr>
				    <td> Apellidos</td>
				    <td> ${usuario.apellidos}</td>
				    <td> 
				    <input type="text" class="form-control" id="apellidos" name="apellidos" placeholder="Apellidos" >				    
				    </td>
				  </tr>
				  
				  <tr>
				    <td> Telefono</td>
				    <td> ${usuario.telf}</td>
				    <td> 
				    <input type="text" class="form-control" id="telf" name="telf" placeholder="Tel&eacute;fono">
				    </td>
				  </tr>
				  
				  <tr>
				    <td> Contrase&ntilde;a</td>
				    <td> </td>
				    <td> 
				    <input class="form-control" id="pass" name="pass" type="password"  placeholder="Contraseña">
				    </td>
				  </tr>
				  
				  <tr>
				    <td> email</td>
				    <td> ${usuario.email}</td>
				    <td> 
				    	<input type="email" class="form-control" placeholder="Email" name=email id="inputEmail">
				    	<span id="msg2" name="msg2"> </span>
				    </td>
				  </tr>
				  
				  <tr>
				    <td> Direccion</td>
				    <td> ${usuario.direccion}</td>
				    <td> 
				    <input type="text" class="form-control" id="dir" name="dir" placeholder="Direcci&oacute;n">
				    </td>
				  </tr>
				  
				  <tr>
				  	<td> <button type="submit" class="btn btn-primary">Cambiar</button> </td>
				  </tr>
				</tbody>
			  </table>
			</div>
		</form>
		
		</br></br>
		
		<footer class="container-fluid text-center">
  			<p>Online Store Copyright</p>  
		</footer>
	    
	    <!-- Uso de AJAX para validar si existe ya un nombre de usuario mediante jQuery -->
		<script type="text/javascript">
			$('#inputName').blur(function(){
				$("#msg1").load('http://localhost:8080/practica02/nameDisponible',"name="+$('#inputName').val());
			})
			
			$('#inputEmail').blur(function(){
				$("#msg2").load('http://localhost:8080/practica02/emailDisponible',"email="+$('#inputEmail').val());
			})
		</script>
	</body>
</html>
