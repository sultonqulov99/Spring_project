package uz.iq.iqgame.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.iq.iqgame.entity.Groups;
import uz.iq.iqgame.entity.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOwnDataDTO {
    private String name;
    private String surname;
    private String email;
    private StatusDTO status;
    private Groups groups;
    private Long userBall;
    private Boolean isFinished;
}
