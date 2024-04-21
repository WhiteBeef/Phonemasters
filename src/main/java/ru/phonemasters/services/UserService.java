package ru.phonemasters.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.phonemasters.dto.UserDTO;
import ru.phonemasters.entities.User;
import ru.phonemasters.entities.UserRole;
import ru.phonemasters.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    @Transactional
    public void enableAppUser(User user) {
        user.setEnabled(true);
    }

    public void signUpUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("Email already taken!");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @PostConstruct
    private void signUpAdmin() {
        if (!userRepository.findAll().isEmpty()) {
            return;
        }

        User user = new User("Phonemasters", "Admin", "admin@phonemasters.ru", "popTanjIDfwQ", UserRole.ADMIN);
        log.info("Aunthetification credentials: admin@phonemasters.ru : popTanjIDfwQ");
        signUpUser(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public void saveUser(UserDTO userDto) {
        userRepository.save(mapToUser(userDto));
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream().map(this::mapToUserDto).toList();
    }

    @NotNull
    private UserDTO mapToUserDto(@NotNull User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUserRole(user.getUserRole());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    @NotNull
    private User mapToUser(@NotNull UserDTO userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUserRole(userDto.getUserRole());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
}