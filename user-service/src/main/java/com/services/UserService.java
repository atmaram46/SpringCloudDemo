package com.services;

import com.entities.User;
import com.entities.value_objects.Department;
import com.entities.value_objects.ResponseTemplateVO;
import com.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository repository,
                       RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }


    public User save(User user) {
        return this.repository.save(user);
    }

    public User getByLoginDetails(String email, String password) {
        List<User> result = this.repository.findByEmail(email);
        for(User data: result) {
            String encryptPassword = BCrypt.hashpw(password, data.getUserSalt());
            if(data.getPassword().equals(encryptPassword))
                return data;
        }
        return null;
    }



    public User getById(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public ResponseTemplateVO getUserWithDepartment(Long id) {
        User user = this.getById(id);
        Department department = restTemplate.getForObject("http://department-service/departments/" + user.getDepartmentId(), Department.class);
        return new ResponseTemplateVO(user, department);
    }
}
