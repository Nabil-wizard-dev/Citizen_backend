package Nabil.Simplice.app.dto.request;

import jakarta.validation.constraints.*;

public class LoginRequest {
	@NotBlank(message = "L'email est obligatoire")
	@Email(message = "Format email invalide")
	private String email;
	
	@NotBlank(message = "Le mot de passe est obligatoire")
	private String motDePasse;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
}
