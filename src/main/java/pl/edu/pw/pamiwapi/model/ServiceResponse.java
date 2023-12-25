package pl.edu.pw.pamiwapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import lombok.*;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
