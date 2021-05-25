//package member.sys;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import io.jsonwebtoken.ClaimJwtException;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.PrematureJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.UnsupportedJwtException;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Service("jwtService")
//public class JwtServiceImpl implements JwtService {
//
///** access token info start */
//	// access token key
//	@Value("${jwt.auth.tokenSecret}")
//    private String tokenSecret;
//	
//	// access token time
//	@Value("${jwt.auth.tokenExpirationMsec}")
//	private Long tokenExpirationMsec;
//	
///** accece token info finish */
//	
///** refresh token info start */
//	//refresh token key
//	@Value("${jwt.auth.refreshTokenSecret}")
//	private String refreshTokenSecret;
//	
//	// refresh token time
//	@Value("${jwt.auth.refreshTokenExpirationMsec}")
//	private Long refreshTokenExpirationMsec;
//	
///** refresh token info finish */
//
//    /**
//     * access token create
//     */
//    @Override
//    public <T> String create(String key, T data, String subject){
//        String jwt = Jwts.builder()
//                .setHeaderParam("typ", "ACCESS")
//                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationMsec))
//                .setSubject(subject)
//                .claim(key, data)
//                .signWith(SignatureAlgorithm.HS256, this.generateKey())
//                .compact();
//        return jwt;
//    }
//    
//    
//    /**
//     * refresh token create
//     */
//    public <T> String createRefreshToken(String key, T data, String subject) {
//        String jwt = Jwts.builder()
//                .setHeaderParam("typ", "REFRESH")
//                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMsec))
//                .setSubject(subject)
//                .claim(key, data)
//                .signWith(SignatureAlgorithm.HS256, this.generateRefreshKey())
//                .compact();
//        return jwt;
//    }
//
//    
//    /**
//     * @desc 		: access key generate
//     * @methodName	: generateKey
//     * @author		: kihong park
//     * @date 		: 2020.08.25
//     * @return
//     */
//    private byte[] generateKey(){
//    	
//        byte[] key = null;
//        try {
//            key = tokenSecret.getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            if(log.isInfoEnabled()){
//            	log.error("{}", e);
//            }else{
//                log.error("Making JWT Key Error ::: {}", e.getMessage());
//            }
//        }
//
//        return key;
//    }
//    
//    /**
//     * @desc 		: refresh key generate
//     * @methodName	: generateKey
//     * @author		: kihong park
//     * @date 		: 2020.08.25
//     * @return
//     */
//    private byte[] generateRefreshKey(){
//        byte[] key = null;
//        try {
//            key = refreshTokenSecret.getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            if(log.isInfoEnabled()){
//            	log.error("{}", e);
//            }else{
//                log.error("Making JWT Key Error ::: {}", e.getMessage());
//            }
//        }
//
//        return key;
//    }
//
//    /**
//     * access token valide check
//     */
//    @Override
//    public boolean isUsable(String jwt) {
//    	@SuppressWarnings("unused")
//		Jws<Claims> claims = null;
//        try {
//        	
//            claims = Jwts.parser()
//                    .setSigningKey(this.generateKey())
//                    .parseClaimsJws(jwt);
//            
//            return true;
//        } catch(ExpiredJwtException eje) {
//            /**
//             * 유효 기간이 지난 JWT를 수신한 경우
//             */
//            log.error("##### 유효기간 만료 #####");
//            return false;
//        } catch(UnsupportedJwtException uje) {
//            /**
//             * 수신한 JWT의 형식이 애플리케이션에서 원하는 형식과 맞지 않는 경우. 예를 들어, 암호화된 JWT를 사용하는 애프리케이션에 암호화되지 않은 JWT가 전달되는 경우에 이 예외가 발생합니다.
//             */
//            log.error("##### 수신한 JWT의 형식 오류 #####");
//            return false;
//        } 
////        catch(SignatureException se) {
////            /**
////             * 시그너처 연산이 실패하였거나, JWT의 시그너처 검증이 실패한 경우
////             */
////            log.error("##### 시그너처 연산이 실패 #####");
////            return false;
////        } 
//        catch(PrematureJwtException pje) {
//            /**
//             * 접근이 허용되기 전인 JWT가 수신된 경우
//             */
//            log.error("##### 접근이 허용 오류 #####");
//            return false;
//        } catch (MalformedJwtException mje) {
//            /**
//             * 구조적인 문제가 있는 JWT인 경우
//             */
//            log.error("##### JWT 구조적인 문제 #####");
//            return false;
//        } catch (ClaimJwtException cje) {
//            /**
//             * JWT 권한claim 검사가 실패했을 때
//             */
//            log.error("##### claim 검사 실패 #####");
//            return false;
//        } catch (Exception e) {
//            log.error("##### Exception #####");
//            return false;
////            if(log.isInfoEnabled()){
////                e.printStackTrace();
////            }else{
////                log.error(e.getMessage());
////            }
////            throw new UnauthorizedException();
//
//            /*개발환경!!!
//             * return false;*/
//
//        }
//    }
//    
//    @Override
//    public boolean isRefreshUsable(String jwt) {
//    	@SuppressWarnings("unused")
//		Jws<Claims> claims = null;
//        try {
//        	claims = Jwts.parser()
//                    .setSigningKey(this.generateRefreshKey())
//                    .parseClaimsJws(jwt);
//            return true;
//        } catch(ExpiredJwtException eje) {
//            /**
//             * 유효 기간이 지난 JWT를 수신한 경우
//             */
//            log.error("##### 유효기간 만료 #####");
//            return false;
//        } catch(UnsupportedJwtException uje) {
//            /**
//             * 수신한 JWT의 형식이 애플리케이션에서 원하는 형식과 맞지 않는 경우. 예를 들어, 암호화된 JWT를 사용하는 애프리케이션에 암호화되지 않은 JWT가 전달되는 경우에 이 예외가 발생합니다.
//             */
//            log.error("##### 수신한 JWT의 형식 오류 #####");
//            return false;
//        } 
////        catch(SignatureException se) {
////            /**
////             * 시그너처 연산이 실패하였거나, JWT의 시그너처 검증이 실패한 경우
////             */
////            log.error("##### 시그너처 연산이 실패 #####");
////            return false;
////        } 
//        catch(PrematureJwtException pje) {
//            /**
//             * 접근이 허용되기 전인 JWT가 수신된 경우
//             */
//            log.error("##### 접근이 허용 오류 #####");
//            return false;
//        } catch (MalformedJwtException mje) {
//            /**
//             * 구조적인 문제가 있는 JWT인 경우
//             */
//            log.error("##### JWT 구조적인 문제 #####");
//            return false;
//        } catch (ClaimJwtException cje) {
//            /**
//             * JWT 권한claim 검사가 실패했을 때
//             */
//            log.error("##### claim 검사 실패 #####");
//            return false;
//        } catch (IllegalArgumentException ie) {
//            log.error("##### token null #####");
//            return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("##### Exception #####");
//            return false;
////            if(log.isInfoEnabled()){
////                e.printStackTrace();
////            }else{
////                log.error(e.getMessage());
////            }
////            throw new UnauthorizedException();
//
//            /*개발환경!!!
//             * return false;*/
//
//        }
//    }
//
//    @Override
//    public String get(String key) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String jwt = request.getHeader("Authorization");
//        Jws<Claims> claims = null;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(this.generateKey())
//                    .parseClaimsJws(jwt);
//        } catch (Exception e) {
//            if(log.isInfoEnabled()){
//                e.printStackTrace();
//            }else{
//                log.error(e.getMessage());
//            }
////            throw new UnauthorizedException();
//
//			/*개발환경
//			Map<String,Object> testMap = new HashMap<>();
//			testMap.put("memberId", 2);
//			return testMap;*/
//        }
//        @SuppressWarnings("unchecked")
//        String value = (String)claims.getBody().get(key);
//        return value;
//    }
//    
//    @Override
//    public String refreshGet(String key) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String jwt = request.getHeader("Authorization");
//        Jws<Claims> claims = null;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(this.generateRefreshKey())
//                    .parseClaimsJws(jwt);
//        } catch (Exception e) {
//            if(log.isInfoEnabled()){
//                e.printStackTrace();
//            }else{
//                log.error(e.getMessage());
//            }
////            throw new UnauthorizedException();
//
//			/*개발환경
//			Map<String,Object> testMap = new HashMap<>();
//			testMap.put("memberId", 2);
//			return testMap;*/
//        }
//        @SuppressWarnings("unchecked")
//        String value = (String)claims.getBody().get(key);
//        return value;
//    }
//
//    @Override
//    public String getMemberId() {
//        return (String) this.get("memberId");
//    }
//    
//    @Override
//    public String getRefreshMemberId() {
//        return (String)this.refreshGet("memberId");
//    }
//}
