package member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class MemberConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	//.csrf().csrfTokenRepository(new CookieCsrfTokenRepository())
            .authorizeRequests() // 인증이 필요한 모든 요청에 대해
	            .antMatchers("/admin/**").hasRole("ADMIN")	//Admin 권한이 있는 경우 접근 허용
	            .antMatchers("/user/**").hasRole("USER")	//User 권한이 있는 경우 접근 허용
                .antMatchers("/", "/home", "/signUp", "/signIn", "/create","/board","/board/**","/search","/search/**","/js/**","/commentApply","/commentDelete","/comment/**","/vue","/vue/**","/boardVue","/toast-ui/**","/tui-grid/**","/detailVue/**","/detailVue","/loginVue").permitAll() //해당 URL은 전체 접근 허용
                .anyRequest().authenticated()	//이외의 URL은 인증 절차를 수행하기 위해 login 페이지로 이동
                .and().csrf().disable()
//            .formLogin() // formLogin을 사용할 것이다.
//                .loginPage("/login")
////                .usernameParameter("username")//아이디 파라미터명 설정
////                .passwordParameter("password")//패스워드 파라미터명 설정
//                //.failureUrl("/login.html?error=true")// 로그인 실패 후 이동 페이지
//                //.successHandler(loginSuccessHandler())//로그인 성공 후 핸들러 (해당 핸들러를 생성하여 핸들링 해준다.)
//                //.failureHandler(loginFailureHandler());//로그인 실패 후 핸들러 (해당 핸들러를 생성하여 핸들링 해준다.)
//                .permitAll()
//                .and()
//                
//            	.cors()
//            	.and()
//            	.csrf()
//            	.disable() 
//            	
//            .logout()
//            .logoutSuccessUrl("/")//로그아웃 성공 후 이동페이지
//            //.deleteCookies("JSESSIONID","remember-me")//로그아웃 후 쿠키 삭제
//                .permitAll()
                //.and()
            //.sessionManagement() //세션 관리 기능이 작동함
            //.invalidSessionUrl("/login")//세션이 유효하지 않을 떄 이동 할 페이지
            //.maximumSessions(1)//최대 허용 가능 세션 수, (-1: 무제한)
            //.maxSessionsPreventsLogin(true)//동시 로그인 차단함, false: 기존 세션 만료(default)
//            .expiredUrl("/expired");//세션이 만료된 경우 이동할 페이지
            ;
   
    }
	
//    // CORS 허용 적용
//    @Bean 
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
	@Bean
	public CorsConfigurationSource corsConfigurationSource(){
	  System.out.println("----------------cors config-----------------------");

	  CorsConfiguration configuration = new CorsConfiguration();

	  configuration.addAllowedOrigin("*");
	  configuration.addAllowedMethod("*");
	  configuration.addAllowedHeader("*");
	  configuration.setAllowCredentials(true);
	  configuration.setMaxAge(3600L);
	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	  source.registerCorsConfiguration("/**", configuration);

	  System.out.println("----------------cors config end-----------------------");
	  return source;
	}
	
	//암호화를 해주는 PasswordEncoder 클래스	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//인증 계정 정보에 들어가는 패스워드는 모두 인코딩이 된 형태로 DB 에 들어가야한다.
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	//로그인 여부 & 권한 여부 확인
	@Bean
	public SpringSecurityDialect springSecurityDialect(){
	    return new SpringSecurityDialect();
	}
	
}