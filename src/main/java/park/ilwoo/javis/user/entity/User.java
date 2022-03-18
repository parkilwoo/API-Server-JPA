package park.ilwoo.javis.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import park.ilwoo.javis.info.entity.SCRAP001;
import park.ilwoo.javis.info.entity.SCRAP002;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

/**
 * User Entity
 */
@Entity
@Data
@Schema(description = "유저")
@SequenceGenerator(
        name="USER_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="USER_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@ApiModel(value = "유저 정보", description = "아이디, 비밀번호, 이름, 주민등록번호를 갖는 VO")
public class User implements UserDetails {

    @Id
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
            generator="USER_SEQ_GEN"          //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정
    )
    @Schema(description = "INDEX", nullable = false, example = "1")
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @JsonIgnore
    private Long idx;

    @NotBlank(message = "ID는 필수입니다.")
    @Length(max = 10, message = "ID는 최대 10자리 입니다.")
    @Schema(description = "ID", nullable = false, example = "loveelf1", defaultValue = "유저ID")
    @ApiModelProperty(name = "유저ID",  example = "loveelf1")
    private String userId;

    @NotBlank(message = "PWD는 필수입니다.")
    @Length(min = 2, max = 24, message = "PWD는 2~24자리 입니다.")
    @Schema(description = "PWD", nullable = false, example = "123456")
    @ApiModelProperty(name = "유저PWD",  example = "123456")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    @Schema(description = "이름", nullable = false, example = "홍길동")
    @ApiModelProperty(name = "유저이름",  example = "홍길동")
    private String name;

    @NotBlank(message = "주민등록번호는 필수입니다.")
    @Schema(description = "주민등록번호", nullable = false, example = "860824-1655068")
    @ApiModelProperty(name = "주민등록번호",  example = "860824-1655068")
    private String regNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    @JsonIgnore
    private SCRAP001 scrap001;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    @JsonIgnore
    private SCRAP002 scrap002;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.userId;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }
}
