package com.bookBazaar.services.implementations;

import com.bookBazaar.models.dto.BookRequestDTO;
import com.bookBazaar.models.entities.BookEntity;
import com.bookBazaar.models.entities.BookRequestEntity;
import com.bookBazaar.models.other.ModelConverter;
import com.bookBazaar.models.repositories.BookRequestRepository;
import com.bookBazaar.services.interfaces.BookRequestServiceINT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookRequestServiceIMPL implements BookRequestServiceINT {
    private final BookRequestRepository bookRequestRepository;
    private final ModelConverter modelConverter = new ModelConverter();

    @Override
    public BookRequestDTO createNewBookRequest(BookRequestDTO bookRequest) {
        Date today = new Date();
        bookRequest.setRequestDate(today);
        BookRequestEntity bookRequestEntity = modelConverter.convertBookRequestDTOToBookRequestEntity(bookRequest);
        BookRequestEntity savedBookRequestEntity = bookRequestRepository.save(bookRequestEntity);
        BookRequestDTO savedBookRequestDTO = modelConverter.convertBookRequestEntityToBookRequestDTO(savedBookRequestEntity);
        return savedBookRequestDTO;
    }

    @Override
    public BookRequestDTO respondToSpecificBookRequestRequest(BookRequestDTO bookRequest) {
        Date today = new Date();
        bookRequest.setResponseDate(today);
        Optional<BookRequestEntity> foundRequestOptional = bookRequestRepository.findById(bookRequest.getId());
        BookRequestEntity foundRequest = foundRequestOptional.get();

        foundRequest.setResponder(bookRequest.getResponder());
        foundRequest.setResponse(bookRequest.getResponse());
        foundRequest.setResponseDate(bookRequest.getResponseDate());
        foundRequest.setStatus("responded");

        BookRequestEntity updatedSavedBookRequest = bookRequestRepository.save(foundRequest);
        return modelConverter.convertBookRequestEntityToBookRequestDTO(updatedSavedBookRequest);
    }

    @Override
    public List<BookRequestDTO> getALLBookRequests() {
        List<BookRequestEntity> allBookRequestsEntities = bookRequestRepository.findAll();
        List<BookRequestDTO> allBookRequestsDTO = new ArrayList<>();
        for(BookRequestEntity requestEntity : allBookRequestsEntities)
        {
            BookRequestDTO bookRequest = modelConverter.convertBookRequestEntityToBookRequestDTO(requestEntity);
            allBookRequestsDTO.add(bookRequest);
        }
        return allBookRequestsDTO;
    }

    @Override
    public List<BookRequestDTO> getALLBookRequestsBySender(String username) {
        List<BookRequestEntity> allBookRequestsEntities = bookRequestRepository.findAllBySenderIgnoreCase(username);
        List<BookRequestDTO> allBookRequestsDTO = new ArrayList<>();
        for(BookRequestEntity requestEntity : allBookRequestsEntities)
        {
            BookRequestDTO bookRequest = modelConverter.convertBookRequestEntityToBookRequestDTO(requestEntity);
            allBookRequestsDTO.add(bookRequest);
        }
        return allBookRequestsDTO;
    }

    @Override
    public List<BookRequestDTO> getALLBookRequestsByResponder(String username) {
        List<BookRequestEntity> allBookRequestsEntities = bookRequestRepository.findAllByResponderIgnoreCase(username);
        List<BookRequestDTO> allBookRequestsDTO = new ArrayList<>();
        for(BookRequestEntity requestEntity : allBookRequestsEntities)
        {
            BookRequestDTO bookRequest = modelConverter.convertBookRequestEntityToBookRequestDTO(requestEntity);
            allBookRequestsDTO.add(bookRequest);
        }
        return allBookRequestsDTO;
    }

    @Override
    public BookRequestDTO getSpecificRequest(String id) {
        Long idConverted = Long.parseLong(id);
        Optional<BookRequestEntity> specificRequestEntityOptional = bookRequestRepository.findById(idConverted);
        BookRequestEntity specificRequestEntity = specificRequestEntityOptional.orElseThrow(() -> new RuntimeException("Request not found"));
        return modelConverter.convertBookRequestEntityToBookRequestDTO(specificRequestEntity);
    }

}