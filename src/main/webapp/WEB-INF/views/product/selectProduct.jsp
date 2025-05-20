<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Search Results - FashionStore</title>
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/assets/css/main.css" rel="stylesheet">
</head>
<body class="search-results-page">
<style>
    /* 5등분 그리드 */
    @media (min-width: 992px) {
        .col-lg-5th {
            flex: 0 0 20%;
            max-width: 20%;
        }
    }
    .product-card {
        padding: 10px 4px 10px 4px;
        border-radius: 8px;
        box-shadow: 0 1px 4px rgba(0,0,0,0.03);
        background: #fff;
        height: 100%;
        margin-bottom: 0;
    }
    .product-image {
        margin-bottom: 4px;
    }
    .main-image {
        width: 110px;
        height: 110px;
        object-fit: cover;
        display: block;
        margin: 0 auto;
        border-radius: 6px;
    }
    .product-title {
        margin: 4px 0 2px 0;
        font-size: 1em;
        font-weight: 500;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    .product-meta, .product-material, .product-stock {
        font-size: 0.92em;
        margin-bottom: 2px;
    }
    .product-price {
        color: #e74c3c;
        font-weight: 600;
        text-align: center !important;
        width: 100%;
        margin: 0 auto 2px auto;
        display: block;
    }
    .row.g-4 {
        --bs-gutter-x: 0.5rem;
        --bs-gutter-y: 0.5rem;
    }
</style>

<!-- ...헤더/네비게이션 등 기존 템플릿 코드 생략... -->

<main class="main">
    <!-- Search Product List Section -->
    <section id="search-product-list" class="search-product-list section">
        <div class="container">
            <!-- 검색 폼 중앙 배치 -->
            <div class="row justify-content-center mb-4">
                <div class="col-12 col-md-6">
                    <form class="search-form desktop-search-form" action="${pageContext.request.contextPath}/productSelect" method="get">
                        <div class="input-group">
                            <input type="text" class="form-control" name="q" value="${keyword}" placeholder="상품 검색...">
                            <button class="btn search-btn" type="submit">
                                <i class="bi bi-search"></i>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row g-4">
                <c:forEach var="product" items="${productList}">
                    <div class="col-6 col-md-4 col-lg-5th">
                        <div class="product-card text-center">
                            <div class="product-image">
                                <img src="${pageContext.request.contextPath}${product.productImage}" class="main-image img-fluid" alt="${product.productName}">
                            </div>
                            <div class="product-details">
                                <h4 class="product-title">
                                    <a href="#" style="color:#222; text-decoration:none;">${product.productName}</a>
                                </h4>
                                <div class="product-meta">
                                    <div class="product-price">
                                        <fmt:formatNumber value="${product.productPrice}" type="currency" currencySymbol="₩"/>
                                    </div>
                                </div>
                                <div class="product-material">재료: ${product.productIngredient}</div>
                                <div class="product-stock">수량: ${product.stockQuantity}</div>


                                <div class="mt-2 d-flex gap-2">
                                    <c:if test="${member != null && member.adminCk == 1}">
                                        <form action="${pageContext.request.contextPath}/admin/productUpdate" method="get" style="flex:1; margin:0;">
                                            <input type="hidden" name="productId" value="${product.productId}" />
                                            <button type="submit" class="btn btn-sm btn-outline-primary w-100 update-btn">수정</button>
                                        </form>
                                        <button type="button" class="btn btn-sm btn-outline-danger w-100 delete-btn" data-product-id="${product.productId}" style="flex:1;">삭제</button>
                                    </c:if>
                                    <c:if test="${member != null && member.adminCk != 1}">
                                        <form action="${pageContext.request.contextPath}/member/cart/add" method="post" style="display:inline;">
                                            <input type="hidden" name="productId" value="${product.productId}" />
                                            <input type="hidden" name="cartQuantity" value="1" />
                                            <button type="submit"
                                                    class="btn btn-xs btn-success px-2 py-1"
                                                    style="font-size:0.85em; min-width:60px;">
                                                <i class="bi bi-cart-plus"></i> 장바구니 담기
                                            </button>
                                        </form>
<%--                                        <form action="${pageContext.request.contextPath}/member/order/cart" method="get">--%>
<%--                                            <button type="submit" class="btn btn-primary">주문하기</button>--%>
<%--                                        </form>--%>
                                        <form action="${pageContext.request.contextPath}/member/order/direct" method="get">
                                            <input type="hidden" name="productId" value="${product.productId}">
                                            <input type="hidden" name="quantity" value="1"><!-- 또는 선택한 수량 -->
                                            <button type="submit" class="btn btn-primary">바로구매</button>
                                        </form>
                                    </c:if>
                                </div>

                            </div>
                        </div>
                    </div>
                </c:forEach>
                <!-- 다음 페이지 버튼 (forEach 바깥) -->
                <div class="text-center mt-4">
                    <c:forEach var="i" begin="1" end="${totalPage}">
                        <a href="?page=${i}<c:if test='${not empty keyword}'>&amp;q=${keyword}</c:if>"
                           class="btn btn-outline-secondary <c:if test='${i == currentPage}'>active</c:if>">
                                ${i}
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </section>
</main>
<script>
    $(document).ready(function(){
        $(".delete-btn").click(function(){
            if(confirm("정말 삭제하시겠습니까?")) {
                var productId = $(this).data("product-id");
                $.post("${pageContext.request.contextPath}/admin/productDelete", {productId: productId}, function(){
                    alert("삭제되었습니다.");
                    location.reload();
                }).fail(function(){
                    alert("삭제 중 오류가 발생했습니다.");
                });
            }
        });
    });


    $(document).ready(function(){
        $(".add-cart-btn").click(function(){
            var productId = $(this).data("product-id");
            var cartQuantity = $(this).data("cart-quantity");

            console.log(productId);
            console.log(cartQuantity);
            console.log(member.memberId);

            $.post("${pageContext.request.contextPath}/member/cart/add", {
                productId: productId,
                cartQuantity: cartQuantity
            }, function(response){
                if(response === "success") {
                    alert("장바구니에 담겼습니다!");
                    $("#cart-count-badge").text(response.cartCount);
                } else if(response === "not_logged_in") {
                    alert("로그인 후 이용해 주세요.");
                    location.href = "${pageContext.request.contextPath}/login";
                } else {
                    alert("장바구니 담기 실패!");
                }
            }).fail(function(){
                alert("장바구니 담기 실패!");
            });
        });
    });
</script>

<!-- 필수 JS만 남김 -->
<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<%--<script src="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.js"></script>--%>
</body>
</html>