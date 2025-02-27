package com.safetrust.user_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.safetrust.user_service.entity.User;
import com.safetrust.user_service.status.ETrackingUser;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("update user b set b.tracking = ?3, b.borowedTotal=?2 where b.id = ?1")
    void updateUserStatus(long id, int total, ETrackingUser status);

    @Query("select b.created_inventory.name, count(b.id) from user b where b.tracking = ?1 group by b.created_inventory.id")
    List<Object[]> findAvailableUserByOfPerInventory(ETrackingUser fines);

}
