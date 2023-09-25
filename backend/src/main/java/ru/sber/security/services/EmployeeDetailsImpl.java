package ru.sber.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sber.entities.BranchOffice;
import ru.sber.entities.Employee;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;

    private String username;

    private String email;
    private BranchOffice branchOffice;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static EmployeeDetailsImpl build(Employee employee) {

        List<GrantedAuthority> authorities = new ArrayList<>() {{
            new SimpleGrantedAuthority(employee.getPosition().getPosition().name());
        }};
        return new EmployeeDetailsImpl
                (
                        employee.getId(),
                        employee.getEmployeeName(),
                        employee.getEmail(),
                        employee.getBranchOffice(),
                        employee.getPassword(),
                        authorities
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public BranchOffice gerBranchOffice() {
        return branchOffice;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
