package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

// JPA를 사용하기 위해서는 항상 트랜잭션이 있어야 한다.
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em; // JPA는 EntityManager로 모든게 동작한다.
                                    // SpringBoot가 build.gradle을 통해 실행된 jpa를 확인하고 EntityManager를 제공해준다.
                                    // 결론적으로 JPA를 쓰려면 EntityManager를 주입 받아야 된다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // JPA가 알아서 INSERT 쿼리 만들어서 DB에 집어넣고 id 또한 세팅해준다.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // find(타입, 식별자)
        return Optional.ofNullable(member);
    }

    // PK 기반이 아니라면 JPQL이라는 객체지향 쿼리를 사용해야 한다.
    @Override
    public Optional<Member> findByName(String name) {
         List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                 .setParameter("name", name)
                 .getResultList();
         return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        //    List<Member> result = em.createQuery("select m from Member m", Member.class)
        //                .getResultList();
        //        return result;
        // 위의 코드 입력 후 Ctrl + Alt + N 누르면 Inline이 되어 아래 처럼 된다.
        return em.createQuery("select m from Member m", Member.class).getResultList();
        // 객체를 대상으로 쿼리를 날린다. Member 엔티티를 조회하고 엔티티 자체를 select 한다.

    }
}
