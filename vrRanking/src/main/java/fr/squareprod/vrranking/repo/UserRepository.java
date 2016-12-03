package fr.squareprod.vrranking.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.squareprod.vrranking.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}
