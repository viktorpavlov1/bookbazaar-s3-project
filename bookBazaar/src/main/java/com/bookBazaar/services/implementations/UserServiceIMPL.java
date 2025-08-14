package com.bookBazaar.services.implementations;

import com.bookBazaar.models.other.AccessToken;
import com.bookBazaar.models.other.ModelConverter;
import com.bookBazaar.models.dto.LoginDTO;
import com.bookBazaar.models.dto.UserDTO;
import com.bookBazaar.models.entities.UserEntity;
import com.bookBazaar.models.repositories.UserRepository;
import com.bookBazaar.services.interfaces.AccessTokenServiceINT;
import com.bookBazaar.services.interfaces.UserServiceINT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceIMPL implements UserServiceINT {

    private final PasswordEncoder passwordEncoder;
    private final ModelConverter modelConverter = new ModelConverter();
    private final UserRepository userRepository;

    private final AccessTokenServiceINT tokenService;
    @Override
    public UserDTO createNewUser(UserDTO newUserDetails) {
        String encodedPassword = passwordEncoder.encode(newUserDetails.getPassword());
        UserEntity preparedUser = modelConverter.convertUserDTOToUserEntity(newUserDetails,encodedPassword);
        UserEntity savedUser = userRepository.save(preparedUser);
        return modelConverter.convertUserEntityToUserDTO(savedUser, encodedPassword);
    }

    @Override
    public String login(LoginDTO loginRequest) {
        UserEntity foundUser = userRepository.findByUsername(loginRequest.getUsername());
        String requestPassword = loginRequest.getPassword();
        String actualPassword = foundUser.getPassword();
        if (loginRequest == null) {
            throw new SecurityException("Invalid credentials");
        }
        else if (!passwordChecker(requestPassword, actualPassword)){
            throw new SecurityException("Invalid credentials");
        }
        else
        {
            String accessToken = generateAccessToken(foundUser);
            return accessToken;
        }
    }

    private boolean passwordChecker (String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public UserDTO getUserByID(String id) {
        Long idConverted = Long.parseLong(id);
        Optional<UserEntity> optionalUser = userRepository.findById(idConverted);
        UserEntity preparedUser = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        return modelConverter.convertUserEntityToUserDTO(preparedUser, preparedUser.getPassword());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return modelConverter.convertUserEntityToUserDTO(userEntity, userEntity.getPassword());
    }

    private String generateAccessToken(UserEntity user) {
        Long userID = user.getId() != null ? user.getId() : null;
        String role = user.getRole() != null ? user.getRole() : null;

        List<String> userRoles = new ArrayList<>();
        userRoles.add(role);
        return tokenService.encode(
                AccessToken.builder()
                        .subject(user.getUsername())
                        .id(userID)
                        .role(userRoles.get(0))
                        .build());
    }
}
