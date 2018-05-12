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
		<!-- Ajustes -->
		<title>Dcervezas</title>
	</head>
	
	<body>
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
		            	<li class="dropdown"> <a href="logout" >logout</a> </li>
		            </ul>
		        </div>
		    </nav>
		</div>
	
		<div class="container">
			<h1> Web del administrador. </h1>
			<p> En esta web se muestran las cookie disponibles  y los usuarios </p>
			
			<!--  Lista de cookies -->
			<div class="table-responsive">
				<table class="table">
	    			<c:forEach items="${cookie}" var="user">
			 		<tr>
			    		<td>
			    			<c:out value="${user.value.name}" />: 
			    		</td>
			    		<td>
			    		    <c:out value="${user.value.value}" />
			    		</td>
			    	</tr>
			 		</c:forEach>
	  			</table>
			</div>
			
			<!--  Lista de usuarios y administradores -->
			<div class="table-responsive">
				<table class="table table-bordered table-hover">
				    <thead>
				        <tr>
				            <th>Nombre</th>
				            <th>e-mail</th>
				            <th>Apellidos</th>
				            <th>telefono</th>
				            <th>Direccion</th>
				            <th>Administrador</th>
				        </tr>
				    </thead>
				    <tbody>
					    <c:forEach items="${listaUsuario}" var="n">
							 <tr>
							 	<td>${n.getNombre()}</td>
							 	<td>${n.getApellidos()}</td>
							 	<td>${n.getEmail()}</td>
							 	<td>${n.telf}</td>
							 	<td>${n.direccion}</td>
							 	<td>${n.admin}</td>
							 </tr>
						</c:forEach>
				    </tbody>
				</table>
			</div>
		</div>
		
		
	</body>
</html>