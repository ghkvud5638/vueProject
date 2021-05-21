package member;

import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberService member;
	
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
	public void login(@RequestBody Member member, HttpServletResponse response, Authentication authentication) {
	}
	
	
}
