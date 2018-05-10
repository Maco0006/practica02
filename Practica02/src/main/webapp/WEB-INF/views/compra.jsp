<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
		            	<li class="dropdown"> <a href="perfil" >perfil</a>  </li>
		            	<li class="dropdown"><a href="mostrarCarrito" > Carrito </a> </li>
						<li class="dropdown"><a href="logout" >logout</a> </li>
						
		            </ul>
		        </div>
		    </nav>
		</div>
		
		<div class="container">
			<form method="post" action="carrito">
				<c:forEach items="${lista}" var="n">
					<div class="panel panel-primary table-responsive col-xs-12 col-md-6 ">
	  					<div class="panel-heading"><h3>	<c:out value = "${n.nombre}"/> </h3></div>
	  						<div class="panel-body">
			    				<table>
									<tr>
								  		<td rowspan="2"> <img src="${n.imagen}" alt="imagen" width="225" height="225" title="${n.nombre}" alt="${n.nombre}" ></td>
								  		<td ><c:out value = "${n.concepto}" /></td>
									</tr>
								 
									<tr>
								  		<td> Unidades: </td>
								  		<td> <input type="text" class="form-control col-md-4 col-sm-4 col-xs-4" placeholder="${n.unidades}" name="uni" value="0" /> </td>
									</tr>	
								
									<tr>
								  		<td> Precio: </td>
								  		<td> <c:out value =  "${n.importe}" /> &euro; </td>
								  		<td> <p> <input type="checkbox" name="opcion" value="${n.id}" />Incluir </p> </td>
									</tr>
								</table>
	  					</div>
					</div>

				</c:forEach>
				<p class="text-center col-xs-12">
					<button type="submit" class="btn btn-primary">Comprar</button>
				</p>
			</form>
		</div>
	</body>
</html>