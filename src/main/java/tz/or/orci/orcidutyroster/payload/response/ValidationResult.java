package tz.or.orci.orcidutyroster.payload.response;

import lombok.Data;

@Data
public class ValidationResult {
    private boolean valid;
    private String message;
}
