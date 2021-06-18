package member.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AuthorityService {
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public AuthorityService(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}
	
	
	public List<Authority> selectAuth() {
		return authorityRepository.findAll();
	}

}
