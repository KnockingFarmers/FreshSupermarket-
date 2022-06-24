package org.gl.util;

import io.jsonwebtoken.*;
import org.gl.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author 甘龙
 */
@Component
public class JwtUtil {

    private static final long EXPIRATION=1000*60*60*24;
    private static final String STRING="admin";

    public  String createToken(User user){
        JwtBuilder jwtBuilder= Jwts.builder();
        String jwtToken=jwtBuilder
                //header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("user",user)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                //signature
                .signWith(SignatureAlgorithm.HS256,STRING)
                .compact();
        return jwtToken;
    }

    public Integer checkToken(String token){

        try {
            Jwts.parser().setSigningKey(STRING).parseClaimsJws(token);
        }catch (JwtException e){
            return 0;
        }
        return 1;
    }

    public Map<String,Object> analyzeToken(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(STRING).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        Map<String,Object> map = (Map<String, Object>) body.get("user");
        return map;
    }
}
