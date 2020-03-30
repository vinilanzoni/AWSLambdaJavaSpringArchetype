package br.com.lanzoni.ucr.filter;

import br.com.lanzoni.ucr.repository.UserRepository;
import br.com.lanzoni.ucr.service.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class AuthFilter implements Filter {

    @Autowired
    UserRepository userRepository;

    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            String header = req.getHeader(HEADER_STRING);
            if(header != null) {
                String jwt = header.replace(TOKEN_PREFIX, "").trim();
                String userId = JwtService.getAuthentication(jwt);
                req.setAttribute("user_id", userId);
                chain.doFilter(request, response);
            } else {
                res.setStatus(HttpStatus.SC_UNAUTHORIZED);
                res.getWriter().write("Autenticação inválida.");
            }
        } catch (JwtException e) {
            res.setStatus(HttpStatus.SC_UNAUTHORIZED);
            res.getWriter().write("Autenticação inválida.");
        } catch (Exception e) {
            log.error(e.getMessage());
            res.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("Erro interno do servidor");
        }
    }
}
