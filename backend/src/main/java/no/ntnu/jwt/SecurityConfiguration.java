package no.ntnu.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
	/**
	 * A service providing our users from the database.
	 */
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/**
	 * This method will be called automatically by the framework to find the authentication to use.
	 * Here we tell that we want to load users from a database
	 *
	 * @param auth Authentication builder
	 * @throws Exception When user service is not found
	 */
	@Autowired
	protected void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	/**
	 * This method will be called automatically by the framework to find the authentication to use.
	 *
	 * @param http HttpSecurity setting builder
	 * @throws Exception When security configuration fails
	 */
	@Bean
	public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
		// Set up the authorization requests, starting from most restrictive at the top,
		// to least restrictive on the bottom
		http
				// Disable CSRF and CORS checks. Without this it will be hard to make automated tests.
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				// Authentication URL is accessible for everyone
				.authorizeHttpRequests((auth) -> auth.requestMatchers("/authenticate").permitAll())
				// The default URL / is accessible to everyone
				.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/").permitAll())
				// Any other request will be authenticated with a stateless policy
				.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
				// Enable stateless session policy
				.sessionManagement((session) ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// Enable our JWT authentication filter
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		// Necessary authorization for each endpoint will be configured by each method,
		// using @PreAuthorize
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
			throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * This method is called to decide what encryption to use for password checking.
	 *
	 * @return The password encryptor
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}