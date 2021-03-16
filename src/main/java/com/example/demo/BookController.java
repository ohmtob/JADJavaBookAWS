package com.example.demo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class BookController {

    String address ="http://localhost:8080/book";

    @Autowired
    BookRepository repository;


    @GetMapping(path = "/book")
    //   @CrossOrigin("http://127.0.0.1:8080")
    @CrossOrigin()
    List<Book> getAll(){
        var l = new ArrayList<Book>();
        for(Book r : repository.findAll())
        {
            l.add(r);
        }
        return l;
    }


    @GetMapping(path ="/book/{id}")
    @CrossOrigin()
    Book getSingle(@PathVariable Long id){
        return  repository.findById(id).get();
    }


    @PostMapping(path = "/book", consumes = "application/json", produces = "application/json")
    @CrossOrigin()
    ResponseEntity<Object> add(@RequestBody Book b){
        repository.save(b);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path( "/{id}" )
                .buildAndExpand( b.getId() )
                .toUri();
        return  ResponseEntity.created( location ).build();

    }

    @PutMapping(path = "/book/{id}", consumes = "application/json", produces = "application/json")
    @CrossOrigin()
    Book update(@PathVariable Long id, @RequestBody Book updateBook){
        Book dbBook = repository.findById(id).get();
        dbBook.setTitle(updateBook.getTitle());
        dbBook.setAuthor(updateBook.getAuthor());
        dbBook.setPrice(updateBook.getPrice());
        repository.save(dbBook);
        return dbBook;
    }

    @DeleteMapping(path = "/book/{id}")
    @CrossOrigin()
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }


}
