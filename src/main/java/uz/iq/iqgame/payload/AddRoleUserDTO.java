package uz.iq.iqgame.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRoleUserDTO {
    @NotNull(message = "Ism yozing")
    private String name;
    @NotNull(message = "group tanlanishi kerak")
    private Long groupId;
    @NotNull(message = "Familiyani yozing")
    private String surname;
    @NotNull(message = "Email kiriting")
    private String email;
    @NotNull(message = "Parol kiritish zarur")
    private String password;
    @NotNull(message = "Role kiritilishi kerak")
    private String systemRoleName;
}
