package com.soulcode.services.Config;

import com.soulcode.services.Security.JWTAuthenticationFilter;
import com.soulcode.services.Security.JWTAuthorizationFilter;
import com.soulcode.services.Services.AuthUserDetailService;
import com.soulcode.services.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// Agrega todos as informações de segurança http e gerencia do user
@EnableWebSecurity
public class JWTConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthUserDetailService userDetailService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // UserDetailService - carregar o usuário do banco
        //BCrypt - gerador de hash de senhas e comparar senhas
        //Uso do passwordEncoder() para comparar senhas de login
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // habilitar o cors e desabilita o csrf
        // para conversar com o angular
        http.cors().and().csrf().disable();
        // JWTAuthenticationFilter é chamado quando uso /login
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtils));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtils));

        http.authorizeRequests() // autoriza as requizições
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                //.antMatchers(HttpMethod.GET, "/services/**").permitAll() // libera GET para /services/cargos
                // ** representa qualquer possibilidade
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean // cross origin resource sharing
    CorsConfigurationSource corsConfigurationSource() { // configuração global de CORS
        CorsConfiguration configuration = new CorsConfiguration(); // configurações padrões
        configuration.setAllowedMethods(List.of( // quais métodos estão liberados via CORS?
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        )); // métodos permitidos para o front acessar

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // endpoints permitidos para o front acessar
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;

    }
    // "/services/funcionarios" -> "/**" -> TODOS OS ENDPOINTS

    // gera e
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

}
