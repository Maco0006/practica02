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
		            	<li class="dropdown"> <a href="productos" > Comprar mas </a> </li>
		            	<li class="dropdown"> <a href="perfil" >perfil</a> </li>                   
		            	<li class="dropdown"> <a href="logout" >logout</a> </li>
		            </ul>
		        </div>
		    </nav>
		</div>
		
		<!-- Como usar los parametros en jsp : Hola <c:out value = "${usuario.nombre},${usuario.apellidos}" />  qué tal.
				  ${usuario.getNombre()}   -->
		
		<div class="container">
			<p> Su carrito: </p>
			<ul>
				<div class="table-responsive">
		  			<table class="table table-bordered table-hover">
		    		    <thead>
					        <tr>
					            <th>Nombre</th>
					            <th>Descripcion</th>
					            <th>Precio (Euros)</th>
					            <th>Cantidad</th>
					        </tr>
					    </thead>
					    <tbody>
						    <c:forEach items="${lista}" var="n">
							 <tr>
							 	<td>${n.nombre}</td>
							 	<td>${n.concepto}</td>
							 	<td>${n.importe}</td>
							 	<td>${n.unidades}</td>
							 	<td>
							 		<a href="eliminar?name=${n.id}" 
									   class="btn btn-danger" role="button">
									   Eliminar
									</a>
							 	</td>
							 </tr>
							</c:forEach>
							<tr>
								<td colspan="2" >Total</td>
								<td> <c:out value =  "${total}" />	</td>
								<td  colspan="2">
									<form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" target="_top">
										<input type="hidden" name="cmd" value="_xclick">
										<input type="hidden" name="business" value="F5ZFQCDAJWKWC">
										<input type="hidden" name="lc" value="ES">
										<input type="hidden" name="item_name" value="buttonPaypal">
										<input type="hidden" name="item_number" value="buttonPaypal">
										<input type="hidden" name="amount" value="${total}">
										<input type="hidden" name="currency_code" value="EUR">
										<input type="hidden" name="button_subtype" value="services">
										<input type="hidden" name="no_note" value="0">
										<input type="hidden" name="cn" value="Añadir instrucciones especiales para el vendedor:">
										<input type="hidden" name="no_shipping" value="2">
										<input type="hidden" name="rm" value="1">
										<input type="hidden" name="return" value="http://localhost:8080/practica02/fin">
										<input type="hidden" name="cancel_return" value="http://localhost:8080/practica02/perfil">
										<input type="hidden" name="bn" value="PP-BuyNowBF:btn_buynowCC_LG.gif:NonHosted">
										<input type="image" src="https://www.sandbox.paypal.com/es_ES/ES/i/btn/btn_buynowCC_LG.gif" border="0" name="submit" alt="PayPal, la forma rápida y segura de pagar en Internet.">
										<img alt="" border="0" src="https://www.sandbox.paypal.com/es_ES/i/scr/pixel.gif" width="1" height="1">
									</form>
								</td>
							</tr>
		        		</tbody>
		  			</table>
				</div>
			</ul>
		</div>
		
	</body>
</html>