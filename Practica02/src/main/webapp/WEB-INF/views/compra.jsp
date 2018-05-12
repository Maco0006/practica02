<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<!-- configuración-->
		<meta charset="utf-8">
		<!-- Para que se vea en dispositivos moviles -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- Estilos de Boostrap -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  		
  		<style>
    		/* Remove the navbar's default rounded borders and increase the bottom margin */ 
		    .navbar {
		      margin-bottom: 50px;
		      border-radius: 0;
		    }    
		    /* Remove the jumbotron's default bottom margin */ 
		     .jumbotron {
		      margin-bottom: 0;
		    }
		    /* Add a gray background color and some padding to the footer */
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
	            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand" href="index"> <img src="resources/img/logoflyunicorn.jpg" width="50" height="50" > </a>
        	</div>
	        <div class="collapse navbar-collapse" id="myNavbar">
	        	<ul class="nav navbar-nav">
        			<li class="active"><a href="index">Home</a></li>
	   			</ul>  
	   			<ul class="nav navbar-nav navbar-right">
        			<li><a href="bienvenido"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
        			<li><a href="carrito"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
      			</ul> 		
	        </div>
	    </nav>
		
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