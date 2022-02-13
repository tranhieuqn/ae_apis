package com.ae.apis.repository;


import com.ae.apis.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    List<ConfirmationToken> findByUserIdAndType(Long userId, String type);
    List<ConfirmationToken> findByUserId(Long userId);

    @Query("" +
            " SELECT c " +
            " FROM ConfirmationToken c " +
            " WHERE c.userId = :userId " +
            " AND c.confirmationToken = :confirmationToken " +
            " AND c.type = :type " +
            " AND c.expireDate >= :nowDate")
    List<ConfirmationToken> findValidConfirmationTokens(Long userId, String confirmationToken, String type, Date nowDate);
}
