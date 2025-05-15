<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>판매상품 수정</title>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/assets/css/main.css" rel="stylesheet">
</head>
<body>
<main class="main">
  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-lg-6">
        <div class="registration-form-wrapper shadow p-4 rounded bg-white">
          <div class="section-header mb-4 text-center">
            <h2>판매상품 수정</h2>
            <p>수정할 판매상품 정보를 입력하세요.</p>
          </div>
          <form id="productForm" action="${pageContext.request.contextPath}/admin/productUpdate" method="post" enctype="multipart/form-data">
            <input type="hidden" id="productId" name="productId" value="${product.productId}" />

            <div class="mb-3">
              <label for="productName" class="form-label">상품명</label>
              <input type="text" class="form-control" id="productName" name="productName" required value="${product.productName}">
            </div>
            <div class="mb-3">
              <label for="productPrice" class="form-label">가격</label>
              <input type="number" class="form-control" id="productPrice" name="productPrice" required min="0" value="${product.productPrice}">
            </div>
            <div class="mb-3">
              <label for="stockQuantity" class="form-label">재고수량</label>
              <input type="number" class="form-control" id="stockQuantity" name="stockQuantity" required min="0" value="${product.stockQuantity}">
            </div>
            <div class="mb-3">
              <label for="categoryId" class="form-label">카테고리</label>
              <select class="form-select" id="categoryId" name="categoryId" required>
                <option value="">카테고리 선택</option>
                <c:forEach var="cat" items="${categoryList}">
                  <option value="${cat.categoryId}" <c:if test="${cat.categoryId == product.categoryId}">selected</c:if>>${cat.categoryName}</option>
                </c:forEach>
              </select>
            </div>
            <div class="mb-3">
              <label for="productIngredient" class="form-label">떡 재료</label>
              <input type="text" class="form-control" id="productIngredient" name="productIngredient" required value="${product.productIngredient}">
            </div>
            <div class="mb-3">
              <label class="form-label">현재 이미지</label><br>
              <c:if test="${not empty product.productImage}">
                <img src="${pageContext.request.contextPath}${product.productImage}" alt="상품 이미지" style="max-width:120px; max-height:120px; margin-bottom:8px;">
              </c:if>
            </div>
            <div class="mb-3">
              <label for="uploadFile" class="form-label">이미지 변경</label>
              <input type="file" class="form-control" id="uploadFile" name="uploadFile" accept="image/*">
              <small class="text-muted">이미지를 변경하지 않으면 기존 이미지가 유지됩니다.</small>
            </div>
            <div class="mb-3">
              <label for="productStatus" class="form-label">상품 상태</label>
              <select class="form-select" id="productStatus" name="productStatus" required>
                <option value="판매중" <c:if test="${product.productStatus == '판매중'}">selected</c:if>>판매중</option>
                <option value="품절" <c:if test="${product.productStatus == '품절'}">selected</c:if>>품절</option>
                <option value="판매중단" <c:if test="${product.productStatus == '판매중단'}">selected</c:if>>판매중단</option>
              </select>
            </div>
            <div class="text-center mb-4">
              <button type="submit" class="btn btn-primary w-100 submit_button">상품 수정</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</main>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  $(document).ready(function(){
    $(".submit_button").click(function(e){
      e.preventDefault();



      // 값 읽기
      var productId = $('#productId').val();
      var productName = $('#productName').val();
      var productPrice = $('#productPrice').val();
      var stockQuantity = $('#stockQuantity').val();
      var categoryId = $('#categoryId').val();
      var productIngredient = $('#productIngredient').val();
      var productImage = $('#uploadFile').val();
      var productStatus = $('#productStatus').val();

      // productId 검사 (수정폼일 때만)
      if (!productId || isNaN(productId)) {
        alert("상품 ID가 올바르지 않습니다.");
        return false;
      }

      // 유효성 검사
      if (productName.trim() === "") {
        alert("상품명을 입력하세요.");
        $('#productName').focus();
        return false;
      }
      if (productPrice === "" || isNaN(productPrice) || Number(productPrice) < 0) {
        alert("가격을 올바르게 입력하세요.");
        $('#productPrice').focus();
        return false;
      }
      if (stockQuantity === "" || isNaN(stockQuantity) || Number(stockQuantity) < 0) {
        alert("재고수량을 올바르게 입력하세요.");
        $('#stockQuantity').focus();
        return false;
      }
      if (categoryId === "") {
        alert("카테고리를 선택하세요.");
        $('#categoryId').focus();
        return false;
      }

      if (productIngredient.trim() === "") {
        alert("떡 재료를 입력하세요.");
        $('#productIngredient').focus();
        return false;
      }

      if (productImage === "") {
        alert("이미지를 선택하세요.");
        $('#uploadFile').focus();
        return false;
      }

      if (productStatus === "") {
        alert("상품 상태를 선택하세요.");
        $('#productStatus').focus();
        return false;
      }


      // 모든 검사 통과 시 폼 전송
      $("#productForm").submit();
    });
  });
</script>
</html>