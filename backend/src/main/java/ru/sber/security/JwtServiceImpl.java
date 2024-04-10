package ru.sber.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Service;
import ru.sber.entities.BranchOffice;
import ru.sber.models.Attributes;
import ru.sber.models.UpdateUserData;
import ru.sber.models.UserDetails;

import java.util.List;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String getSubClaim(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        return jwt.getClaim(claimName);
    }

    @Override
    public List<String> getRoleClaim(Jwt jwt) {
        return (List<String>) ((Map<?, ?>) jwt.getClaims().get("realm_access")).get("roles");
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

    @Override
    public String getFirstNameClaim(Jwt jwt) {
        return jwt.getClaim("given_name");
    }

    @Override
    public String getLastNameClaim(Jwt jwt) {
        return jwt.getClaim("family_name");
    }

    @Override
    public UserDetails getDataUser(Jwt jwt, BranchOffice branchOffice, String status) {
        return new UserDetails(
                getPreferredUsernameClaim(jwt),
                getEmailClaim(jwt),
                getRoleClaim(jwt),
                getPhoneNumberClaim(jwt),
                getFirstNameClaim(jwt),
                getLastNameClaim(jwt),
                branchOffice,
                status);
    }

    @Override
    public UpdateUserData getDataUserByContext(Jwt jwt) {
        UpdateUserData updateUserData = new UpdateUserData();
        updateUserData.setEmail(getEmailClaim(jwt));
        updateUserData.setFirstName(getFirstNameClaim(jwt));
        updateUserData.setLastName(getLastNameClaim(jwt));

        Attributes attributes = new Attributes();
        attributes.setPhoneNumber(getPhoneNumberClaim(jwt));
        updateUserData.setAttributes(attributes);

        return updateUserData;
    }
}
