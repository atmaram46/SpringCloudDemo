package com.services;

import com.entities.AuthRequest;
import com.entities.AuthResponse;
import com.entities.UserResponse;
import com.entities.value_objects.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;

    @Autowired
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AuthResponse register(AuthRequest authRequest) {
        //do validation if user already exists
        //This change is made in order not to generate new salt each time....
        //The previous generated salt needs to be stored in DB.
//        authRequest.setUserSalt(BCrypt.gensalt());
//        authRequest.setPassword(BCrypt.hashpw(authRequest.getPassword(), authRequest.getUserSalt()));

        String result = restTemplate.postForObject("http://user-service/users/saveUser", authRequest, String.class);

//        String accessToken = jwt.generate(userVO, "ACCESS");
//        String refreshToken = jwt.generate(userVO, "REFRESH");

        return new AuthResponse(result);
    }

    public AuthResponse update(AuthRequest authRequest) {
        String result = restTemplate.postForObject("http://user-service/users/updateUser", authRequest, String.class);
        return new AuthResponse(result);
    }

    public UserResponse findDetails(UserResponse loginRequest) {
        return restTemplate.postForObject("http://user-service/users/login", loginRequest, UserResponse.class);
    }

    public String getDataFromRegister() {
        return restTemplate.getForObject("http://user-service/users/secure", String.class);
    }
}
