package com.khoobi.big.repository;

import com.khoobi.big.model.AdEvent;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AdRepository extends CassandraRepository<AdEvent, String> {

    @Query("UPDATE ad_event SET clickTime=:clickTime WHERE requestId = :requestId")
    void update(Long clickTime, String requestId);
}
