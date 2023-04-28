//package com.aussieogame.backend.service;
//
//import com.aussieogame.backend.utils.TestFactoryUtils;
//import com.aussieogame.backend.model.dao.impl.User;
//import com.aussieogame.backend.repo.UserRepository;
////import com.aussieogame.backend.exception.RegistrationException;
//import com.github.javafaker.Faker;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    private static final Faker faker = new Faker();
//
//    @Mock
//    private UserRepository repo;
//
//    @InjectMocks
//    private GameApiService api;
//
//    @Test
//    @DisplayName("UserService is bootstrapped")
//    void serviceIsInjected() {
//        assertNotNull(api);
//    }
//
//    @Test
//    @DisplayName("getDisplayName calls repo")
//    void getDisplayName_invokesRepo() {
//        //given
//        final String displayName = faker.name().username();
//        final String username = faker.letterify("????@email.com");
//        JwtAuthenticationToken mockJwt = mockJwt(username);
//
//        mockUser(displayName, username);
//
//        //when
//     //   Optional<String> actual = api.getDisplayName(mockJwt);
//
//        //then
//        verify(repo).findByUsername(username);
//     //   assertThat(actual).hasValue(displayName);
//    }
//
//    @Test
//    @DisplayName("Given username already registered then fails early")
//    void givenAlreadyRegistered_assignRegistrationToUser_fails() {
//        //given
//        final String displayName = faker.name().username();
//        final String username = faker.letterify("????@email.com");
//        JwtAuthenticationToken principal = mockJwt(username);
//
//        given(repo.existsByUsername(any())).willReturn(true);
//
//        //when
//        Throwable thrown = catchThrowable(() -> api.assignRegistrationToUser(displayName, principal));
//
//        //then
//        assertThat(thrown)
//                .isInstanceOf(RegistrationException.class)
//                .hasMessageContaining("Registration already exists.");
//        verify(repo, never()).save(any());
//    }
//
//    @Test
//    @DisplayName("assignRegistrationToUser calls repo with new registration")
//    void assignRegistrationToUser_invokesRepo() {
//        //given
//        final String displayName = faker.name().username();
//        final String username = faker.letterify("????@email.com");
//        JwtAuthenticationToken principal = mockJwt(username);
//
//        given(repo.existsByUsername(any())).willReturn(false);
//
//        //when
//        try {
//            api.assignRegistrationToUser(displayName, principal);
//        } catch (Throwable e) {
//            fail();
//        }
//
//        //then
//        verifyThatCallsRepoWithProperData(displayName, username);
//    }
//
//
//    private void verifyThatCallsRepoWithProperData(String displayName, String username) {
//        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
//        verify(repo).save(userCaptor.capture());
//        assertThat(userCaptor.getValue().getUsername()).isEqualTo(username);
//        assertThat(userCaptor.getValue().getDisplayName()).isEqualTo(displayName);
//        assertThat(userCaptor.getValue().getPoints()).isZero();
//    }
//
//    private void mockUser(String displayName, String username) {
//        User user = TestFactoryUtils.createUser(username);
//        user.setDisplayName(displayName);
//        given(repo.findByUsername(username)).willReturn(Optional.of(user));
//    }
//
//    private JwtAuthenticationToken mockJwt(String username) {
//        JwtAuthenticationToken mockJwt = Mockito.mock(JwtAuthenticationToken.class);
//        given(mockJwt.getName()).willReturn(username);
//        return mockJwt;
//    }
//
//}