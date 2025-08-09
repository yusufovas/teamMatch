package com.example.teamMatch.services.auth;


import com.example.teamMatch.dto.LoginDto;
import com.example.teamMatch.dto.UserDto;

import java.util.Map;

public interface AuthService {

    Map<String, String> signup (UserDto userDto);
    String signin(LoginDto loginDto);
}
