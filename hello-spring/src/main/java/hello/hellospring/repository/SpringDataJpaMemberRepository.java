package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
    // JpaRepository를 받고 있으면 SpringDataJpaMemberRepository이 구현체를 자동으로 만들어준다.
    // 즉, Spring Bean에 자동으로 등록한다.
}

/**
 * 스프링 데이터 JPA가 SpringDataJpaMemberRepository를 스프링 빈으로 자동 등록해준다.
 * JpaRepository에서 기본적인 메소드를 다 제공해준다. save, findById 등.. 기본적인 CRUD를 제공해준다.
 * 비즈니스에 따라 name, address, email 등 찾고 싶은 데이터가 매우 다양하다. 이런것들은 공통으로 제공하기가 어렵다.
 */