<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Input</title>
</head>

<body>
	<h2>Enter Number of People</h2>
	
	<form action="listPeople.do">
		<input type="text" name="userCount"/>
		<input type="submit"/>
	</form>

</body>

</html>
