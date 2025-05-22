<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>주문 내역 조회</title>
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <style>
        .order-table { background: #fff; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.04); }
        .order-img { width: 48px; height: 48px; object-fit: cover; border-radius: 8px; border:1px solid #eee; }
        .order-status { font-weight: 600; }
        .order-memo { color: #888; font-size: 0.95em; }
        .order-empty { text-align: center; color: #888; padding: 40px 0; }
    </style>
</head>
<body>
<main class="main">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <h2 class="mb-4 text-center">주문 내역 조회</h2>
                <div class="table-responsive">
                    <table class="table align-middle order-table">
                        <thead>
                        <tr>
                            <th scope="col">주문상품번호</th>
                            <th scope="col">회원명</th>
                            <th scope="col">상품이미지</th>
                            <th scope="col">상품명</th>
                            <th scope="col">상품가격</th>
                            <th scope="col">주문메모</th>
                            <th scope="col">주문상태</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty orderList}">
                            <tr>
                                <td colspan="7" class="order-empty">주문 내역이 없습니다.</td>
                            </tr>
                        </c:if>
                        <c:forEach var="order" items="${orderList}">
                            <tr>
                                <td>${order.memberOrderProductId}</td>
                                <td>${order.memberName}</td>
                                <td>
                                    <img src="${order.productImage}" alt="상품이미지" class="order-img">
                                </td>
                                <td>${order.productName}</td>
                                <td>
                                    <fmt:formatNumber value="${order.productPrice}" pattern="#,###"/>원
                                </td>
                                <td class="order-memo">${order.memberOrderMemo}</td>
                                <td class="order-status">${order.memberOrderStatus}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${!isAdmin && order.memberOrderStatus eq '주문접수'}">
                                            <form action="${pageContext.request.contextPath}/order/updateStatus" method="post" style="display:inline;">
                                                <input type="hidden" name="memberOrderProductId" value="${order.memberOrderProductId}" />
                                                <input type="hidden" name="memberOrderStatus" value="주문취소요청" />
                                                <button type="submit" class="btn btn-outline-warning btn-sm">주문취소 요청</button>
                                            </form>
                                        </c:when>

                                        <c:when test="${!isAdmin && order.memberOrderStatus eq '주문취소요청'}">
                                            <span class="text-warning">주문취소요청</span>
                                        </c:when>

                                        <c:when test="${isAdmin && order.memberOrderStatus eq '주문접수'}">
                                            <form action="${pageContext.request.contextPath}/order/updateStatus" method="post" style="display:inline;" onsubmit="return showApproveAlert();">
                                                <input type="hidden" name="memberOrderProductId" value="${order.memberOrderProductId}" />
                                                <input type="hidden" name="memberOrderStatus" value="주문승인" />
                                                <button type="submit" class="btn btn-outline-primary btn-sm">주문승인</button>
                                            </form>
                                        </c:when>

                                        <c:when test="${isAdmin && order.memberOrderStatus eq '주문취소요청'}">
                                            <form action="${pageContext.request.contextPath}/order/updateStatus" method="post" style="display:inline;" onsubmit="return showCancelApproveAlert();">
                                                <input type="hidden" name="memberOrderProductId" value="${order.memberOrderProductId}" />
                                                <input type="hidden" name="memberOrderStatus" value="주문취소" />
                                                <input type="hidden" name="quantity" value="${order.quantity}" />
                                                <input type="hidden" name="productId" value="${order.productId}" />
                                                <button type="submit" class="btn btn-outline-danger btn-sm">주문취소 승인</button>
                                            </form>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
<script>
    function showApproveAlert() {
        alert('주문이 승인되었습니다.');
        return true; // 폼 submit 진행
    }
    function showCancelApproveAlert() {
        alert('주문취소가 승인되었습니다.');
        return true;
    }
</script>
</html>