package hello.hellospring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // JPA와 Mapping -> JPA가 관리하는 엔티티가 됨.
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // db가 자동으로 id를 생성해 주는 것을 의미
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
