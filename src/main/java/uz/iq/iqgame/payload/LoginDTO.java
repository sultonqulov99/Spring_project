package uz.iq.iqgame.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotNull(message = "email kiritilishi shart")
    private String email;
    @NotNull(message = "password kiritilishi shart")
    private String password;
}
