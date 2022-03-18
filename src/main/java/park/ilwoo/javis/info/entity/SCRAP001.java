package park.ilwoo.javis.info.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * scrap001 Vo
 */
@Entity
@Data
@Schema(description = "SCRAP001")
public class SCRAP001 {

    @Id
    @Schema(description = "INDEX", nullable = false, example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long idx;

    @NotBlank(message = "주민등록번호는 필수입니다.")
    @Schema(description = "주민등록번호", nullable = false, example = "860824-1655068")
    @JsonAlias("주민등록번호")
    private String regNo;

    @Schema(description = "소득내역", nullable = false, example = "급여")
    @JsonAlias("소득내역")
    private String incomeType;

    @Schema(description = "총지급액", nullable = false, example = "2400000")
    @JsonAlias("총지급액")
    private Long totalPay;

    @Schema(description = "업무시작일", nullable = false, example = "2020.10.03")
    @JsonAlias("업무시작일")
    private String jobStartDay;

    @Schema(description = "기업명", nullable = false, example = "(주)활빈당")
    @JsonAlias("기업명")
    private String company;

    @Schema(description = "이름", nullable = false, example = "홍길동")
    @JsonAlias("이름")
    private String name;

    @Schema(description = "지급일", nullable = false, example = "2020.11.02")
    @JsonAlias("지급일")
    private String payDate;

    @Schema(description = "업무종료일", nullable = true, example = "2021.12.15")
    @JsonAlias("업무종료일")
    private String jobEndDay;

    @Schema(description = "소득구분", nullable = false, example = "근로소득(연간)")
    @JsonAlias("소득구분")
    private String incomeClass;

    @Schema(description = "사업자등록번호", nullable = false, example = "012-34-56789")
    @JsonAlias("사업자등록번호")
    private String serviceNum;
}
