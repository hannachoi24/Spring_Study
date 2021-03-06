package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    /* 변경 전
    MemberService memberService = new MemberService();
    // 메소드를 클리어 해주기 위해서 repository가 필요하다.
    MemberRepository memberRepository = new MemoryMemberRepository();
    */

    /** 하지만 이렇게 되면 service에서 호출한 repository와 지금 위에서 작성한 repository는
     서로 다른 repository라는 문제가 발생한다.
     같은 인스턴스를 사용하려면, MemberService에서 Constructor DI를 사용해야 한다.
     */

    // 변경 후
    MemberService memberService;
    MemoryMemberRepository memberRepository;


    // 각 테스트를 실행하기 전에
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    // Test 코드는 한글로 해도 큰 문제 없음
    // given - when - then => 상황이 주어지고, 실행했을 때 결과가 나와야 한다.
    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        // 회원가입한것이 리포지토리에 있는게 맞는지 확인하기 위한 검증
        // Ctrl+Alt+V 하면 Optional 포맷이 만들어짐
        Member findMember = memberService.findOne(saveId).get();
        // option + Enter -> static으로 만들 수 있다.
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring"); // member1의 이름과 같에 설정

        // when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        /*  // Ctrl + / = comment
        try {
            memberService.join(member2);    // 예외 발생
            fail("예외가 발생해야 합니다.");
        }catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        // then


    }

    @Test
    void findMembers() {

    }

    @Test
    void findOne() {

    }

}