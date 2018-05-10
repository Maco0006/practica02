<!--  Version 0.0.2 de index jsp -->
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
	<body onload="<c:if test="${err!=null}"> alert('${err}') </c:if>">
		<!-- Start your code here -->
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
		                <li class="dropdown">
		                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">Loggin <b class="caret"></b></a>
		                    <ul role="menu" class="dropdown-menu">
		                        <form method="post" action="index">
									<li> 
										<div class="input-group">
											<label>E-mail</label>
											<input type=email class="form-control" placeholder="Introduce e-mail" name=email value="test@test" placeholder="e-mail"  required />
										</div>
									</li>
									<li class="divider"></li>
									<li> 
										<div class="form-group">
										   <label>Password</label>
										   <input type="password"  class="form-control"  placeholder="Introduce contraseña" name="key" value="test" required>		   
										</div>
									</li>
									<li class="divider"></li>
									<li>
										<div class="form-group">
											<button type="submit" class="btn btn-primary">Acceso</button>
										</div>
									</li>												
								</form>
		                    </ul>
		                </li>
		                <li class="dropdown"><a href="registro" >Registro</a> </li>
	
		            </ul>
		        </div>
		    </nav>
		</div>
	
		<div class="container">
			<div class="jumbotron">
			    <div class="panel panel-danger">
				  <div class="panel-heading"> <h4>¡Atención! <small>amantes de la vida</small></h4></div>
				  <div class="panel-body">
				    <table class="table" >
				    	<tr>
				    		<td rowspan="2"> <img src="resources/img/img1.jpg"  alt="imagen 1"/> </td>
				    		<td> Os traigo lo que andas buscando, para disfutrar con los tuyos o solitario. De manera que ya no tienes escusa para !vivir menos¡.</td>
				    	</tr>
	
				    	<tr>
				    		<td> <center> <img src="resources/img/img2.jpg"  width="125" height="125" alt="chiste 1"/> </center> </td>
				    	</tr>
				    </table>
				  </div>
				</div>
			</div>
		</div>
	
	</body>
<!DOCTYPE html>

