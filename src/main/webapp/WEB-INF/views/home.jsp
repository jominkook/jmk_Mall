<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
	<style>
		body {
			background: url('${pageContext.request.contextPath}/resources/images/background.jpg') no-repeat center center fixed;
			background-size: cover;
			margin: 0;
			padding: 0;
			height: 100vh;
		}
		.center-message {
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			background: rgba(255,255,255,0.7);
			padding: 40px 60px;
			border-radius: 16px;
			text-align: center;
			font-size: 2em;
			font-weight: bold;
			color: #333;
			box-shadow: 0 4px 16px rgba(0,0,0,0.08);
		}
	</style>
</head>
<body>
<div class="center-message">
	떡 조아를 찾아주셔서 감사합니다!
	우리 떡 조아는 고객들의 만족을 위해 항상 최선을 다하고 있습니다.
	좋은 재료와 숙련된 장인의 손맛이 담긴 떡을 가족들과 함께 즐기세요.
	단체주문 환영합니다!!!<br>
</div>
</body>
</html>