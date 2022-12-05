package com.eurofins.locator.persistance.repositories;

import com.eurofins.locator.persistance.enities.IpEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpRepository extends MongoRepository<IpEntity, String> {
    boolean existsByIp(String ip);
}
