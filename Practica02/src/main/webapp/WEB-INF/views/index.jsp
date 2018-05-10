<!-- Configuracion jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
	<head>
		<!-- Configuracion pagina -->
		<title>Home Page</title>
  		<meta charset="utf-8">
  		<!-- Configuracion para moviles -->
  		<meta name="viewport" content="width=device-width, initial-scale=1">
  		<!-- Librerias de estilos Bootstrap -->
  		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  		<!-- Estilos -->
  		<style>
    		/* Remove the navbar's default margin-bottom and rounded borders */ 
    		.navbar {
      			margin-bottom: 0;
      			border-radius: 0;
    		}
    		
    		/* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    		.row.content {height: 525px;}
    
    		/* Set gray background color and 100% height */
    		.sidenav {
      			padding-top: 20px;
      			background-color: #f1f1f1;
      			height: 100%;
    		}
    
    		/* Set black background color, white text and some padding */
    		footer {
      			background-color: #555;
      			color: white;
     			padding: 15px;
    		}
    
    		/* On small screens, set height to 'auto' for sidenav and grid */
   			@media screen and (max-width: 767px) {
      			.sidenav {
        			height: auto;
        			padding: 15px;
      			}
      			.row.content {height:auto;} 
    		}
		</style>
	</head>
	<body onload="<c:if test="${err!=null}"> alert('${err}') </c:if>">

		<nav class="navbar navbar-inverse">
		  <div class="container-fluid">
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>                        
		      </button>
		      <a class="navbar-brand" href="index"><img src="resources/img/logo.png" width="25" height="25" ></a>
		    </div>
		    <div class="collapse navbar-collapse" id="myNavbar">
		      <ul class="nav navbar-nav">
		        <li class="active"><a href="index">Home</a></li>
		      </ul>
		      <ul class="nav navbar-nav navbar-right">
		        <li><a href="registro"><span class="glyphicon glyphicon-log-in"></span> Registrarse</a></li>
		      </ul>
		    </div>
		  </div>
		</nav>
		  
		<div class="container-fluid text-center">    
		  <div class="row content">
		    <div class="col-sm-2 sidenav">
		    </div>
		    <div class="col-sm-8 text-center"> 
				<form method="post" action="index">
					<h2>Iniciar sesi&oacuten en Unicorn-World</h2>
					<label>Email</label></br>
					<input type=email placeholder="Introduce e-mail" name=email required /></br></br>
					<label>Contraseña</label></br>
					<input type="password" placeholder="Introduce contraseña" name="key" required></br></br>   
					<button type="submit">Entrar</button>
				</form>
		    </div>
		    <div class="col-sm-2 sidenav">
		    </div>
		  </div>
		</div>
		
		<footer class="container-fluid text-center">
		  <p>Online Unicorn-World Store Copyright &copy;</p>
		</footer>
	</body>
</html>
