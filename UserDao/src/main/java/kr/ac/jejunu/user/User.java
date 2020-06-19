package kr.ac.jejunu.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data // 예를 들어, 자기 자신을 참조하는 순환참조인 경우에는 @Data를 사용하면 안됨.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userinfo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 지원하는 자동생성 모델을 사용.
    private Integer id;
    private String name;
    private String password;
}
