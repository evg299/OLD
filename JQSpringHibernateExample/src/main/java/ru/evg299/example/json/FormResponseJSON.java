package ru.evg299.example.json;

import java.util.List;

public class FormResponseJSON {
	private String status = "succsess";
	private List<ErrorJSON> errors;
	// private Map<String, String> errors;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ErrorJSON> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorJSON> errors) {
		this.errors = errors;
	}



}
