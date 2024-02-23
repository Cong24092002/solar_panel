package com.demo.MQTT.config;

import com.demo.MQTT.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/solar/auth/**","/swagger-ui/**","/v3/api-docs","/solar").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/solar/auth/**","/swagger-ui/**","/v3/api-docs","/solar","").permitAll()
                        .anyRequest().authenticated()
//                )
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer((oauth2) -> oauth2
//                        .jwt(Customizer.withDefaults())
                );
        return http.build();
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey()).build();
    }
    private RSAPublicKey publicKey() {
        // ...
        try {
            // Đọc nội dung khóa công khai từ tệp (ví dụ: my-public-key.pub trong thư mục resources)
            ClassPathResource resource = new ClassPathResource("my-public-key.pub");
            Path path = Paths.get(resource.getURI());
            byte[] publicKeyBytes = Files.readAllBytes(path);

            // Chuyển đổi base64-encoded public key sang byte array
            publicKeyBytes = Base64.getDecoder().decode(publicKeyBytes);

            // Tạo X509EncodedKeySpec từ byte array
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);

            // Sử dụng KeyFactory để tạo đối tượng RSAPublicKey
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

            return rsaPublicKey;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo khóa công khai", e);
        }
    }

}