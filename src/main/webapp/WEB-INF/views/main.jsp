<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Index - FashionStore</title>
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/aos/aos.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/drift-zoom/drift-basic.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/css/main.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body class="index-page">

<header id="header" class="header position-relative">
    <!-- Top Bar -->
    <div class="top-bar py-2 d-none d-lg-block">
        <div class="container-fluid container-xl">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <div class="d-flex align-items-center gap-4">
                        <div class="top-bar-item d-flex align-items-center">
                            <i class="bi bi-telephone-fill me-2"></i>
                            <span>회사전화번호:</span>
                            <a href="" class="ms-1">010-4127-4820</a>
                        </div>
                        <div class="top-bar-item d-flex align-items-center">
                            <i class="bi bi-person-fill me-2"></i>
                            <span>대표자:</span>
                            <span class="ms-1">조민국</span>
                        </div>
                        <div class="top-bar-item d-flex align-items-center">
                            <i class="bi bi-geo-alt-fill me-2"></i>
                            <span>주소:</span>
                            <span class="ms-1">경기도 고양시 일산서구 일산2동</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Main Header -->
    <div class="main-header">
        <div class="container-fluid container-xl">
            <div class="d-flex py-3 align-items-center justify-content-between">
                <!-- 이미지+로고 묶음 -->
                <div class="d-flex align-items-center">
                    <img src="${pageContext.request.contextPath}/resources/images/ricecake.png" alt="떡 이미지" style="max-width:80px; height:auto; margin-right:18px;">
                    <a href="${pageContext.request.contextPath}/" class="logo d-flex align-items-center" style="text-decoration:none;">
                        <h1 class="sitename mb-0" style="font-size:2.2rem;">떡<span> 조아</span></h1>
                    </a>
                </div>
                <!-- Actions & Member Info -->
                <div class="header-actions d-flex align-items-center justify-content-end">
                    <!-- 회원 정보/충전금액 표시 -->
                    <c:choose>
                        <c:when test="${member != null}">
                            <div class="d-flex align-items-center ms-3" style="gap:8px;">
                                  <span class="fw-semibold">
                                    <i class="bi bi-person-circle me-1"></i>${member.memberId}
                                  </span>
                                  <span class="text-success fw-semibold">
                                    <i class="bi bi-currency-dollar"></i>
                                    <fmt:formatNumber value="${member.money}" pattern="#,###"/>원
                                  </span>
                                  <!-- a href="${pageContext.request.contextPath}/wishlist" class="header-action-btn d-none d-md-flex">
                                      <i class="bi bi-heart"></i>
                                      <span class="action-text d-none d-md-inline-block">Wishlist</span>
                                  </a-->
                                  <a href="${pageContext.request.contextPath}/member/cart/list" class="position-relative" style="display:inline-block;">
                                      <i class="bi bi-cart" style="font-size:1.7em; color:#222;"></i>
                                        <c:if test="${cartCount > 0}">
                                            <span id="cart-count-badge"
                                                  class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                                                  style="font-size:0.85em; min-width:22px; min-height:22px; display:flex; align-items:center; justify-content:center;">
                                                    ${cartCount}
                                            </span>
                                            장바구니
                                        </c:if>
                                 </a>
                                 <a href="/member/logout.do">로그아웃</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="d-flex align-items-center ms-3" style="gap:8px;">
                                <a href="/member/login" class="bi bi-person-circle me-1">로그인</a>
                                <a href="/member/join" class="bi bi-person-circle me-1">회원가입</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <!-- Navigation -->
    <div class="header-nav">
        <div class="container-fluid container-xl position-relative">
            <nav id="navmenu" class="navmenu">
                <c:choose>
                    <c:when test="${member != null && member.adminCk == 1}">
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/" class="active">Home</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/productRegister">상품등록</a></li>
                            <li><a href="${pageContext.request.contextPath}/productSelect">상품조회</a></li>
                            <li><a href="${pageContext.request.contextPath}/review">고객주문내역</a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/" class="active">Home</a></li>
                            <li><a href="${pageContext.request.contextPath}/productSelect">상품조회</a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </nav>
        </div>
    </div>
</header>

<main class="main">
    <jsp:include page="${contentPage}" />
</main>

<footer id="footer" class="footer light-background">
</footer>

<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/swiper/swiper-bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/aos/aos.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/isotope-layout/isotope.pkgd.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/glightbox/js/glightbox.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/assets/vendor/drift-zoom/Drift.min.js"></script>

<script>

    /* gnb_area 로그아웃 버튼 작동 */
    $("#gnb_logout_button").click(function(){
        //alert("버튼 작동");
        $.ajax({
            type:"POST",
            url:"/member/logout.do",
            success:function(data){
                alert("로그아웃 성공");
                document.location.reload();
            }
        });
    });


</script>
</body>
</html>

