package com.services;

import com.entities.AuthRequest;
import com.entities.AuthResponse;
import com.entities.LoginRequest;
import com.entities.value_objects.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwt;

    @Autowired
    public AuthService(RestTemplate restTemplate,
                       final JwtUtil jwt) {
        this.restTemplate = restTemplate;
        this.jwt = jwt;
    }

    public AuthResponse register(AuthRequest authRequest) {
        //do validation if user already exists
        //This change is made in order not to generate new salt each time....
        //The previous generated salt needs to be stored in DB.
        authRequest.setUserSalt(BCrypt.gensalt());
        authRequest.setPassword(BCrypt.hashpw(authRequest.getPassword(), authRequest.getUserSalt()));

        UserVO userVO = restTemplate.postForObject("http://user-service/users/saveUser", authRequest, UserVO.class);
        Assert.notNull(userVO, "Failed to register user. Please try again later");

        String accessToken = jwt.generate(userVO, "ACCESS");
        String refreshToken = jwt.generate(userVO, "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse findDetails(LoginRequest loginRequest) {
        UserVO userVO = restTemplate.postForObject("http://user-service/users/login", loginRequest, UserVO.class);
        if(userVO != null) {
            String accessToken = jwt.generate(userVO, "ACCESS");
            String refreshToken = jwt.generate(userVO, "REFRESH");
            return new AuthResponse(accessToken, refreshToken);
        }
        return null;
    }

    public String getDataFromRegister() {
        return restTemplate.getForObject("http://user-service/users/secure", String.class);
    }
}
