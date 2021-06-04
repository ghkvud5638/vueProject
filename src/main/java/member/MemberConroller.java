package member;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberConroller {
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberService member;
	
	@Autowired
	private JwtService jwtService;
	
//	@Autowired
//	private MemberRepository memberRepository;

	@GetMapping("/")
	public String index() {
		return "/index";
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "/hello";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	@GetMapping("/signUp")
	public String signUp() {
		return "/signUp";
	}
	
	@PostMapping("/create")
	public String create(Member member) {
		log.debug("member : "+ member);
		member.setDate(LocalDate.now());
		this.member.save(member);
		return "redirect:/login";
	}
	
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	@RequestMapping("/signUp")
	public void signUp(@RequestBody Member member) {
		System.out.println("member : "+member);
		member.setDate(LocalDate.now());
		this.member.save(member);
	}
	
	@RequestMapping("/loginVue")
	public ResponseEntity<Map<String,Object>> login(@RequestBody Member member, HttpServletResponse res) {
		System.out.println("loginVue try : "+member);
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
        	Member loginUser = this.member.login(member.getEmail(),member.getPassword());
        	//로그인 성공하면 코튼 생성
        	String token = jwtService.create(loginUser);
        	System.out.println("토큰 : "+token);
        	
        	//토큰정보는 request의 헤더로 보내고 나머지는 Map에 담음
        	res.setHeader("jwt-auth-token", token);
        	
        	resultMap.put("status", true);
        	resultMap.put("data", loginUser);
        	status = HttpStatus.ACCEPTED;
        }catch(RuntimeException e){
        	log.error("로그인 실패 ",e);
        	resultMap.put("message", e.getMessage());
        	status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@RequestMapping("/infoVue")
	public ResponseEntity<Map<String,Object>> getInfo(@RequestBody Member member, HttpServletRequest req) {
		System.out.println("infoVue try : "+member);
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus status = null;
//        Member m = memberRepository.findByEmail(member.getEmail());
        try {
			String info = this.member.getServerInfo();
			resultMap.putAll(jwtService.get(req.getHeader("jwt-auth-token")));
        	resultMap.put("status", true);
        	resultMap.put("info", info);
        	resultMap.put("request_body", member);
        	status = HttpStatus.ACCEPTED;        	
		} catch (RuntimeException e) {
			log.error("정보조회 실패 ",e);
        	resultMap.put("message", e.getMessage());
        	status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
        return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
}
