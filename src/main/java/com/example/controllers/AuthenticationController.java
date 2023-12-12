package com.example.controllers;

import com.example.dto.AuthenticationRequest;
import com.example.dto.AuthenticationResponse;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization ";

    @PostMapping("/authentication")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(),authenticationRequest.getHaslo()));

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Niepoprawne dane logowania");
        } catch(DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"Taki uzytkownik nie istieje");
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getMail());
//        Optional<User> optionalUser = userRepository.findFirstByMail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

//        if (optionalUser.isPresent()) {
//            response.getWriter().write(new JSONObject()
//                    .put("userId", optionalUser.get().getId())
//                    .put("role", optionalUser.get().getRole())
//                    .toString());
//        }

//        response.setHeader("Access-Control-Expose-Headers", "Authorization");
//        response.setHeader("Access-Control-Allow_Headers", "Authorization,X-Pingother,Origin,X-Requested-With,Content-Type,Accept,X-Custom-header");
//        response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwt);

        return new AuthenticationResponse(jwt);

    }
}
