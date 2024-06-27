package propensi.b02.sobatarlydia.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http 
            .csrf().disable()
            .cors().disable()
            .authorizeRequests()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/registrasi").permitAll()
            .antMatchers("/login**").permitAll()
            .antMatchers("/pengguna/viewall").hasAuthority("Admin")
            .antMatchers("/pengguna/add").hasAuthority("Admin")
            .antMatchers("/pengguna/profil").permitAll()
            .antMatchers("/home.png").permitAll()
            .antMatchers("/logo.png").permitAll()
            .antMatchers("/pengguna/update-password").permitAll()
            .antMatchers("/pengguna/update-password1").permitAll()
            .antMatchers("/pengguna/profil").permitAll()
            .antMatchers("/pengguna/**").hasAuthority("Admin")
            .antMatchers("/api/v1/penjualan/list-all").hasAnyAuthority("Karyawan", "Apoteker", "Admin")
            .antMatchers("/api/v1/**").hasAnyAuthority("Karyawan", "Apoteker")
            .antMatchers("/obat/daftarkan-obat").hasAuthority("Karyawan")
            .antMatchers("/obat/input-data").hasAuthority("Karyawan")
            .antMatchers("/obat/data-obat").hasAnyAuthority("Karyawan", "Admin", "Apoteker")
            .antMatchers("/obat/detail-obat/**").hasAnyAuthority("Karyawan", "Admin", "Apoteker")
            .antMatchers("/obat/obat-ditolak").hasAuthority("Apoteker")
            .antMatchers("/obat/obat-diterima").hasAuthority("Apoteker")
            .antMatchers("/obat/arsipkan/**").hasAuthority("Apoteker")
            .antMatchers("/obat-detail/waiting").hasAnyAuthority("Karyawan", "Apoteker")
            .antMatchers("/obat/update/**").hasAuthority("Karyawan")
            .antMatchers("/penjualan/viewall").hasAnyAuthority("Karyawan", "Admin", "Apoteker")
            .antMatchers("/penjualan/detail-penjualan/**").hasAnyAuthority("Karyawan", "Admin", "Apoteker")
            .antMatchers("/penjualan/add**").hasAnyAuthority("Karyawan")
            .antMatchers("/retur/add").hasAnyAuthority("Karyawan")
            .antMatchers("/retur/viewall").hasAnyAuthority("Karyawan", "Apoteker")
            .antMatchers("/retur/verifikasi").hasAnyAuthority("Apoteker")
            .antMatchers("/retur/update/**").hasAnyAuthority("Karyawan")
            .antMatchers("/penawaran/daftarkan-penawaran").hasAnyAuthority("Distributor")
            .antMatchers("/penawaran/viewall").hasAnyAuthority("Distributor", "Apoteker")
            .antMatchers("/penawaran/verifikasi/**").hasAnyAuthority("Apoteker")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .failureHandler(handleAuthenticationFailure())
            .defaultSuccessUrl("/", true)
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login").permitAll();
        return http.build();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

    @Bean
    public AuthenticationFailureHandler handleAuthenticationFailure() {
        return new SimpleUrlAuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AuthenticationException authenticationException) throws IOException, ServletException {
                
                if (authenticationException instanceof BadCredentialsException) {
                    setDefaultFailureUrl("/login?error");
                }
                else if (authenticationException instanceof DisabledException) {
                    setDefaultFailureUrl("/login?accessDenied");
                }
                
                super.onAuthenticationFailure(httpRequest, httpResponse, authenticationException);
            }
        };
    }
}