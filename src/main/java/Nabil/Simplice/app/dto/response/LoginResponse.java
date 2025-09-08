package Nabil.Simplice.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import Nabil.Simplice.app.enums.UtilisateurRole;

public class LoginResponse {
	@JsonProperty("token")
	private String token;
	@JsonProperty("expiresIn")
	private Long expiresIn;
	private UtilisateurRole role;

	// Constructeur sans argument
	public LoginResponse() {}

	// Constructeur avec tous les champs
	public LoginResponse(String token, Long expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UtilisateurRole getRole() {
		return this.role;
	}

	public void setRole(UtilisateurRole role) {
		this.role = role;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
}
