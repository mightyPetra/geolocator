package com.eurofins.locator.persistance.enities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ip_address")
public class IpEntity {

    @Id
    private String id;
    private String ip;
    @DBRef
    private LocationEntity location;

}