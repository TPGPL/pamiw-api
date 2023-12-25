package pl.edu.pw.pamiwapi.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    @Builder.Default
    private String tokenType = "Bearer";
    private String token;
    private Date issuedAt;
    private Date expireAt;
}
