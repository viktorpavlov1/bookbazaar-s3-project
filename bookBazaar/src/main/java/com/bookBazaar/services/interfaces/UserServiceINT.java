package com.bookBazaar.services.interfaces;

import com.bookBazaar.models.dto.LoginDTO;
import com.bookBazaar.models.dto.UserDTO;

public interface UserServiceINT {
    public UserDTO createNewUser(UserDTO newUserDetails);
    public String login(LoginDTO loginRequest);
    public UserDTO getUserByID(String id);
    public UserDTO getUserByUsername(String username);
}
