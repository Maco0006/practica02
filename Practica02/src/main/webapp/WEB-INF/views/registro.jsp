<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>

	<head>
		<!-- configuración-->
		<title>Registro</title>
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
		    /* Add a gray background color and some padding to the footer */
		    footer {
		      background-color: #f2f2f2;
		      padding: 25px;
		    }
		    th, td{
		    	padding: 10px;
		    }
	 	</style>
	  
	  
	</head>

	
	<body onload="<c:if test="${err!=null}"> alert('${err}') </c:if>">
		<%! int puerto = 8080; %>
		<div class="container">
		  	<nav class="navbar navbar-inverse">
				  <div class="container-fluid">
				    <div class="navbar-header">
				      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>                        
				      </button>
				      <a href="index">
				      <img src="resources/img/logoflyunicorn.jpg" width="50" height="50">
				      </a>
				    </div>
				    <div class="collapse navbar-collapse" id="myNavbar">
				      <ul class="nav navbar-nav">
				        <li class="active"><a href="index">Home</a></li>
				      </ul>
				      <ul class="nav navbar-nav navbar-right">
				      </ul>
				    </div>
				  </div>
				</nav>

		</div>

		<div class="container">
			<div class="jumbotron" style="text-align:center">
				<form class="form-horizontal" action="registro" method=post>
				<h2>Formulario de registro:</h2><br/>
					<!-- Email -->
				    <div class="form-group">

				        <label class="control-label col-xs-3">Email:</label>				
				        <div class="col-xs-6">
	            			<input type="email" class="form-control" placeholder="Email" name=email id="inputEmail" required>  
				        </div>
				        <span id="msg2"> </span>
				    </div>

				    <!-- Contraseña -->
				    <div class="form-group">
				        <label class="control-label col-xs-3">Contraseña:</label>
				        <div class="col-xs-6">
				            <input type="password" class="form-control" placeholder="Password" name=key id="inputPassword" required>
				        </div>
				    </div>
				    <!-- Nombre -->
				    <div class="form-group">
				        <label class="control-label col-xs-3">Nombre:</label>        
				        <div class="col-xs-6">
				            <input type="text" class="form-control" placeholder="Nombre" name=name id="inputName" required>
				        </div>
				       <span id="msg1"> </span>
				    </div>
				    <!-- Apellidos -->
				    <div class="form-group">
				        <label class="control-label col-xs-3">Apellidos:</label>
				        <div class="col-xs-6">
				            <input type="text" class="form-control" placeholder="Apellidos" name=apellidos id="inputApellidos" required>
				        </div>
				    </div>
				    <!-- Telefono -->
				    <div class="form-group">
				        <label class="control-label col-xs-3" >Telefono:</label>
				        <div class="col-xs-6">
				        	<input type="tel" class="form-control" placeholder="Telefono" name=telf id="inputTelefono" required >
				        </div>
				    </div>
				    <!-- Direccion -->
				    <div class="form-group">
				        <label class="control-label col-xs-3">Dirección:</label>
				        <div class="col-xs-7">
				            <textarea rows="3" class="form-control"  placeholder="C/ o Avd." name=dir id="inputDireccion" required></textarea>
				        </div>
				    </div>
				    <!-- Terminos & Condiciones -->
				    <div class="form-group">
				        <div class="col-xs-offset-3 col-xs-6">
				            <label class="checkbox-inline">
				                <input type="checkbox" value="agree" required>  Accepto <a href="http://localhost:8080/practica02/terminos">Terminos y condiciones</a>.
				            </label>
				        </div>
				    </div>
				    <br>
				    <div class="form-group">
				        <div class="col-xs-offset-3 col-xs-6">
				        	<!-- Boton para efecutar el formulario -->
				            <input type="submit" class="btn btn-primary" value="Enviar">
				            <!-- Boton para limpiar el formulario -->
				            <input type="reset" class="btn btn-default" value="Limpiar">
				        </div>
				    </div>
				</form>
			</div>
		</div>
		
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