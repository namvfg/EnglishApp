/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.components;

import com.ndd.enums.UserRole;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
@PropertySource("classpath:configs.properties")
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey; // Phải dài ít nhất 32 bytes

    @Value("${jwt.expiration-ms}")
    private long expirationTimeMs;

    // Tạo JWT
    public String generateToken(String email, UserRole role) throws JOSEException {
        JWSSigner signer = new MACSigner(secretKey.getBytes());

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .claim("role", role.toString())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + expirationTimeMs))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    // Kiểm tra token hợp lệ
    public boolean isTokenValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secretKey.getBytes());

            return signedJWT.verify(verifier)
                    && new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());
        } catch (Exception e) {
            return false;
        }
    }

    // Lấy username từ token
    public String extractEmail(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public Authentication getAuthentication(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            String email = claims.getSubject();
            String role = claims.getStringClaim("role");

            // Tạo danh sách quyền (role cần tiền tố ROLE_)
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

            // Tạo đối tượng UserDetails
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(email, "", authorities);

            return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
        } catch (Exception e) {
            return null;
        }
    }
}
