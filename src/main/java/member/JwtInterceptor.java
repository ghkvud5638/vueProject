package member;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JwtService jwtService;
	
	
//	컨트롤러(즉 RequestMapping이 선언된 메서드 진입) 실행 직전에 동작.
//	반환 값이 true일 경우 정상적으로 진행이 되고, false일 경우 실행이 멈춥니다.(컨트롤러 진입을 하지 않음)
//	전달인자 중 Object handler는 핸들러 매핑이 찾은 컨트롤러 클래스 객체입니다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		System.out.println("여기 JwtInterceptor");
		System.out.println(request.getMethod()+" : "+ request.getServletPath());
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}else {
			//request의 parameter에서 auth_token으로 넘어온 녀석을 찾아본다
//			String token = request.getParameter("auth-token");
			String token = request.getHeader("jwt-auth-token");
			System.out.println("토큰 : "+token);
			if (token != null && token.length() > 0) {
				jwtService.checkValid(token); //유효한 토큰인지
				log.trace("토큰사용 가능 : {}", token);
				return true;
			}else {
//				throw new RuntimeException("인증토큰이 없습니다.");
				log.trace("토큰사용 불가 : {}", token);
				return false;
			}
		}
	}

}
