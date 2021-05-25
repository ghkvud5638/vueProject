package member;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtService {
	
	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${jwt.expmin}")
	private Long expireMin;
	
	//로그인 성공시 사용자 정보를 기반으로 jwttoken을 생성하여 반환
	public String create(final Member member) {
		final JwtBuilder builder = Jwts.builder();
		builder.setHeaderParam("typ", "JWT");//토큰의 타입, 고정값
		
		builder.setSubject("로그인 완료")
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))//유효기간
				.claim("Member", member).claim("second", "더 담고싶은거 있어?");//담고싶은 정보
		
		//signature - seceret key를 이용한 암호화
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		
		final String jwt = builder.compact();
		return jwt;
	}
	
	//전달받은 토큰이 제대로 생성된것인지 확인, 문제가 있다면 Runtime 예외
	public void checkValid(final String jwt) {
		Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
	}
	
	//jwt 토큰을 분석해서 필요한 정보를 반환
	public Map<String,Object> get(final String jwt) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return claims.getBody();
	}
	
	
	
}
