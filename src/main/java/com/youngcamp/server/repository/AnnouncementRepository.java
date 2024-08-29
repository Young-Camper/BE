package com.youngcamp.server.repository;

import com.youngcamp.server.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from Announcement a where a.id in :announcementIds")
    void deleteAllByUserId(@Param("announcementIds") List<Long> ids);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from Announcement a where a.id = :announcementId")
    void deleteAllByUserId(@Param("announcementId") Long id);
}
