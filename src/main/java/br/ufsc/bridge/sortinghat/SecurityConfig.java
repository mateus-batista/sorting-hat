package br.ufsc.bridge.sortinghat;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/oauth_login", "/login", "/login/**", "/login_failure", "/sorteador", "/vendor/**", "/css/**", "/img/**", "/js/**", "/font/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.and()
				.oauth2Login()
				.loginPage("/login")
				.defaultSuccessUrl("/resultado", true)
				.failureUrl("/login_failure")
				.userInfoEndpoint().oidcUserService(new OidcUserService());

		http.headers().frameOptions().sameOrigin();
	}
}