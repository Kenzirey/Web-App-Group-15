package no.ntnu.database.jwt;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import no.ntnu.database.model.Role;
import no.ntnu.database.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Contains authentication information, which is required by the UserDetailsService.
 * Whole class comes from 05-jwt-authentication from app-dev repository by Gist.
 */
public class AccessUserDetails implements UserDetails {

	private final String username;
	private final String password;
	private final boolean isActive;
	private final List<GrantedAuthority> authorities = new LinkedList<>();
	private final String twoFactorSecret;
    private final boolean isTwoFactorEnabled;
	private final Long userId;

	/**
	 * Create access object.
	 *
	 * @param user The user to copy data from
	 */
	public AccessUserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.isActive = user.isActive();
		this.convertRoles(user.getRoles());
		this.twoFactorSecret = user.getTwoFactorSecret();
        this.isTwoFactorEnabled = user.isTwoFactorEnabled();
		this.userId = user.getId();
	}

	private void convertRoles(Set<Role> roles) {
		authorities.clear();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getTwoFactorSecret() {
        return twoFactorSecret;
    }

    public boolean isTwoFactorEnabled() {
        return isTwoFactorEnabled;
    }
	public Long getUserId() {
		return userId;
	}
}
