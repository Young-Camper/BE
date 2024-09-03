package com.youngcamp.server.repository;

import com.youngcamp.server.domain.Announcement;
import org.springframework.data.domain.Pageable;
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
    void deleteAllAnnouncementById(@Param("announcementIds") List<Long> ids);

    @Query(value = "select a.id from Announcement a where a.id in :announcementIds")
    List<Long> findExistingIds(@Param("announcementIds") List<Long> ids);
}
