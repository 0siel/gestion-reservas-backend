package com.osiel.auth.services;

import com.osiel.auth.dtos.LoginRequest;
import com.osiel.auth.dtos.TokenResponse;

public interface AuthService {
	
	TokenResponse autenticar(LoginRequest request) throws Exception;

}
