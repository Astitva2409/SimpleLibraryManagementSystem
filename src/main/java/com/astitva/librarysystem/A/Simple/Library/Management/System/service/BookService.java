package com.astitva.librarysystem.A.Simple.Library.Management.System.service;

import com.astitva.librarysystem.A.Simple.Library.Management.System.dto.AuthorDTO;
import com.astitva.librarysystem.A.Simple.Library.Management.System.dto.BookDTO;
import com.astitva.librarysystem.A.Simple.Library.Management.System.entities.AuthorEntity;
import com.astitva.librarysystem.A.Simple.Library.Management.System.repository.AuthorRepository;
import com.astitva.librarysystem.A.Simple.Library.Management.System.service.AuthorService;
import com.astitva.librarysystem.A.Simple.Library.Management.System.entities.BookEntity;
import com.astitva.librarysystem.A.Simple.Library.Management.System.exceptions.ResourceNotFoundException;
import com.astitva.librarysystem.A.Simple.Library.Management.System.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorService authorService;

    private boolean isExistsById(Long id) {
        boolean exists = bookRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Book not found with id "+id);
        return true;
    }

    public BookDTO createNewBook(BookDTO inputAuthor) {
        BookEntity bookEntity = modelMapper.map(inputAuthor, BookEntity.class);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return modelMapper.map(savedBookEntity, BookDTO.class);
    }

    public List<BookDTO> getAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookEntities
                .stream()
                .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class))
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        isExistsById(id);
        BookEntity bookEntity = bookRepository.findById(id).get();
        return modelMapper.map(bookEntity, BookDTO.class);
    }

    public boolean deleteBookById(Long id) {
        isExistsById(id);
        bookRepository.deleteById(id);
        return true;
    }

    public BookDTO updateBookById(Map<String, Object> updates, Long id) {
        isExistsById(id);
        BookEntity bookEntity = bookRepository.findById(id).get();
        updates.forEach((field, value) -> {
            Field field1 = ReflectionUtils.findField(BookEntity.class, field);
            if (field1 != null) {
                field1.setAccessible(true);
                Class<?> fieldType = field1.getType();
                if (fieldType.equals(LocalDate.class) && value instanceof String) {
                    LocalDate dateValue = LocalDate.parse((String) value);
                    ReflectionUtils.setField(field1, bookEntity, dateValue);
                } else {
                    ReflectionUtils.setField(field1, bookEntity, value);
                }
            }
        });
        bookRepository.save(bookEntity);
        return modelMapper.map(bookEntity, BookDTO.class);
    }

    public List<BookDTO> getBooksByTitle(String title) {
        List<BookEntity> bookEntities = bookRepository.findByTitleContainingIgnoreCase(title);
        if (bookEntities.isEmpty()) throw new ResourceNotFoundException("books not found with title: "+title);
        return bookEntities
                .stream()
                .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class))
                .collect(Collectors.toList());
    }

    public List<BookDTO> getAllBooksPublishedAfterDate(LocalDate date) {
        List<BookEntity> bookEntities = bookRepository.findByPublishedDateGreaterThanAndEquals(date);
        if (bookEntities.isEmpty())
            throw new ResourceNotFoundException("No books were published after the given date: "+date);
        return bookEntities
                .stream()
                .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class))
                .collect(Collectors.toList());
    }

    public BookEntity assignAuthorToBook(Long bookId, Long authorId) {
        isExistsById(bookId);
        authorService.isExistsById(authorId);
        BookEntity bookEntity = bookRepository.findById(bookId).get();
        AuthorEntity authorEntity = authorService.findByAuthorId(authorId);
        bookEntity.setAuthor(authorEntity);
        bookRepository.save(bookEntity);
        return bookEntity;
    }
}
