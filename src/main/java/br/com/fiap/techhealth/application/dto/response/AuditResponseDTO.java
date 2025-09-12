package br.com.fiap.techhealth.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditResponseDTO {

    private String createBy;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            timezone = "America/Sao_Paulo"
    )
    private Instant createAt;

    private String updateBy;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            timezone = "America/Sao_Paulo"
    )
    private Instant updateAt;

}
