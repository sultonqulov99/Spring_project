package uz.iq.iqgame.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    private Long id;
    private String name;
    private String timeExpired;
    private Long groupId;
}
