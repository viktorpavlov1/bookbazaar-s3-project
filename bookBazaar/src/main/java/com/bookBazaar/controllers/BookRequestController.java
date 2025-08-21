package com.bookBazaar.controllers;

import com.bookBazaar.models.dto.BookDTO;
import com.bookBazaar.models.dto.BookRequestDTO;
import com.bookBazaar.services.interfaces.BookRequestServiceINT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {})
public class BookRequestController {

    private final BookRequestServiceINT bookRequestService;
    @PostMapping("/new")
    public ResponseEntity<BookRequestDTO> createNewRequest(@RequestBody BookRequestDTO bookRequest)
    {
        BookRequestDTO response = bookRequestService.createNewBookRequest(bookRequest);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/respond")
    public ResponseEntity<BookRequestDTO> respondToRequest(@RequestBody BookRequestDTO bookRequest)
    {
        BookRequestDTO response = bookRequestService.respondToSpecificBookRequestRequest(bookRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookRequestDTO>> getAllRequests()
    {
        List<BookRequestDTO> response = bookRequestService.getALLBookRequests();
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/sender/{username}")
    public ResponseEntity<List<BookRequestDTO>> getAllRequestsBySender(@PathVariable(value="username") final String username)
    {
        List<BookRequestDTO> response = bookRequestService.getALLBookRequestsBySender(username);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/responder/{username}")
    public ResponseEntity<List<BookRequestDTO>> getAllRequestsByResponder(@PathVariable(value="username") final String username)
    {
        List<BookRequestDTO> response = bookRequestService.getALLBookRequestsByResponder(username);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/specific/{id}")
    public ResponseEntity<BookRequestDTO> getSpecificRequest(@PathVariable(value="id") final String id)
    {
        BookRequestDTO response = bookRequestService.getSpecificRequest(id);
        return ResponseEntity.ok().body(response);
    }
}