<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${not empty cartList}">
    <c:set var="cartSize" value="${fn:length(cartList)}" />
    <c:set var="firstProductName" value="${cartList[0].productName}" />
</c:if>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>주문서</title>
    <meta charset="utf-8">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function changeQty(diff, price) {
            var qtyInput = document.getElementById('quantity');
            var qty = parseInt(qtyInput.value) || 1;
            qty += diff;
            if (qty < 1) qty = 1;
            qtyInput.value = qty;

            // 합계 금액 동적 변경
            document.getElementById('totalPriceTd').innerText = (price * qty).toLocaleString() + "원";
            document.getElementById('hiddenQuantity').value = qty;
        }
    </script>
</head>
<body>
<div class="container py-5">
    <h2 class="mb-4 text-center">주문서 확인</h2>
    <form id="orderForm">
        <table class="table table-bordered align-middle">
            <thead>
            <tr>
                <th>이미지</th>
                <th>상품명</th>
                <th>수량</th>
                <th>가격</th>
                <th>합계</th>
            </tr>
            </thead>
            <tbody>
            <!-- 장바구니 주문 -->
            <c:if test="${not empty cartList}">
                <c:set var="totalPrice" value="0" />
                <c:forEach var="cart" items="${cartList}">
                    <tr>
                        <td>
                            <img src="${pageContext.request.contextPath}${cart.productImage}" alt="${cart.productName}" style="width:48px; height:48px; object-fit:cover; border-radius:8px;">
                        </td>
                        <td>${cart.productName}</td>
                        <td>${cart.cartQuantity}</td>
                        <td><fmt:formatNumber value="${cart.productPrice}" pattern="#,###"/>원</td>
                        <td>
                            <fmt:formatNumber value="${cart.productPrice * cart.cartQuantity}" pattern="#,###"/>원
                            <c:set var="totalPrice" value="${totalPrice + (cart.productPrice * cart.cartQuantity)}" />
                        </td>
                        <input type="hidden" name="productId" value="${cart.productId}">
                        <input type="hidden" name="quantity" value="${cart.cartQuantity}">
                    </tr>
                </c:forEach>
            </c:if>
            <!-- 바로구매 주문 -->
            <c:if test="${not empty product}">
                <tr>
                    <td>
                        <img src="${pageContext.request.contextPath}${product.productImage}" alt="${product.productName}" style="width:48px; height:48px; object-fit:cover; border-radius:8px;">
                    </td>
                    <td>${product.productName}</td>
                    <td>
                        <div class="input-group" style="width:120px; margin:auto;">
                            <button type="button" class="btn btn-outline-secondary" onclick="changeQty(-1, ${product.productPrice})">-</button>
                            <input type="text" name="quantity" id="quantity" class="form-control text-center" value="${quantity}" min="1" readonly>
                            <button type="button" class="btn btn-outline-secondary" onclick="changeQty(1, ${product.productPrice})">+</button>
                        </div>
                        <input type="hidden" id="hiddenQuantity" name="quantity" value="${quantity}">
                    </td>
                    <td><fmt:formatNumber value="${product.productPrice}" pattern="#,###"/>원</td>
                    <td id="totalPriceTd">
                        <fmt:formatNumber value="${product.productPrice * quantity}" pattern="#,###"/>원
                    </td>
                    <input type="hidden" name="productId" value="${product.productId}">
                </tr>
            </c:if>
            </tbody>
        </table>
        <div class="mb-4 text-end" style="font-size:1.3em; font-weight:bold;">
            총 결제금액:
            <span style="color:#2d6cdf;" id="finalTotalPrice">
                <c:choose>
                    <c:when test="${not empty cartList}">
                        <fmt:formatNumber value="${totalPrice}" pattern="#,###"/> 원
                    </c:when>
                    <c:when test="${not empty product}">
                        <fmt:formatNumber value="${product.productPrice * quantity}" pattern="#,###"/> 원
                    </c:when>
                </c:choose>
            </span>
        </div>
        <div class="mb-3">
            <label for="paymentMethod" class="form-label">결제수단</label>
            <select name="paymentMethod" id="paymentMethod" class="form-select" required>
                <option value="카드">카드</option>
                <option value="간편결제">간편결제</option>
            </select>
        </div>
        <div class="text-center">
            <button type="button" class="btn btn-primary btn-lg" id="payBtn">결제하기</button>
        </div>
    </form>
</div>
</body>
<script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script>
    function changeQty(diff, price) {
        var qtyInput = document.getElementById('quantity');
        var qty = parseInt(qtyInput.value) || 1;
        qty += diff;
        if (qty < 1) qty = 1;
        qtyInput.value = qty;

        document.getElementById('totalPriceTd').innerText = (price * qty).toLocaleString() + "원";
        document.getElementById('hiddenQuantity').value = qty;
        document.getElementById('finalTotalPrice').innerText = (price * qty).toLocaleString() + " 원";
    }

    //아임포트
    document.getElementById('payBtn').onclick = function() {
        var IMP = window.IMP;
        IMP.init("imp11067471");

        var isCart = "${not empty cartList}";
        var totalPrice = "${totalPrice}";
        var amount, productName, qty;

        if (isCart === "true") {
            // 장바구니 결제
            amount = totalPrice;
            var cartSize = ${cartList != null ? cartList.size() : 0};
            var firstProductName = "${cartList[0].productName}";
            productName = cartSize > 1
                ? firstProductName + " 외 " + (cartSize - 1) + "건"
                : firstProductName;
            qty = 1;
        } else {
            // 단일상품 결제
            productName = "${product.productName}";
            qty = document.getElementById('hiddenQuantity').value;
            amount = "${product.productPrice}" * qty;
        }

        var buyerEmail = "${member.memberMail}";
        var buyerName = "${member.memberName}";

        var payMethodKor = document.getElementById('paymentMethod').value;
        var payMethod = (payMethodKor === "카드") ? "card" : "kakaopay";

        IMP.request_pay({
            pg: "html5_inicis",
            pay_method: payMethod,
            name: productName,
            amount: amount,
            buyer_email: buyerEmail,
            buyer_name: buyerName
        }, function(rsp) {
            if (rsp.success) {
                var form = document.getElementById('orderForm');
                var inputImpUid = document.createElement('input');
                inputImpUid.type = 'hidden';
                inputImpUid.name = 'imp_uid';
                inputImpUid.value = rsp.imp_uid;
                form.appendChild(inputImpUid);

                // ★ 장바구니/단일상품 분기
                if (isCart === "true") {
                    form.action = "${pageContext.request.contextPath}/member/order/cart";
                } else {
                    form.action = "${pageContext.request.contextPath}/member/order/submit";
                }
                form.method = "post";
                form.submit();
            } else {
                alert("결제에 실패하였습니다: " + rsp.error_msg);
            }
        });
    };
</script>
</html>
