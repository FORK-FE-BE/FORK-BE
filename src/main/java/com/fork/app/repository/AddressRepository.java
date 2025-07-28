package com.fork.app.repository;

import com.fork.app.domain.entity.Address;
import com.fork.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUser(User user);
}
