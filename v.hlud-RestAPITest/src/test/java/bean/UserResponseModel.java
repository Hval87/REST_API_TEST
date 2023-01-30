package bean;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserResponseModel {
    private int userId;
    private int id;
    private String title;
    private String body;
}