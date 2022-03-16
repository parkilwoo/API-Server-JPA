package park.ilwoo.javis.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

/**
 * User Entity
 */
@Entity
@Data
@Schema(description = "유저")
public class User implements UserDetails {

    @Id
//    @Column(nullable = false, length = 20, columnDefinition = "comment '유저 ID'")
    @NotBlank(message = "ID는 필수입니다.")
    @Schema(description = "ID", nullable = false, example = "loveelf1")
    private String userId;

//    @Column(nullable = false, length = 100, columnDefinition = "comment '유저 패스워드'")
    @NotBlank(message = "PWD는 필수입니다.")
    @Schema(description = "PWD", nullable = false, example = "123456")
    private String password;

//    @Column(nullable = false, length = 30, columnDefinition = "comment '유저 이름'")
    @NotBlank(message = "이름은 필수입니다.")
    @Schema(description = "이름", nullable = false, example = "홍길동")
    private String name;

//    @Column(unique = true, nullable = false, length = 14, columnDefinition = "comment '유저 주민등록번호'")
    @Length(min = 14, max = 14, message = "올바른 주민등록번호가 아닙니다.")
    @NotBlank(message = "주민등록번호는 필수입니다.")
    @Schema(description = "주민등록번호", nullable = false, example = "860824-1655068")
    private String regNo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
