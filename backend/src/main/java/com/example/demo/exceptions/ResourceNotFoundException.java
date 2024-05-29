package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(Class resourceName, Long id) {
        super(new StringBuilder("The resource ")
                .append(resourceName.getName())
                .append(" with Id: ")
                .append(id)
                .append(" don't exist")
                .toString()
        );
    }

    public ResourceNotFoundException(Class resourceName, String email) {
        super(new StringBuilder("The resource ")
                .append(resourceName.getName())
                .append(" with Email: ")
                .append(email)
                .append(" don't exist")
                .toString()
        );
    }
}
