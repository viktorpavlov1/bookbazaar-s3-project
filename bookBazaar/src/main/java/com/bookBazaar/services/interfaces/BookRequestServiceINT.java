package com.bookBazaar.services.interfaces;

import com.bookBazaar.models.dto.BookRequestDTO;

import java.util.List;

public interface BookRequestServiceINT {
    public BookRequestDTO createNewBookRequest(BookRequestDTO bookRequest);
    public BookRequestDTO respondToSpecificBookRequestRequest(BookRequestDTO bookRequest);
    public List<BookRequestDTO> getALLBookRequests();
    public List<BookRequestDTO> getALLBookRequestsBySender(String username);
    public List<BookRequestDTO> getALLBookRequestsByResponder(String username);
    public BookRequestDTO getSpecificRequest(String id);
}