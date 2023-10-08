package uz.iq.iqgame.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.iq.iqgame.entity.tmp.AbsLongEntity;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Language extends AbsLongEntity {
    @Column(nullable = false)
    private String programmingLanguageName;
}
