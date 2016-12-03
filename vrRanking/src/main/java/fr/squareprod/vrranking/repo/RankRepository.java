package fr.squareprod.vrranking.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.squareprod.vrranking.model.Rank;

public interface RankRepository extends MongoRepository<Rank, String> {
}
