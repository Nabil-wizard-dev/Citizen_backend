package Nabil.Simplice.app.service;

import Nabil.Simplice.app.dto.request.LoginRequest;
import Nabil.Simplice.app.dto.request.RegisterRequest;
import Nabil.Simplice.app.dto.response.LoginResponse;
import Nabil.Simplice.app.dto.response.RegisterResponse;

public interface AuthentificationService {
	RegisterResponse register(RegisterRequest request);
	LoginResponse login(LoginRequest request);
}
