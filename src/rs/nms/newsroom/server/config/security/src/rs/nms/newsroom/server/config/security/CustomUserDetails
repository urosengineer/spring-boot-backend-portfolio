package rs.nms.newsroom.server.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.nms.newsroom.server.domain.Permission;
import rs.nms.newsroom.server.domain.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final Long clientId;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.clientId = user.getClient() != null ? user.getClient().getId() : null;
        this.username = user.getUsername();
        this.password = user.getPasswordHash();
        this.authorities = extractAuthorities(user);
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(User user) {
        Set<Permission> permissions = user.getRole().getPermissions();
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
