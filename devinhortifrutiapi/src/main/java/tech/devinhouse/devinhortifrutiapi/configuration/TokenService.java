package tech.devinhouse.devinhortifrutiapi.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.devinhouse.devinhortifrutiapi.dto.UsuarioDTO;
import tech.devinhouse.devinhortifrutiapi.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Date;

@Service
public class TokenService {
    @Value("${security.jwt.expiration}")
    private Long expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) throws JsonProcessingException {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date expiracao = new Date();

        expiracao.setTime(hoje.getTime() + expiration);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setIsAdmin(usuario.getIsAdmin());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonUsuario = ow.writeValueAsString(usuarioDTO);

        return Jwts.builder()
                .setIssuer("Security JWT")
                .setSubject(jsonUsuario)
                .setIssuedAt(hoje)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean tokenValido(String token){
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Long getUsuarioPorId(String token) throws JsonProcessingException {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        ObjectMapper mapper = new ObjectMapper();
        UsuarioDTO usuario = mapper.readValue(claims.getSubject(), UsuarioDTO.class);
        return usuario.getId();
    }
}
