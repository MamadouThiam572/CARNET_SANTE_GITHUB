package com.senegalsante.repository;

import com.senegalsante.model.Profile;
import com.senegalsante.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByUser(User user);
}
