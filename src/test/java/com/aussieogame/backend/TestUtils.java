package com.aussieogame.backend;

import com.aussieogame.backend.model.dao.enumeration.Race;
import com.aussieogame.backend.model.dao.impl.User;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class TestUtils {
    private static final Faker faker = new Faker();

    public static User createUser(String username) {
        return new User(
                faker.name().username(),
                username,
                0L,
                new HashSet<>(),
                Race.PLATYPUS
        );
    }
}
