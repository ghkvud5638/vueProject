package member;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "MEMBER")
public class Member {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_id")
	private long seq;
	
	@NotEmpty
	@Column(name = "userId")
	private String email;
	
	@NotEmpty
	private String password;
	
	private String name;
	
	@Column(name = "RegDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	
	
	
	
	@Builder
	public Member(@NotEmpty String email, @NotEmpty String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}
}
