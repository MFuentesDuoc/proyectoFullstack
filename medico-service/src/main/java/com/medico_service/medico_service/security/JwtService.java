package com.medico_service.medico_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Clave secreta para firmar los tokens (cambia esto en producción)
    private static final String SECRET = "claveSecretaSuperSeguraParaJWT1234567890";

    // Tiempo de expiración: 24 horas (en milisegundos)
    private static final long EXPIRATION_TIME = 86400000;

    // Obtener la clave de firma
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generar un token a partir del username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)           // El usuario
                .setIssuedAt(new Date())        // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiración
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Firmar
                .compact();                     // Generar el token
    }

    // Extraer el username desde el token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validar que el token sea correcto (no haya sido modificado)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true; // Token válido
        } catch (JwtException e) {
            return false; // Token inválido
        }
    }
}