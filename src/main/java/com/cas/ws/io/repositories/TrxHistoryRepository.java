package com.cas.ws.io.repositories;


import com.cas.ws.io.entity.TrxHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxHistoryRepository extends JpaRepository<TrxHistory, Long> {
}
