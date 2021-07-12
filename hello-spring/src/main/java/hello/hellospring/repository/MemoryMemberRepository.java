package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

// interface를 implements한다.
public class MemoryMemberRepository implements MemberRepository{

    // 메모리에 저장하는 방식으로 우선 채택.. (나중에 데이터베이스에 저장)
    private static Map<Long, Member> store = new HashMap<>(); // 이처럼 공유될땐 원래 Concurrent HashMap을 쓰는 것이 좋다.
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // store에 넣기 전에 member의 Id 세팅
        store.put(member.getId(), member); // store에 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // store에서 id를 꺼내는 내용, NULL이 나올 수 있으므로 Optional로 감싼다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
       return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();

        // java8 lambda 이용.
        // store를 loop 돌린다. member.getName()이 파라미터로 넘어온 name과 같은지 확인
        // findAny는 하나라도 찾는 것.
        // 끝까지 돌아도 없으면 Optional이 null을 감싸서 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        // store.values() == member
        // member를 리스트로 반환해준다.
    }
}
/*
    어떻게 동작하는지 검증하기 위해서 테스트 케이스를 작성해야 한다.
    개발한 기능을 실행해서 테스트할 때 자바의 main 메서드를 통해서 실행하거나, 웹 애플리케이션의 컨트롤러를 통해서
    해당 기능을 실행한다. 이러한 방법은 준비하고 실행하는데 오래 걸리고, 반복 실행하기 어렵고 여러 테스트를 한번에
    실행하기 어렵다는 단점이 있다. 자바는 JUnit이라는 프레임워크로 테스트를 실행해서 이러한 문제를 해결한다.
    /test/java/package/ 에 같은 이름의 package를 만들어준다.
    즉, 현재 이 파일의 package는 repository이므로 해당 경로에도 repository package를 만들어주자.
    그런 후, 관례상 MemoryMemberRepositoryTest라는 Class를 만들어준다.
 */
