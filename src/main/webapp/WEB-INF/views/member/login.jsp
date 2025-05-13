<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Login - FashionStore</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 기존 CSS 및 부트스트랩 링크 유지 -->
	<link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/assets/css/main.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body class="login-page">

<main class="main">
	<div class="text-center my-4">
		<img src="${pageContext.request.contextPath}/resources/images/ricecake.png" alt="떡 이미지" style="max-width:200px;">
	</div>

	<section id="login" class="login section">
		<div class="container" data-aos="fade-up" data-aos-delay="100">
			<div class="row justify-content-center">
				<div class="col-lg-5 col-md-8" data-aos="zoom-in" data-aos-delay="200">
					<div class="login-form-wrapper">
						<div class="login-header text-center">
							<h2>떡 좋아</h2>
							<p>로그인</p>
						</div>

						<form id="login_form" method="post" action="${pageContext.request.contextPath}/member/login">
							<div class="mb-4">
								<label for="memberId" class="form-label">아이디</label>
								<input type="text" class="form-control" id="memberId" name="memberId" placeholder="Enter your Id" required autocomplete="current-id">
							</div>
							<div class="mb-3">
								<div class="d-flex justify-content-between">
									<label for="memberPw" class="form-label">패스워드</label>
									<a href="#" class="forgot-link">Forgot password?</a>
								</div>
								<input type="password" class="form-control" id="memberPw" name="memberPw" placeholder="Enter your password" required autocomplete="current-password">
							</div>
							<c:if test="${result == 0}">
								<div class="login_warn text-danger mb-3">사용자 ID 또는 비밀번호를 잘못 입력하셨습니다.</div>
							</c:if>
							<div class="mb-4 form-check">
								<input type="checkbox" class="form-check-input" id="remember">
								<label class="form-check-label" for="remember">Remember for 30 days</label>
							</div>
							<button type="submit" class="btn btn-primary w-100 login_button mb-3">로그인</button>
							<button type="button" class="btn btn-secondary join_button">회원가입</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
</main>

<script>
	// 로그인 버튼 클릭 시 폼 제출
	$(".login_button").click(function(){
		$("#login_form").submit();
	});

	//조인 버튼
	$(".join_button").click(function(){
		window.location.href = "/member/join";
	});
</script>
</body>
</html>