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

		<!--  JQuery -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

	</head>

	
	<body onload="<c:if test="${err!=null}"> alert('${err}') </c:if>">
		<%! int puerto = 8080; %>
		<div class="container">
		  	<nav role="navigation" class="navbar navbar-default">
		        <div class="navbar-header">
		            <a href="index" class="navbar-brand"> <img src="resources/img/logo.jpg" width="25" height="25"  alt="logo"/> </a>
		        </div>
		    </nav>
		</div>

		<div class="container">
			<div class="jumbotron" style="text-align:center">
				<form class="form-horizontal" action="registro" method=post>
					<!-- Email -->
				    <div class="form-group">

				        <label class="control-label col-xs-3">Email:</label>				
				        <div class="col-xs-6">
	            			<input type="email" class="form-control" placeholder="Email" name=email id="inputEmail" value="andres@pablo" required>  
				        </div>
				        <span id="msg2"> </span>
				    </div>

				    <!-- Contraseña -->
				    <div class="form-group">
				        <label class="control-label col-xs-3">Password:</label>
				        <div class="col-xs-6">
				            <input type="password" class="form-control" placeholder="Password" name=key id="inputPassword" value="1234" required>
				        </div>
				    </div>
				    <!-- Nombre -->
				    <div class="form-group">
				        <label class="control-label col-xs-3">Nombre:</label>        
				        <div class="col-xs-6">
				            <input type="text" class="form-control" placeholder="Nombre" name=name id="inputName" value="Andres" required >
				        </div>
				       <span id="msg1"> </span>
				    </div>
				    <!-- Apellidos -->
				    <div class="form-group">
				        <label class="control-label col-xs-3">Apellidos:</label>
				        <div class="col-xs-6">
				            <input type="text" class="form-control" placeholder="Apellidos" name=apellidos id="inputApellidos" value="Pablo" required>
				        </div>
				    </div>
				    <!-- Telefono -->
				    <div class="form-group">
				        <label class="control-label col-xs-3" >Telefono:</label>
				        <div class="col-xs-6">
				        	<input type="tel" class="form-control" placeholder="Telefono" name=telf id="inputTelefono" value="12356789" required >
				        </div>
				    </div>
				    <!-- Direccion -->
				    <div class="form-group">
				        <label class="control-label col-xs-3">Dirección:</label>
				        <div class="col-xs-7">
				            <textarea rows="3" class="form-control"  placeholder="C/ o Avd." name=dir id="inputDireccion" value="C/ Martinez, Ubeda (JAEN)" required></textarea>
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
