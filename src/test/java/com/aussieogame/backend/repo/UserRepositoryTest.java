/*
package com.aussieogame.backend.repo;

import com.aussieogame.backend.TestUtils;
import com.aussieogame.backend.model.dao.impl.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository repo;

    private static final Faker faker = new Faker();

    @Test
    @DisplayName("UserRepository is bootstrapped")
    void repoIsInjected() {
        assertNotNull(repo);
    }

    @Test
    @DisplayName("Can find by username")
    @Sql({"classpath:sql/users.sql"})
    void givenExistingUser_canFindByUsername() {
        //given
        //in DB: ('Discord User', 1000, 'PLATYPUS', 'discord:oauth2');
        final String username = "discord:oauth2";

        //when
        User found = repo.findByUsername(username).orElseThrow();
        boolean existing = repo.existsByUsername(username);

        //then
        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(existing).isTrue();
    }

    @Test
    @DisplayName("Will not find nonexistent username")
    @Sql({"classpath:sql/users.sql"})
    void givenNonexistentUser_willNotFindByUsername() {
        //given
        final String username = "not-in-db";

        //when
        Optional<User> found = repo.findByUsername(username);
        boolean existing = repo.existsByUsername(username);

        //then
        assertThat(found).isNotPresent();
        assertThat(existing).isFalse();
    }

    @Test
    @DisplayName("Will not find anything in an empty db")
    void givenEmptyDb_willNotFindByUsername() {
        //given
        final String username = "from-empty-db";

        //when
        long dbRows = repo.count();
        Optional<User> found = repo.findByUsername(username);
        boolean existing = repo.existsByUsername(username);

        //then
        assertThat(dbRows).isZero();
        assertThat(found).isNotPresent();
        assertThat(existing).isFalse();
    }

    @ParameterizedTest
    @MethodSource("supplyValidUsers")
    @DisplayName("Given valid User when persisting then OK")
    void givenValidUser_persists(User user) {
        //given
        //when
        repo.save(user);
        User persisted = repo.findById(user.getId()).orElseThrow();

        //then
        assertThat(persisted).isEqualTo(user);
    }

    @Test
    @DisplayName("Given duplicate username when persisting then throws")
    void givenDuplicateUsername_throws() {
        //given
        User existing = TestUtils.createUser("same@username");
        User duplicate = TestUtils.createUser("same@username");
        repo.save(existing); //this should pass

        //when
        Throwable thrown = catchThrowable(() -> repo.save(duplicate));

        //then
        assertThat(thrown).isInstanceOf(DataIntegrityViolationException.class);
    }

    @ParameterizedTest
    @MethodSource("supplyInvalidUsers")
    @DisplayName("Given null or empty fields when persisting validation fails")
    @Disabled("waiting on DB validation")
    void givenInvalidData_throws(User user) {
        // TODO: waiting on DB validation
        //given
        //when
        Throwable thrown = catchThrowable(() -> repo.save(user));

        //then
        // assertThat(thrown).isInstanceOf( ... );
    }

    private static Stream<User> supplyValidUsers() {
        return Stream.generate(() -> TestUtils.createUser(
                        faker.letterify("????@email.com")
                )
        ).limit(3);
    }

    private static List<User> supplyInvalidUsers() {
        User u1 = TestUtils.createUser("nullDisplayName");
        u1.setDisplayName(null);
        User u2 = TestUtils.createUser("emptyDisplayName");
        u2.setDisplayName("");
        User u3 = TestUtils.createUser("blankDisplayName");
        u3.setDisplayName("\n\t");
        User u4 = TestUtils.createUser(null);
        User u5 = TestUtils.createUser("");
        User u6 = TestUtils.createUser("\n\t");

        return List.of(
                u1, u2, u3, u4, u5, u6
        );
    }
}*/
