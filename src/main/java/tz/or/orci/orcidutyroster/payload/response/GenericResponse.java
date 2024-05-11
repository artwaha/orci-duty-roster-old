package tz.or.orci.orcidutyroster.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<Entity> {
    private int status;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private List<Entity> data;
}
