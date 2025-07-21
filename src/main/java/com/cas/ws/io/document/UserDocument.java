package com.cas.ws.io.document;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;



@Document
public class UserDocument implements Serializable {
	 
		private static final long serialVersionUID = 5313493413859894403L;
		
		@Id
		@Column(nullable=false)
		private String userId;

		@Column(nullable=false, length=50)
		private String firstName;
		
		@Column(nullable=false, length=50)
		private String lastName;
		
		@Column(nullable=false, length=120)
		private String email;

		@Column(nullable=false)
		private String password;
		
		@Column(nullable=false)
		private String encryptedPassword;
		
		private String emailVerificationToken;
		
		private String passwordResetToken;
		
		@Column(nullable=false)
		private Boolean emailVerificationStatus = false;
		
		
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getEncryptedPassword() {
			return encryptedPassword;
		}

		public void setEncryptedPassword(String encryptedPassword) {
			this.encryptedPassword = encryptedPassword;
		}

		public String getEmailVerificationToken() {
			return emailVerificationToken;
		}

		public void setEmailVerificationToken(String emailVerificationToken) {
			this.emailVerificationToken = emailVerificationToken;
		}

		public Boolean getEmailVerificationStatus() {
			return emailVerificationStatus;
		}

		public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
			this.emailVerificationStatus = emailVerificationStatus;
		}

		public String getPasswordResetToken() {
			return passwordResetToken;
		}

		public void setPasswordResetToken(String passwordResetToken) {
			this.passwordResetToken = passwordResetToken;
		}

}
