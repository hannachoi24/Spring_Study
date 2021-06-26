package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();
    // 만약 MemoryMemberRepository 전에 Test 파일을(현재 파일)을 먼저 만들어진다면? -> 테스트 주도 개발 = TDD

    // 테스트가 끝날 때마다 store를 clear하므로 충돌이 발생하지 않는다.
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get(); // Optional에서 값만 꺼낼때는 .get()으로 꺼낼 수 있다.
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2); // 두 객체만 넣었으므로 길이는 2
    }


}

/*
    Test는 클래스 레벨에서 Run 할 수도 있고,
    상위 패키지에서 Run 하면 전체 클래스를 다 Test 할 수 있다.
    테스트를 하나 끝내면 데이터를 클리어 해줘야 다른 테스트 케이스 간에 충돌이 발생하지 않는다.
 */
