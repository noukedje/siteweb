package com.gestion.commerce.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class RememberMeLogins {
	@NotNull
	@Size(max=64)
	private String username;
	@Id
	@NotNull
	@Size(max=64)
	private String series;
	@NotNull
	@Size(max=64)
	private String token;
	@NotNull
	private Date last_used;
	public RememberMeLogins() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getLast_used() {
		return last_used;
	}
	public void setLast_used(Date last_used) {
		this.last_used = last_used;
	}
    
}
