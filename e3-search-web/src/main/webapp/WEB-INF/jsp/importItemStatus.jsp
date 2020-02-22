<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>数据导入情况</title>
</head>
<body>

    <c:if test="${status ==1}">
        <h1 style="color: green;">数据导入成功！！！</h1>
        <a href="http://192.168.25.130:8080/solr/#/collection1/query">请查看</a>
    </c:if>
    <c:if test="${status ==0}">
        <h1 style="color: red;">数据导入失败！！！</h1>
        <a href="http://localhost:8480/importItem.action">请重试</a>
    </c:if>
    

</body>
</html>