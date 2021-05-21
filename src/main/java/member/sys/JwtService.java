package member.sys;

public interface JwtService {
	/** accece token */
    <T> String create(String key, T data, String subject);
    String get(String key);
    String getMemberId();
    
    /** refresh token */
    <T> String createRefreshToken(String key, T data, String subject);
    boolean isUsable(String jwt);
	boolean isRefreshUsable(String jwt);
	String refreshGet(String key);
	String getRefreshMemberId();
}
