package com.techmunna.user.service.repositiories;

import com.techmunna.user.service.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
