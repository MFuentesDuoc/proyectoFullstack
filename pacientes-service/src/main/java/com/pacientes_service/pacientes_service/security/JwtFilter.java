package com.pacientes_service.pacientes_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // Obtener el header de autorización
        final String authHeader = request.getHeader("Authorization");

        // Si no hay token o no empieza con "Bearer ", seguir sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Extraer el token (quitar "Bearer ")
        final String token = authHeader.substring(7);

        // Extraer el username del token
        final String username = jwtService.extractUsername(token);

        // Si hay username y no hay autenticación previa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Cargar el usuario desde la base de datos
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validar el token
            if (jwtService.validateToken(token)) {
                // Crear la autenticación
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Guardar la autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuar con la petición
        chain.doFilter(request, response);
    }
}