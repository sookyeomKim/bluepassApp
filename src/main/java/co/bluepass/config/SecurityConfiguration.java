package co.bluepass.config;

import co.bluepass.security.*;
import co.bluepass.security.xauth.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import javax.inject.Inject;

/**
 * The type Security configuration.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private TokenProvider tokenProvider;

    /**
     * Password encoder password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure global.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/scripts/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/fonts/**")
            .antMatchers("/images/**")
            .antMatchers("/styles/**")
            .antMatchers("/swagger-ui/**")
            .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/register/add").hasAuthority(AuthoritiesConstants.USER)
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/account/reset_password/init").permitAll()
            .antMatchers("/api/account/reset_password/finish").permitAll()

            /* 전체 접근 허용 */
            .antMatchers(HttpMethod.GET, "/api/commonCodes").permitAll()
            .antMatchers(HttpMethod.GET, "/api/commonCodes/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/clubs").permitAll()
            .antMatchers(HttpMethod.GET, "/api/clubs/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/instructors").permitAll()
            .antMatchers(HttpMethod.GET, "/api/instructors/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/actions").permitAll()
            .antMatchers(HttpMethod.GET, "/api/actions/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/actionSchedules").permitAll()
            .antMatchers(HttpMethod.GET, "/api/actionSchedules/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/classSchedules").permitAll()
            .antMatchers(HttpMethod.GET, "/api/classSchedules/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/partnerRequests").permitAll()
            .antMatchers(HttpMethod.POST, "/api/partnerRequests").permitAll()
            .antMatchers(HttpMethod.POST, "/api/account/request").permitAll()

            /* 사용자 접근 허용 */
            .antMatchers("/api/ticketHistory").hasAnyAuthority(AuthoritiesConstants.USER, AuthoritiesConstants.REGISTER, AuthoritiesConstants.ADMIN)
            .antMatchers("/api/ticketHistory/**").hasAnyAuthority(AuthoritiesConstants.USER, AuthoritiesConstants.REGISTER, AuthoritiesConstants.ADMIN)
            .antMatchers("/api/reservations").authenticated()
            .antMatchers("/api/reservations/**").authenticated()

            /* 제휴업체 접근 허용 */
            .antMatchers(HttpMethod.POST, "/api/clubs").hasAnyAuthority(AuthoritiesConstants.VENDOR, AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.DELETE, "/api/clubs/**").hasAnyAuthority(AuthoritiesConstants.VENDOR, AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.POST, "/api/instructors").hasAnyAuthority(AuthoritiesConstants.VENDOR, AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.DELETE, "/api/instructors/**").hasAnyAuthority(AuthoritiesConstants.VENDOR, AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.POST, "/api/actions").hasAnyAuthority(AuthoritiesConstants.VENDOR, AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.DELETE, "/api/actions/**").hasAnyAuthority(AuthoritiesConstants.VENDOR, AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.POST, "/api/actionSchedules").hasAnyAuthority(AuthoritiesConstants.VENDOR, AuthoritiesConstants.ADMIN)
            .antMatchers(HttpMethod.DELETE, "/api/actionSchedules/**").hasAnyAuthority(AuthoritiesConstants.VENDOR, AuthoritiesConstants.ADMIN)

            .antMatchers("/api/partner/**").hasAnyAuthority(AuthoritiesConstants.VENDOR, AuthoritiesConstants.ADMIN)

            .antMatchers("/api/logs/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/**").authenticated()
            .antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/websocket/**").permitAll()
            .antMatchers("/metrics/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/dump/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/shutdown/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/beans/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/configprops/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/info/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/autoconfig/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/env/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api-docs/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/protected/**").authenticated()
            .and()
            .apply(securityConfigurerAdapter());

    }

    private XAuthTokenConfigurer securityConfigurerAdapter() {
        return new XAuthTokenConfigurer(userDetailsService, tokenProvider);
    }

    /**
     * Security evaluation context extension security evaluation context extension.
     *
     * @return the security evaluation context extension
     */
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
