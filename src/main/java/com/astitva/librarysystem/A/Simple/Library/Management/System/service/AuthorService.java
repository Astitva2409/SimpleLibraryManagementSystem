package com.astitva.librarysystem.A.Simple.Library.Management.System.service;

import com.astitva.librarysystem.A.Simple.Library.Management.System.dto.AuthorDTO;
import com.astitva.librarysystem.A.Simple.Library.Management.System.entities.AuthorEntity;
import com.astitva.librarysystem.A.Simple.Library.Management.System.exceptions.ResourceNotFoundException;
import com.astitva.librarysystem.A.Simple.Library.Management.System.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final int PAGE_SIZE = 10;

    public AuthorEntity findByAuthorId(Long id) {
        boolean exists = authorRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Author not found with id "+id);
        return authorRepository.findById(id).get();
    }

    public Pageable getPageableAndSorting(String sortBy, Integer pageNumber) {
        return PageRequest.of(pageNumber,
                PAGE_SIZE,
                Sort.by(Sort.Order.asc(sortBy)));
    }

    public AuthorDTO createNewAuthor(AuthorDTO inputAuthor) {
        AuthorEntity authorEntity = modelMapper.map(inputAuthor, AuthorEntity.class);
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
        return modelMapper.map(savedAuthorEntity, AuthorDTO.class);
    }

    public List<AuthorDTO> getAllAuthors(String sortBy, Integer pageNumber) {
        Pageable pageable = getPageableAndSorting(sortBy, pageNumber);
        List<AuthorEntity> authorEntities = authorRepository.findAll(pageable).getContent();
        return authorEntities
                .stream()
                .map(authorEntity -> modelMapper.map(authorEntity, AuthorDTO.class))
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        AuthorEntity authorEntity = findByAuthorId(id);
        return modelMapper.map(authorEntity, AuthorDTO.class);
    }

    public boolean deleteAuthorById(Long id) {
        findByAuthorId(id);
        authorRepository.deleteById(id);
        return true;
    }

    public AuthorDTO updateAuthorById(Map<String, Object> updates, Long id) {
        AuthorEntity authorEntity = findByAuthorId(id);
        updates.forEach((field, value) -> {
            Field field1 = ReflectionUtils.findField(AuthorEntity.class, field);
            if (field1 != null) {
                field1.setAccessible(true);
                Class<?> fieldType = field1.getType();
                if (fieldType.isEnum() && value instanceof String) {
                    Object enumValue = Enum.valueOf((Class<Enum>) fieldType, (String) value);
                    ReflectionUtils.setField(field1, authorEntity, enumValue);
                } else {
                    ReflectionUtils.setField(field1, authorEntity, value);
                }
            }
        });
        authorRepository.save(authorEntity);
        return modelMapper.map(authorEntity, AuthorDTO.class);
    }

    public List<AuthorDTO> getAuthorByName(String name) {
        List<AuthorEntity> authorEntities = authorRepository.findByNameIgnoreCase(name);
        if (authorEntities.isEmpty()) throw new ResourceNotFoundException("Author not found with name: "+name);
        return authorEntities
                .stream()
                .map(authorEntity -> modelMapper.map(authorEntity, AuthorDTO.class))
                .collect(Collectors.toList());
    }
}
