package com.lawencon.jobportaladmin.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportaladmin.dto.ErrorResDto;

@Component
public class AuthorizationFilter extends OncePerRequestFilter{

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private List<RequestMatcher> matchers;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final long count = matchers.stream().filter(m -> m.matches(request)).collect(Collectors.counting());
		if (count == 0) {
			final String header = request.getHeader("Authorization");
			if (header != null) {
				final String jwt = header.replaceFirst("Bearer ", "");
				try {					
					final String tokenURl = "http://localhost:8082/token/validate";
					
					final HttpHeaders headers = new HttpHeaders();
				    headers.setContentType(MediaType.APPLICATION_JSON);
					headers.setBearerAuth(jwt);
					
					final RequestEntity<Object> tokenChecker= RequestEntity.post(tokenURl).headers(headers).body(null);
					final ResponseEntity<String> id = restTemplate.exchange(tokenChecker, String.class);
					final Authentication auth = new UsernamePasswordAuthenticationToken(id.getBody(),null);
					SecurityContextHolder.getContext().setAuthentication(auth);
					JwtConfig.set(jwt);

				} catch (Exception e) {
					e.printStackTrace() ;
					response.setStatus(401);
					final ErrorResDto<String> errorRes = new ErrorResDto<>();
					errorRes.setMessage("token expired");
					response.getWriter().append(new ObjectMapper().writeValueAsString(errorRes));
					return;
				}
			} else {	
				response.setStatus(401); 
				return;
			}
		}	

		filterChain.doFilter(request, response);
	}

	
}
