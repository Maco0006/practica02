<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<!-- configuración-->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<!-- Para que se vea en dispositivos moviles -->
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
		<!-- Estilos de Boostrap -->
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
		<script type="text/javascript" src="http://getbootstrap.com/dist/js/bootstrap.js"></script>
		<link type="text/css" rel="stylesheet" href="http://getbootstrap.com/dist/css/bootstrap.css">
		<!-- Mi estilos -->
		<link rel="stylesheet" type="text/css" href="resources/estilos.css">
		
		<!--  JQuery -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	</head>
	
	<body onload="<c:if test="${err!=null}"> alert('${err}') </c:if>" >
		<%! int puerto = 8080; %>
		
		<div class="container">
		  <nav role="navigation" class="navbar navbar-default">
		        <div class="navbar-header">
		            <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
		                <span class="sr-only">Toggle navigation</span>
		                <span class="icon-bar"></span>
		                <span class="icon-bar"></span>
		                <span class="icon-bar"></span>
		            </button>
		            <a href="index" class="navbar-brand"> <img src="resources/img/logo.jpg" width="25" height="25" > </a>
		        </div>
	
		        <div id="navbarCollapse" class="collapse navbar-collapse">
		            <ul class="nav navbar-nav pull-right">                   
		            	<li class="dropdown"> <a href="productos" > Ver productos. </a> </li>
		            	<li class="dropdown"><a href="mostrarCarrito" > Carrito </a> </li>
						<li class="dropdown"><a href="logout" >logout</a> </li>
						
		            </ul>
		        </div>
		    </nav>
		</div>
		
		<div class="container">
			<p>
				Hola <c:out value = "${usuario.nombre},${usuario.apellidos}" />  usted solo puede:
					<ul>
						<li> Efectuar compras.
						<li> Cambiar datos.
					</ul>
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
				    <input class="form-control" id="pass" name="pass" type="password"  placeholder="Password">
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
