<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/editMember.css">
<style>
	td{border: 1px solid black; width: 300px; height: 300px;}
	.item-img{width:100px; height: 100px; background-color: yellow;}
</style>
</head>
<body>
	<!-- 로고 -->
	<a href="${pageContext.request.contextPath}/home">
	    <img src="${pageContext.request.contextPath}/resources/images/캠핑가로고1.png" alt="Logo">
	</a>
	
	<!-- 상품카테고리 선택 -->
	<!-- 임의.location.href에 값을 설정하면 해당 값으로 페이지가 이동(리다이렉트).브라우저으이 현재 위치를 가져오거나 설정할 떄 사용. -->
	<select name="category" onchange="location.href='${pageContext.request.contextPath}/list?category=' + this.value">
		<option value="all">전체상품</option>
		<option value="campingfurniture">캠핑가구</option>
		<option value="cooking">조리도구</option>
		<option value="light">조명/랜턴</option>
		<option value="electronics">전자기기</option>
		<option value="tents">텐트</option>
		<option value="sleeping">칭남</option>
		<option value="mat">매트</option>
		<option value="fireplace">난로</option>
	</select>
	<table>
	
		<tr>
		<c:forEach var="item" items="${itemList}">
			<td>
				<div class="item-img">item_img: (추가예정)</div>
				<p><c:out value="${item.item_name}" /></p>
				<p><c:out value="${item.price}" /></p>			
			</td>
		</c:forEach>
		</tr>
	</table>
	<table border="1" width="90%">
		<td>이전 1 2 3 4 5 6 7 8 9 10 다음</td>
	</table>
</body>
</html>