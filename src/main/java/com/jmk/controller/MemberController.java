package com.jmk.controller;


import java.util.Random;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmk.model.MemberVO;
import com.jmk.service.MemberService;

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
	public String joinPOST(MemberVO member) throws Exception {
		
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
	
	/*이메일 인증 */
	
	/*@RequestMapping(value = "/mailCheck", method = RequestMethod.GET)
	@ResponseBody
	public void mailCheckGET(String email) throws Exception {
		
		 뷰(view)로부터 넘어온 데이터 확인 
		logger.info("이메일 데이터 전송 확인");
		logger.info("인증번호 : " + email);
		
		인증번호(난수) 생성 
		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;
		logger.info("인증번호" + checkNum);
	}
	
	
	@RequestMapping(value = "/sendMail", method = RequestMethod.GET) 
	public void sendMailTest() throws Exception {
		
		String subject = "test 메일";
		String content = "하이 조민국" + "<img src=\"https://t1.daumcdn.net/cfile/tistory/214DCD42594CC40625\">";
		String from = "whalsrnraos@naver.com";
		String to = "whalsrnraos@naver.com";
		
		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper mailHelper = new MimeMessageHelper(mail,true,"UTF-8");
			// true는 멀티파트 메세지를 사용하겠다는 의미
			
			
             /* 단순한 텍스트 메세지만 사용시엔 아래의 코드도 사용 가능 
             * MimeMessageHelper mailHelper = new MimeMessageHelper(mail,"UTF-8");
             *//*
             * 단순한 텍스트 메세지만 사용시엔 아래의 코드도 사용 가능 
             * MimeMessageHelper mailHelper = new MimeMessageHelper(mail,"UTF-8");
             
			
			mailHelper.setFrom(from);
			// 빈에 아이디 설정한 것은 단순히 smtp 인증을 받기 위해 사용 따라서 보내는이(setFrom())반드시 필요
            // 보내는이와 메일주소를 수신하는이가 볼때 모두 표기 되게 원하신다면 아래의 코드를 사용하시면 됩니다.
            //mailHelper.setFrom("보내는이 이름 <보내는이 아이디@도메인주소>");
			
			mailHelper.setTo(to);
			mailHelper.setSubject(subject);
			mailHelper.setText(content, true);
			
			// true는 html을 사용하겠다는 의미입니다.
            
            
             * 단순한 텍스트만 사용하신다면 다음의 코드를 사용하셔도 됩니다. mailHelper.setText(content);
             
			
			FileSystemResource file = new FileSystemResource(new File("D:\\test.txt"));
			mailHelper.addAttachment("test.txt", file );
			mailSender.send(mail);
			
		
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//MimeMesagePreparator를 사용해서 메일을 전송하는 방법
	@Test
	public void mailSendTest2() throws Exception {
		
		String subject = "test 메일";
		String content = "하이 조민국" + "<img src=\"https://t1.daumcdn.net/cfile/tistory/214DCD42594CC40625\">";
		String from = "whalsrnraos@naver.com";
		String to = "whalsrnraos@naver.com";
		
		try {
            final MimeMessagePreparator preparator = new MimeMessagePreparator() {
                
                public void prepare(MimeMessage mimeMessage) throws Exception{
                    final MimeMessageHelper mailHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    
                    mailHelper.setFrom(from);
                    mailHelper.setTo(to);
                    mailHelper.setSubject(subject);
                    mailHelper.setText(content, true);
                    
                    FileSystemResource file = new FileSystemResource(new File("D:\\test.txt")); 
                    mailHelper.addAttachment("test.txt", file);
                    
                }
                
            };
            
            mailSender.send(preparator);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
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
		String setFrom = "whalsrnraos@gmail.com";
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
    public String loginPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr) throws Exception{
        
        //System.out.println("login 메서드 진입");
        //System.out.println("전달된 데이터 : " + member);
    	
    	HttpSession session = request.getSession();
    	String rawPw = "";
    	String encodePw = "";
    	
    	MemberVO lvo = memberservice.memberLogin(member);  // 제출한아이디와 일치하는 아이디있는지 
    	
    	if(lvo != null) {  // 일치하는 아이디 존재시
    		rawPw = member.getMemberPw();        // 사용자가 제출한 비밀번호
            encodePw = lvo.getMemberPw();        // 데이터베이스에 저장한 인코딩된 비밀번호
            
            if(true == pwEncoder.matches(rawPw, encodePw)) {        // 비밀번호 일치여부 판단
            	
            	lvo.setMemberPw("");                    // 인코딩된 비밀번호 정보 지움
                session.setAttribute("member", lvo);     // session에 사용자의 정보 저장
                return "redirect:/main";        // 메인페이지 이동
            } 
    		else {
    			rttr.addFlashAttribute("result", 0);            
                return "redirect:/member/login";    // 로그인 페이지로 이동
            }
    	}
    	else {  // 일치하는 아이디가 존재하지 않을 시 (로그인 실패)
    		rttr.addAttribute("result",0);
    		return "redirect:/member/login";    // 로그인 페이지로 이동
    		
    		
    	}
    	/*MemberVO lvo = memberservice.memberLogin(member);
    	
    	 if(lvo == null) {                                // 일치하지 않는 아이디, 비밀번호 입력 경우
             
             int result = 0;
             rttr.addFlashAttribute("result", result);
             return "redirect:/member/login";
             
         }
         
         session.setAttribute("member", lvo);            // 일치하는 아이디, 비밀번호 경우 (로그인 성공)
         
         return "redirect:/main";*/ 
        
    }
	
    /* 메인페이지 로그아웃 */
    @RequestMapping(value="logout.do", method=RequestMethod.GET)
    public String logoutMainGET(HttpServletRequest request) throws Exception{
    	 
    	logger.info("logoutMainGET메서드 진입");
         
         HttpSession session = request.getSession();
         
         session.invalidate();
         
         return "redirect:/main";   
    }
 



}