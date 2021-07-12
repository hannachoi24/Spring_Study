package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원 저장
    Optional<Member> findById(Long id); // id로 회원을 찾기
    Optional<Member> findByName(String name);
    List<Member> findAll(); // 지금 까지 저장된 모든 회원들의 정보를 반환
}
