package com.eurofins.locator.persistance.repositories;

import com.eurofins.locator.persistance.enities.LocationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends MongoRepository<LocationEntity, String> {

    Optional<LocationEntity> findByLoc(Double[] loc);
}
