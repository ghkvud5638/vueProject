package member;

import java.util.Date;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import member.board.Board;
import member.board.BoardRepository;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
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
