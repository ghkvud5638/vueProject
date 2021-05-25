package member;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import member.board.Board;
import member.board.BoardRepository;

@SpringBootApplication
public class Application implements WebMvcConfigurer{
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("여기 addInterceptors");
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/detailVue/**") // 적용 경로
			.excludePathPatterns(Arrays.asList("/**","/board/**")); //제외 경로
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		System.out.println("여기 addCorsMappings");
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*")
			.allowedHeaders("*")
			.exposedHeaders("jwt-auth-token");
	}
	
	
	
    @Bean
    public CommandLineRunner initData(BoardRepository boardRepository, MemberRepository memberRepository) {
        return args -> 
            IntStream.rangeClosed(1, 154).forEach(i -> {
            	Member user =  Member.builder()
            			.email("ghkvud@naver.com")
                        .name("userId" + i)
                        .password("5638")
                        .build();

                memberRepository.save(user);
                
                Board b = Board.builder()
                        .boardTitle("test" + i)
                        .boardContent("content"+i)
                        .writer(user)
                        .build(); 

                boardRepository.save(b); 
            });
    }
}
