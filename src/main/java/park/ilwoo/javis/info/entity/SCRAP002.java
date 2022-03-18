package park.ilwoo.javis.info.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * scrap001 Vo
 */
@Entity
@Data
@Schema(description = "SCRAP002")
public class SCRAP002 {

    @Id
    @Schema(description = "INDEX", nullable = false, example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long idx;

    @Schema(description = "소득구분", nullable = false, example = "산출세액")
    @JsonAlias("소득구분")
    private String incomeClass;

    @Schema(description = "총사용금액", nullable = false, example = "2000000")
    @JsonAlias("총사용금액")
    private Long totalAmount;
}
