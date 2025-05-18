<!-- filepath: /member/join.jsp (예시) -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>dssa-FashionStore</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 기존 템플릿 CSS/JS 경로 유지 -->
	<link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/assets/css/main.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member/join.css">
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body class="register-page">

<main class="main">

	<!-- ...중략... -->
	<main class="main">

		<div class="text-center my-4">
			<img src="${pageContext.request.contextPath}/resources/images/ricecake.png" alt="떡 이미지" style="max-width:200px;">
		</div>

		<!-- Register Section -->
		<section id="register" class="register section">

			<div class="container" data-aos="fade-up" data-aos-delay="100">

				<div class="row justify-content-center">
					<div class="col-lg-6">

						<div class="registration-form-wrapper" data-aos="zoom-in" data-aos-delay="200">

							<div class="section-header mb-4 text-center">
								<h2>떡 조아</h2>
								<p>회원가입</p>
							</div>

							<form action="#" method="POST">
								<div class="row">
									<!-- 아이디 -->
									<div class="col-12 mb-3">
										<div class="form-group">
											<label for="memberId">아이디</label>
											<input type="text" class="form-control id_input" name="memberId" id="memberId" required>
											<span class="id_input_re_1 text-success" style="display:none;">사용 가능한 아이디입니다.</span>
											<span class="id_input_re_2 text-danger" style="display:none;">아이디가 이미 존재합니다.</span>
											<span class="final_id_ck text-danger" style="display:none;">아이디를 입력해주세요.</span>
										</div>
									</div>
									<!-- 비밀번호 -->
									<div class="col-12 mb-3">
										<div class="form-group">
											<label for="memberPw">비밀번호</label>
											<input type="password" class="form-control pw_input" name="memberPw" id="memberPw" required>
											<span class="final_pw_ck text-danger" style="display:none;">비밀번호를 입력해주세요.</span>
										</div>
									</div>
									<!-- 비밀번호 확인 -->
									<div class="col-12 mb-3">
										<div class="form-group">
											<label for="pwck_input">비밀번호 확인</label>
											<input type="password" class="form-control pwck_input" id="pwck_input" required>
											<span class="final_pwck_ck text-danger" style="display:none;">비밀번호 확인을 입력해주세요.</span>
											<span class="pwck_input_re_1 text-success" style="display:none;">비밀번호가 일치합니다.</span>
											<span class="pwck_input_re_2 text-danger" style="display:none;">비밀번호가 일치하지 않습니다.</span>
										</div>
									</div>
									<!-- 이름 -->
									<div class="col-12 mb-3">
										<div class="form-group">
											<label for="memberName">이름</label>
											<input type="text" class="form-control user_input" name="memberName" id="memberName" required>
											<span class="final_name_ck text-danger" style="display:none;">이름을 입력해주세요.</span>
										</div>
									</div>

									<div class="form-group mb-3">
										<label for="memberMail">이메일</label>
										<div class="input-group">
											<input type="email" class="form-control mail_input" name="memberMail" id="memberMail" required placeholder="이메일 입력">
											<button type="button" class="btn btn-primary w-100 mail_check_button" style="min-width:80px;">전송</button>
										</div>
										<span class="final_mail_ck text-danger" style="display:none;">이메일을 입력해주세요.</span>
										<span class="mail_input_box_warn text-danger" style="display:none;"></span>
										<div class="input-group mt-2">
											<input type="text" class="form-control mail_check_input" placeholder="인증번호 입력" disabled>
										</div>
										<span id="mail_check_input_box_warn" class="text-danger" style="display:none;"></span>
									</div>
									<!-- 주소 -->
									<div class="col-12 mb-3">
										<div class="form-group">
											<label>주소</label>
											<div class="input-group mb-2">
												<input class="form-control address_input_1" name="memberAddr1" readonly="readonly" placeholder="우편번호">
												<button type="button" class="btn btn-primary w-100 address_button" style="min-width:100px;" onclick="execution_daum_address()">주소 찾기</button>
											</div>
											<input class="form-control address_input_2 mb-2" name="memberAddr2" readonly="readonly" placeholder="기본주소">
											<input class="form-control address_input_3" name="memberAddr3" readonly="readonly" placeholder="상세주소">
											<div class="d-flex align-items-center gap-2 mt-2">
												<div class="d-flex align-items-center gap-2 mt-2">
                                                <span class="final_addr_ck text-danger ms-2" style="display:none; font-size:0.97em;">
                                            주소를 입력해주세요.
                                            </span>
												</div>
											</div>
										</div>
									</div>
									<div class="text-center mb-4">
										<button type="submit" class="btn btn-primary w-100">회원가입</button>
									</div>
									<div class="text-center">
										<p class="mb-0">이미 계정이 있으신가요? <a href="/member/login">로그인</a></p>
									</div>
								</div>
							</form>
						</div>

					</div>
				</div>
			</div>
		</section>

</main>

<!-- 기존 템플릿 푸터 등 유지 -->

<!-- Daum 주소 API -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- 기존 회원가입 script 붙여넣기 -->
<script>
	var code = "";

	/* 유효성 검사 통과유무 변수 */
	var idCheck = false;            // 아이디
	var idckCheck = false;            // 아이디 중복 검사
	var pwCheck = false;            // 비번
	var pwckCheck = false;            // 비번 확인
	var pwckcorCheck = false;        // 비번 확인 일치 확인
	var nameCheck = false;            // 이름
	var mailCheck = false;            // 이메일
	var mailnumCheck = false;        // 이메일 인증번호 확인
	var addressCheck = false         // 주소

	$(document).ready(function(){
		//회원가입 버튼(회원가입 기능 작동)
		$(".join_button").click(function(){

			/* 입력값 변수 */
			var id = $('.id_input').val();                 // id 입력란
			var pw = $('.pw_input').val();                // 비밀번호 입력란
			var pwck = $('.pwck_input').val();            // 비밀번호 확인 입력란
			var name = $('.user_input').val();            // 이름 입력란
			var mail = $('.mail_input').val();            // 이메일 입력란
			var addr = $('.address_input_3').val();        // 주소 입력란

			/* 아이디 유효성검사 */
			if(id == ""){
				$('.final_id_ck').css('display','block');
				idCheck = false;
			}else{
				$('.final_id_ck').css('display', 'none');
				idCheck = true;
			}


			/* 비밀번호 유효성 검사 */
			if(pw == ""){
				$('.final_pw_ck').css('display','block');
				pwCheck = false;
			}else{
				$('.final_pw_ck').css('display', 'none');
				pwCheck = true;
			}

			/* 비밀번호 확인 유효성 검사 */
			if(pwck == ""){
				$('.final_pwck_ck').css('display','block');
				pwckCheck = false;
			}else{
				$('.final_pwck_ck').css('display', 'none');
				pwckCheck = true;
			}


			/* 이름 유효성 검사 */
			if(name == ""){
				$('.final_name_ck').css('display','block');
				nameCheck = false;
			}else{
				$('.final_name_ck').css('display', 'none');
				nameCheck = true;
			}


			/* 이메일 유효성 검사 */
			if(mail == ""){
				$('.final_mail_ck').css('display','block');
				mailCheck = false;
			}else{
				$('.final_mail_ck').css('display', 'none');
				mailCheck = true;
			}

			/* 주소 유효성 검사 */
			if(addr == ""){
				$('.final_addr_ck').css('display','block');
				addressCheck = false;
			}else{
				$('.final_addr_ck').css('display', 'none');
				addressCheck = true;
			}

			/* 최종 유효성 검사 */
			if(idCheck&&idckCheck&&pwCheck&&pwckCheck&&pwckcorCheck&&nameCheck&&mailCheck&&mailnumCheck&&addressCheck ){

				$("#join_form").attr("action", "/member/join");
				$("#join_form").submit();


			}
			return false;
		});
	});

	//아이디 중복검사
	$('.id_input').on("propertychange change keyup paste input", function(){

		//console.log("keyup 테스트");
		var memberId = $('.id_input').val(); // .id_input에 입력되는 값
		var data = {memberId : memberId}	 // '컨트롤에 넘길 데이터 이름' : '데이터(.id_input에 입력되는 값)'

		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/member/memberIdChk",
			data : data,
			success : function(result){
				//console.log("성공 여부" + result);
				if(result != 'fail'){
					$('.id_input_re_1').css("display","inline-block");
					$('.id_input_re_2').css("display", "none");
					// 아이디 중복이 없는 경우
					idckCheck = true;
				} else {
					$('.id_input_re_2').css("display","inline-block");
					$('.id_input_re_1').css("display", "none");
					// 아이디 중복된 경우
					idckCheck = false;
				}

			}// success 종료
		}); // ajax 종료

	});// function 종료


	// 인증번호 전송 버튼 클릭
	$(".mail_check_button").click(function () {
		const email = $(".mail_input").val();
		const codeInput = $(".mail_check_input");
		const warnMsg = $(".mail_input_box_warn");
		const mailCk = $(".final_mail_ck");

		// 이메일 입력값 유효성 검사
		if (email.trim() === "") {
			mailCk.show();
			warnMsg.hide();
			return false;
		} else {
			mailCk.hide();
		}

		// 이메일 형식 검사
		if (!mailFormCheck(email)) {
			warnMsg.text("올바르지 못한 이메일 형식입니다.").show();
			return false;
		} else {
			warnMsg.text("이메일이 전송 되었습니다. 이메일을 확인해주세요.").show();
		}

		// 인증번호 요청
		$.ajax({
			type: "GET",
			url: "mailCheck?email=" + encodeURIComponent(email),
			success: function (data) {
				codeInput.prop("disabled", false).val("");
				code = data;
			},
			error: function () {
				warnMsg.text("이메일 전송에 실패했습니다. 다시 시도해주세요.").show();
			}
		});
	});

	// 인증번호 입력 유효성 검사 (포커스 아웃 시)
	$(".mail_check_input").on("blur", function () {
		const inputCode = $(this).val();
		const $warn = $("#mail_check_input_box_warn");

		if (inputCode === "") {
			$warn.text("인증번호를 입력해주세요.").removeClass("text-success").addClass("text-danger").show();
			mailnumCheck = false;
			return;
		}

		if (inputCode === code) {
			$warn.text("인증번호가 일치합니다.").removeClass("text-danger").addClass("text-success").show();
			mailnumCheck = true;
		} else {
			$warn.text("인증번호를 다시 확인해주세요.").removeClass("text-success").addClass("text-danger").show();
			mailnumCheck = false;
		}
	});


	/* 다음 주소 연동 */
	function execution_daum_address(){
		new daum.Postcode({
			oncomplete: function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.

				// 각 주소의 노출 규칙에 따라 주소를 조합한다.
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수

				//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}

				// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
				if(data.userSelectedType === 'R'){
					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
						extraAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if(data.buildingName !== '' && data.apartment === 'Y'){
						extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
					}
					// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
					if(extraAddr !== ''){
						extraAddr = ' (' + extraAddr + ')';
					}
					// 조합된 참고항목을 해당 필드에 넣는다.
					// 주소변수 문자열과 참고항목 문자열 합치기
					addr += extraAddr;
				} else {
					document.getElementById("sample6_extraAddress").value = '';
				}
				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				$(".address_input_1").val(data.zonecode);
				//$("[name=memberAddr1]").val(data.zonecode);    // 대체가능
				$(".address_input_2").val(addr);
				//$("[name=memberAddr2]").val(addr);            // 대체가능
				// 상세주소 입력란 disabled 속성 변경 및 커서를 상세주소 필드로 이동한다.
				$(".address_input_3").attr("readonly",false);
				$(".address_input_3").focus();


			}
		}).open();

	}

	/* 비밀번호 확인 일치 유효성 검사 */

	$('.pwck_input').on("propertychange change keyup paste input", function(){
		var pw = $('.pw_input').val();
		var pwck = $('.pwck_input').val();
		$('.final_pwck_ck').css('display', 'none');

		if(pw == pwck){
			$('.pwck_input_re_1').css('display','block');
			$('.pwck_input_re_2').css('display','none');
			pwckcorCheck = true;
		}else{
			$('.pwck_input_re_1').css('display','none');
			$('.pwck_input_re_2').css('display','block');
			pwckcorCheck = false;
		}


	});

	/* 입력 이메일 형식 유효성 검사 */
	function mailFormCheck(email){
		var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		return form.test(email);
	}
</script>
</main>
</body>
</html>