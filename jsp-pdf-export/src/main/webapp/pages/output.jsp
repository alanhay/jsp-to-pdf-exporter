<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>OUTPUT</title>
</head>

<body>
	<table>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.id}</td>
				<td>${user.forename}</td>
				<td>${user.surname}</td>
			</tr>
		</c:forEach>
	</table>

	<br />
	<c:if test="${! param.pdf}">
		<a href="listPeople.do?${pdfExportUrl}">Export as PDF</a>
	</c:if>
	<br />
	<br />
</body>
</html>
