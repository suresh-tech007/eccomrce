package com.eccomrce.eccomrce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eccomrce.eccomrce.Config.JwtProvider;
import com.eccomrce.eccomrce.exception.UserException;
 
import com.eccomrce.eccomrce.model.User;
import com.eccomrce.eccomrce.repository.UserRepository;
import com.eccomrce.eccomrce.request.LoginRequest;
import com.eccomrce.eccomrce.response.AuthResponse;
import com.eccomrce.eccomrce.service.CartService;
import com.eccomrce.eccomrce.service.CustomeUserServiceImplementation;

 
import org.springframework.web.bind.annotation.PostMapping;
 


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncode;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomeUserServiceImplementation customUserService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new UserException("Email is Already Used With Another Account ");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("Email is Already Used With Another Account");
        }
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);
        createdUser.setPassword(passwordEncode.encode(password));

        User savedUser = userRepository.save(createdUser);

        cartService.createCart(savedUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
                savedUser.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.gerenateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("SignUp Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

    
        Authentication authentication = authenticate(username, password);
    

       
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.gerenateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("SignIn Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    private Authentication authenticate(String username, String password) {
       

        UserDetails userDetails = customUserService.loadUserByUsername(username);
         

        
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username");
        }

        if (!passwordEncode.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }
        
         

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

     
    
    
}
