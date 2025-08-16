package com.example.teamMatch.services.auth;


import com.example.teamMatch.dto.auth.LoginDto;
import com.example.teamMatch.dto.user.UserResponseDto;

import java.util.Map;

public interface AuthService {

    Map<String, String> signup(UserResponseDto userResponseDto);

    String signin(LoginDto loginDto);
}
