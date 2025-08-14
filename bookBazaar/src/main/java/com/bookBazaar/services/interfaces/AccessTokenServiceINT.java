package com.bookBazaar.services.interfaces;

import com.bookBazaar.models.other.AccessToken;

public interface AccessTokenServiceINT {
    public String encode(AccessToken accessToken);
    public AccessToken decode(String encodedAccessToken);
}
