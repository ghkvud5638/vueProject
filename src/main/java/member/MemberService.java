package member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// UserDetailsService 인터페이스를 구현하면서 반드시 구현해야하는 부분이다.
	// UserDetailsService 를 구현하게 되면, 스프링 인증 계정과 내 DB 의 Account 객체가 연동된다. (사용자 계정 DB 와 연동.)
	// 즉 loadUserByUsername 를 이렇게 연동할 수 있게끔 코딩해야 하는데, 그냥 이같이 해주면 된다.
	@Override
	public UserDetails loadUserByUsername(String inputEmail) throws UsernameNotFoundException {
		System.out.println("email : "+inputEmail);
		//회원 이름으로 회원을 조회 한다.
		Member member = memberRepository.findByEmail(inputEmail);	
		System.out.println("member : "+member);
		
//		log.info("memberRepository.findByEmail here "+member);
		//회원정보 권한에 따라서 권한을 부여한다.
		List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		if(("admin@email.com").equals(inputEmail)) {
			auth.add(new SimpleGrantedAuthority("ADMIN"));
		}else {
			auth.add(new SimpleGrantedAuthority("USER"));
		}
		
		return new User(member.getEmail(), member.getPassword(), auth); // user 정보 , email&password&auth
	}
	
	//회원을 저장한다. 
	public Member save(Member member) {
//		System.out.println("memberService"+member);
		//비밀번호 암호화
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		return memberRepository.save(member);
	}
	
	public UserDetails login(Member member, HttpServletResponse response) throws UsernameNotFoundException {
		System.out.println("member : "+member);
		 Member m = memberRepository.findByEmail(member.getEmail());
			List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
			if(("admin@email.com").equals(m.getEmail())) {
				auth.add(new SimpleGrantedAuthority("ADMIN"));
			}else {
				auth.add(new SimpleGrantedAuthority("USER"));
			}

			return new User(m.getEmail(), m.getPassword(), auth); // user 정보 , email&password&auth
	}
	

}
