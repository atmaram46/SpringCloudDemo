package com.services;

import com.cache.CreateCacheMap;
import com.entities.User;
import com.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CreateCacheMap createCacheMap;

    public String saveAndValidate(User user) {
        if(!UserUtil.isValid(user.getDob())) {
            return "Error in Date of Birth!!!";
        }
        if(createCacheMap.isKeyPresent(user.getEmail())) {
            return "Email already present!!!";
        }
        saveValidData(user);
        return "Success";
    }

    public String validateAndUpdate(User user) {
        if(!UserUtil.isValid(user.getDob())) {
            return "Error in Date of Birth!!!";
        }
        if(!createCacheMap.isKeyPresent(user.getEmail())) {
            System.out.println("Email not present!!! Adding new Entry!!!");
        }
        saveValidData(user);
        return "Success";
    }

    public User getDataForEmail(String email) {
        return createCacheMap.get(email);
    }

    private void saveValidData(User user) {
        createCacheMap.put(user.getEmail(), user);
    }

    public void removeKeyData(String email) {
        createCacheMap.remove(email);
    }

}
