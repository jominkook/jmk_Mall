package com.jmk.controller.member;


import java.util.Random;

import com.jmk.vo.member.Member;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmk.service.member.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberservice;
	

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private BCryptPasswordEncoder pwEncoder;
	
	
	
	//회원가입 페이지 이동
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public void loginGET() {
		logger.info("회원가입 페이지 진입");
	}
	
	//회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPOST(Member member) throws Exception {
		
		//logger.info("join 진입");
		
		//회원가입 서비스 실행
		//memberservice.memberJoin(member);
		
		//logger.info("join Service 성공");
		
		String rawPw = "";            // 인코딩 전 비밀번호
        String encodePw = "";        // 인코딩 후 비밀번호
        
        rawPw = member.getMemberPw();            // 비밀번호 데이터 얻음
        encodePw = pwEncoder.encode(rawPw);        // 비밀번호 인코딩
        member.setMemberPw(encodePw);            // 인코딩된 비밀번호 member객체에 다시 저장
        
        /* 회원가입 쿼리 실행 */
        memberservice.memberJoin(member);
		
		return "redirect:/main";
	}
	
	 /* 비동기방식 로그아웃 메서드 */
    @RequestMapping(value="logout.do", method=RequestMethod.POST)
    @ResponseBody
    public void logoutPOST(HttpServletRequest request) throws Exception{
        
        logger.info("비동기 로그아웃 메서드 진입");
        
        HttpSession session = request.getSession();
        
        session.invalidate();
        
    }
	
	//로그인 페이지 이동
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void joinGET() {
			
		logger.info("로그인 페이지 진입");
	
	}
	
	//아이디 중복검사
	@RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception {
		
		//logger.info("memberIdChk() 진입");
		
		logger.info("memberIdChk() 진입");
		
		int result = memberservice.idCheck(memberId);
		
		logger.info("결과값 = " + result);
		
		if(result != 0) {
			
				return "fail"; //중복 아이디 존재
		
		} else {
			
				return "success"; //중복 아이디 x
		}
	} // memberIdChkPOST() 종료
	
	/*이메일 인증*/
	@RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
	@ResponseBody
	public String mailCheckGET(String email) throws Exception {
		/*뷰(view)로부터 넘어온 데이터 확인 */
		logger.info("이메일 데이터 전송 확인");
		logger.info("인증번호 :" + email);
		
		/*인증번호(난수) 생성*/
		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;
		logger.info("인증번호" + checkNum);
		
		/*이메일 보내기 */
		String setFrom = "whaksenaos@naver.com";
		String toMail = email;
		String title = "회원가입 인증 이메일 입니다";
		String content = 
				"홈페이지를 방문해주셔서 감사합니다."+
				"<br><br>" +
				"인증 번호는" + checkNum + "입니다." +
				"<br>" +
				"해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
		
		try {
		            
		            MimeMessage message = mailSender.createMimeMessage();
		            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		            helper.setFrom(setFrom);
		            helper.setTo(toMail);
		            helper.setSubject(title);
		            helper.setText(content,true);
		            mailSender.send(message);
		            
		        }catch(Exception e) {
		            e.printStackTrace();
		  
		      }
				
			  String num = Integer.toString(checkNum);
			  
			  return num;
		}
	
    /* 로그인 */
    @RequestMapping(value="login", method=RequestMethod.POST)
    public String loginPOST(HttpServletRequest request, @ModelAttribute Member member, RedirectAttributes rttr) throws Exception{
        
        //System.out.println("login 메서드 진입");
        //System.out.println("전달된 데이터 : " + member);
    	
    	HttpSession session = request.getSession();
    	String rawPw = "";
    	String encodePw = "";

    	Member lvo = memberservice.memberLogin(member);

		if (lvo != null) {
			rawPw = member.getMemberPw(); // 사용자가 입력한 비밀번호
			encodePw = lvo.getMemberPw(); // 데이터베이스에서 가져온 암호화된 비밀번호

			logger.info("rawPw: " + rawPw);
			logger.info("encodePw: " + encodePw);

			if (encodePw != null && pwEncoder.matches(rawPw, encodePw)) {
				lvo.setMemberPw(""); // 비밀번호 정보 제거
				session.setAttribute("member", lvo); // 세션에 사용자 정보 저장
				return "redirect:/main"; // 메인 페이지로 이동
			} else {
				rttr.addFlashAttribute("result", 0);
				return "redirect:/member/login"; // 로그인 페이지로 이동
			}
		} else {
			rttr.addFlashAttribute("result", 0);
			return "redirect:/member/login"; // 로그인 페이지로 이동
		}
        
    }
    /* 메인페이지 로그아웃 */
    @RequestMapping(value="logout.do", method=RequestMethod.GET)
    public String logoutMainGET(HttpServletRequest request) throws Exception{
    	 
    	logger.info("logoutMainGET메서드 진입");
         
         HttpSession session = request.getSession();
         
         session.invalidate();

		return "redirect:/member/login";
    }

}
