package com.application.security.service.impl;

import com.application.security.dto.UserDTO;
import com.application.security.entity.User;
import com.application.security.repository.UserRepository;
import com.application.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(()->new UsernameNotFoundException("user not found"));
            }
        };
    }

    @Override
    public ResponseEntity<?> getUserById(int userId,UserDetails userDetails) {
//        System.out.println("hello");
        Map<String, Object> map = new LinkedHashMap<>();
        try{
            User u =userRepository.getReferenceById(userId);
//            System.out.println(u.getFirstname());
            if (u!=null) {
                UserDTO userDto = modelMapper.map(u, new TypeToken<UserDTO>(){}.getType());
                map.put("status", 1);
                map.put("data", userDto);
                return new ResponseEntity<>(map, HttpStatus.OK);
            } else {
                map.clear();
                map.put("status", 0);
                map.put("message", "User not found");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
