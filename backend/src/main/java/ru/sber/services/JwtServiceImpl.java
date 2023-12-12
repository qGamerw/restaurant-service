package ru.sber.services;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String getSubClaim(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        return jwt.getClaim(claimName);
    }

    public String getEmailClaim(Jwt jwt) {
        return jwt.getClaim("email");
    }

    @Override
    public String getPhoneNumberClaim(Jwt jwt) {
        return jwt.getClaim("phone_number");
    }

    @Override
    public String getPreferredUsernameClaim(Jwt jwt) {
        return jwt.getClaim("preferred_username");
    }
}
