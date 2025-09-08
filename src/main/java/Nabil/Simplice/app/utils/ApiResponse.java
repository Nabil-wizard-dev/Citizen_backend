package Nabil.Simplice.app.utils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ApiResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LocalDateTime date;
	private boolean isError;
	private String message;
	private T data;
	

	public ApiResponse(LocalDateTime date, boolean isError, String message, T data) {
		super();
		this.date = date;
		this.isError = isError;
		this.message = message;
		this.data = data;
	}
	
	public ApiResponse() {
		super();
	}
	
	
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	// Méthodes statiques pour créer des réponses standardisées
	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(LocalDateTime.now(), false, message, data);
	}
	
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(LocalDateTime.now(), false, "Succès", data);
	}
	
	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(LocalDateTime.now(), true, message, null);
	}
	
	public static <T> ApiResponse<T> error(String message, T data) {
		return new ApiResponse<>(LocalDateTime.now(), true, message, data);
	}
	
}
