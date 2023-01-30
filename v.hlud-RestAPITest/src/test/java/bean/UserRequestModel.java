package bean;

import lombok.*;

import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRequestModel implements RequestModel{
    private int userId;
    private String title;
    private String body;
}