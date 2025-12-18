package io.getarrays.contactapi.resource;

import static io.getarrays.contactapi.constant.Constant.PHOTO_DIRECTORY;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.getarrays.contactapi.domain.Contact;
import io.getarrays.contactapi.service.ContactService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactResource {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        // return ResponseEntity.ok().body(contactService.createContact(contact));
        return ResponseEntity.created(URI.create("/contacts/<user_ID>")).body(contactService.createContact(contact));
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getContacts(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(contactService.getAllContacts(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContacts(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(contactService.getContact(id));
    }

    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(contactService.uploadPhoto(id, file));
    }

    @GetMapping(path = "/image/{filename}")
    public byte[] getPhoto(@PathVariable String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }

}