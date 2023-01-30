package bean;

import lombok.*;

import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Geo {
    private String lat;
    private String lng;
}