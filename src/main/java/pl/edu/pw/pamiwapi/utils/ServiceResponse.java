package pl.edu.pw.pamiwapi.utils;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> {
    private T data;
    private boolean wasSuccessful;
    private String message;
}
