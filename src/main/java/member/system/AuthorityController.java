package member.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthorityController {
	
	@Autowired
	AuthorityService authorityService; 
	
	@RequestMapping("/api/auth/search")
	public List<Authority> authSearch(@RequestBody String isUse, String authSys, String  authGroupName) {
		System.out.println(":::::: authSearch");
		log.debug(" "+isUse, authSys, authGroupName);
		List<Authority> list = authorityService.selectAuth();
		System.out.println(":::::: 조회결과");
		System.out.println(list);
		return list;
	}

}
