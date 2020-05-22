package kr.ac.jejunu.user;

import lombok.*;

@Data // 예를 들어, 자기 자신을 참조하는 순환참조인 경우에는 @Data를 사용하면 안됨.
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String password;
}
