<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	
	<head>
		<!-- configuraciÃ³n-->
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
	<!-- Start your code here -->
	<div class="container">
	  <nav role="navigation" class="navbar navbar-default">
	        <div class="navbar-header">
	            <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
	                <span class="sr-only">Toggle navigation</span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <a href="index" class="navbar-brand"> <img src="resources/img/logo.jpg" width="25" height="25"  alt="logo"/> </a>
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
										<input type=email class="form-control" placeholder="aa@aa.a" name=email value="test@test" placeholder="e-mail"  required />
									</div>
								</li>
								<li class="divider"></li>
								<li> 
									<div class="form-group">
									   <label>Password</label>
									   <input type="password"  class="form-control"  placeholder="Password" name="key" value="test" required>
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
			  <div class="panel-heading"> <h4>Terminos y condiciones</h4></div>
			  <div class="panel-body">
			    Solo vale disfrutar.
			  </div>
			</div>
		</div>
	</div>



</body>
<!DOCTYPE html>