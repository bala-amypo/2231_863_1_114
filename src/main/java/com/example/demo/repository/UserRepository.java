package com.example.demo.repository;

import com.example.demo.entity.*;
import java.util.*;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
}
