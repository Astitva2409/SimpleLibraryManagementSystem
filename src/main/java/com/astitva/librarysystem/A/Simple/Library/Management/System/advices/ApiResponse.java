package com.astitva.librarysystem.A.Simple.Library.Management.System.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
public class ApiResponse<T> {
    private T data;
    private ApiError error;
    private LocalDate timeStamp;

    public ApiResponse() {
        this.timeStamp = LocalDate.now();
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }
}
