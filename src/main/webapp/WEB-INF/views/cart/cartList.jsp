<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>장바구니</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/css/main.css" rel="stylesheet">
    <style>
        .cart-table { background: #fff; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.04); }
        .cart-img { width: 48px; height: 48px; object-fit: cover; border-radius: 8px; border:1px solid #eee; }
        .delete-btn { background: none; border: none; color: #e74c3c; font-size: 1.5em; cursor: pointer; }
        .delete-btn:hover { color: #c0392b; }
        .cart-product-name { font-weight: 500; font-size: 1.05em; }
        .cart-empty { text-align: center; color: #888; padding: 40px 0; }
    </style>
</head>
<body>
<main class="main">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <h2 class="mb-4 text-center">장바구니</h2>
                <div class="table-responsive">
                    <table class="table align-middle cart-table">
                        <thead>
                        <tr>
                            <th scope="col">이미지</th>
                            <th scope="col">상품명</th>
                            <th scope="col">수량</th>
                            <th scope="col">가격</th>
                            <th scope="col">합계</th>
                            <th scope="col">삭제</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="totalPrice" value="0" />
                        <c:forEach var="cart" items="${cartList}">
                            <tr>
                                <td>
                                    <img src="${pageContext.request.contextPath}${cart.productImage}" alt="${cart.productName}" class="cart-img"/>
                                </td>
                                <td class="cart-product-name">${cart.productName}</td>
                                <td>${cart.cartQuantity}</td>
                                <td><fmt:formatNumber value="${cart.productPrice}" pattern="#,###"/>원</td>
                                <td><fmt:formatNumber value="${cart.productPrice * cart.cartQuantity}" pattern="#,###"/>원</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/member/cart/delete" method="post" style="margin:0;">
                                        <input type="hidden" name="cartId" value="${cart.cartId}" />
                                        <button type="submit" class="delete-btn" title="삭제">
                                            <i class="bi bi-dash-circle"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        <c:set var="totalPrice" value="${totalPrice + (cart.productPrice * cart.cartQuantity)}" />
                        </c:forEach>
                        <c:if test="${empty cartList}">
                            <tr>
                                <td colspan="6" class="cart-empty">장바구니가 비어 있습니다.</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- 총 주문금액 & 주문하기 버튼 -->
            <div class="row justify-content-center mt-4">
                <div class="col-lg-6 text-center">
                    <div class="row justify-content-center mt-4">
                        <div class="col-lg-6">
                            <div class="card shadow-sm border-0">
                                <div class="card-body text-center">
                                    <div class="mb-2" style="font-size:1.3em; color:#555;">
                                        <i class="bi bi-currency-won" style="font-size:1.5em; color:#2d6cdf; vertical-align:-0.1em;"></i>
                                        <span style="font-weight:600;">총 주문금액</span>
                                    </div>
                                    <div style="font-size:2em; font-weight:bold; color:#2d6cdf;">
                                        <fmt:formatNumber value="${totalPrice}" pattern="#,###"/> 원
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <form id="cartOrderForm" action="${pageContext.request.contextPath}/member/order/cart" method="get">
                        <button type="submit" class="btn btn-primary btn-lg w-100" style="font-size:1.2em">
                            주문하기
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>