package com.astitva.librarysystem.A.Simple.Library.Management.System.service;

import com.astitva.librarysystem.A.Simple.Library.Management.System.dto.BookDTO;
import com.astitva.librarysystem.A.Simple.Library.Management.System.entities.AuthorEntity;
import com.astitva.librarysystem.A.Simple.Library.Management.System.entities.BookEntity;
import com.astitva.librarysystem.A.Simple.Library.Management.System.exceptions.ResourceNotFoundException;
import com.astitva.librarysystem.A.Simple.Library.Management.System.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final int PAGE_SIZE = 10;

    public BookEntity findBookById(Long id) {
        boolean exists = bookRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Book not found with id "+id);
        return bookRepository.findById(id).get();
    }

    public Pageable getPageableAndSorting(String sortBy, Integer pageNumber) {
        return PageRequest.of(pageNumber,
                PAGE_SIZE,
                Sort.by(Sort.Order.asc(sortBy)));
    }

    public BookDTO createNewBook(BookDTO inputBook) {
        authorService.findByAuthorId(inputBook.getAuthorId());
        BookEntity bookEntity = modelMapper.map(inputBook, BookEntity.class);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return modelMapper.map(savedBookEntity, BookDTO.class);
    }

    public List<BookDTO> getAllBooks(String sortBy, Integer pageNumber) {
        Pageable pageable = getPageableAndSorting(sortBy, pageNumber);
        List<BookEntity> bookEntities = bookRepository.findAll(pageable).getContent();
        return bookEntities
                .stream()
                .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class))
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        BookEntity bookEntity = findBookById(id);
        return modelMapper.map(bookEntity, BookDTO.class);
    }

    public boolean deleteBookById(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    public BookDTO updateBookById(Map<String, Object> updates, Long id) {
        BookEntity bookEntity = findBookById(id);
        updates.forEach((field, value) -> {
            if ("authorId".equals(field)) {
                // Update author relationship
                Long authorId = Long.valueOf(value.toString());
                AuthorEntity authorEntity = authorService.findByAuthorId(authorId);
                bookEntity.setAuthor(authorEntity);
            } else {
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
            }
        });
        bookRepository.save(bookEntity);
        return modelMapper.map(bookEntity, BookDTO.class);
    }

    public List<BookDTO> getBooksByTitle(String title, String sortBy, Integer pageNumber) {
        Pageable pageable = getPageableAndSorting(sortBy, pageNumber);
        List<BookEntity> bookEntities = bookRepository.findByTitleContainingIgnoreCase(title, pageable);
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
}
