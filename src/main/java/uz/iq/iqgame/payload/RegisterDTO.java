package uz.iq.iqgame.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @NotNull(message = "Ism yozing")
    private String name;
    @NotNull(message = "Familiyani yozing")
    private String surname;
    @NotNull(message = "Email kiriting")
    private String email;
    @NotNull(message = "Muassasani tanlang")
    private Long status;
    @NotNull(message = "Parol kiritish zarur")
    private String password;
}
