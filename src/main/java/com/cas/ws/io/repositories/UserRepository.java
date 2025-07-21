package com.cas.ws.io.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cas.ws.io.document.UserDocument;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, Long> {
	UserDocument findByEmail(String email);

	UserDocument findByUserId(String userId);

	UserDocument findUserByEmailVerificationToken(String token);

	UserDocument findByPasswordResetToken(String token);
}
