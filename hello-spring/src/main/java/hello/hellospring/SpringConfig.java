package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // Spring이 스프링 빈에 등록하라는 것으로 인식
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository()); // memberService를 이 부분 호출해서 스프링 빈에 등록을 해준다.
    }

    @Bean
    public MemberRepository memberRepository() { // 인터페이스(MemberRepository), 구현체(MemoryMemberRepository)
        return new MemoryMemberRepository(); // 인터페이스에는 new가 안됨
    }
}

/* Spring이 memberService와 memberRepository를 스프링 빈에 등록해준다.
스프링 빈에 등록 되어 있는 memberRepository를 MemberService에 넣어준다.
Controller는 그대로 @Controller와 @Autowired 사용
 */