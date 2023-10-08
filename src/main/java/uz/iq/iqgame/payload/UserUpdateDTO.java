package uz.iq.iqgame.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {
    private String name;
    private String surname;
    private String oldPassword;
    private String password;
    private Long statusId;
    private Long groupsId;
}
