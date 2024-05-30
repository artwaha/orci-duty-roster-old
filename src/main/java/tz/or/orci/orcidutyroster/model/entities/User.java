package tz.or.orci.orcidutyroster.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tz.or.orci.orcidutyroster.model.auditable.AuditableWithUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "users")
public class User extends AuditableWithUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true)
    private String username;

    private String fullName;

    @ManyToOne
    @JoinColumn
    private Designation designation;

    @Column(nullable = false)
    private boolean active = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @ManyToOne
    private Department department;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Workstation> workstations = new ArrayList<>();

    /* UserDetails Implementations 👇 */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().name())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
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
        return active;
    }
}
