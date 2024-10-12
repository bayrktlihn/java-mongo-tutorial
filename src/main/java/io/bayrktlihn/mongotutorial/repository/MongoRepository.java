package io.bayrktlihn.mongotutorial.repository;

import java.util.List;
import java.util.Optional;

public interface MongoRepository<TENTITY, TID> {
    List<TENTITY> findAll();

    TENTITY save(TENTITY entity);

    Optional<TENTITY> findById(TID id);

    void deleteById(TID id);
}
