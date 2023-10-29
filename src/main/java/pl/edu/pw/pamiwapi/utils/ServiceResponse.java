package pl.edu.pw.pamiwapi.utils;

import jakarta.validation.ConstraintViolation;
import lombok.*;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> {
    private T data;
    private boolean wasSuccessful;
    private String message;

    public static <T> ServiceResponse<T> createInvalidResponse(Set<ConstraintViolation<T>> violations) {
        var message = new StringBuilder();

        for (var v : violations) {
            message.append(v.getMessage()).append(" ");
        }

        return ServiceResponse.<T>builder()
                .message(message.toString())
                .build();
    }
}
