package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    /*
    스프링이 application.properties파일에 있는 정보들을 보고 자체적으로 빈을 생성 (db에 연결할 수 있는, 정보가 있는 빈을 생성)
    private DataSource dataSource;
    @Autowired  //DataSource를 spring에서 제공해준다
    public SpringConfig(DataSource dataSource) { 주입
        this.dataSource = dataSource;
    }
*/

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    // Spring이 스프링 빈에 등록하라는 것으로 인식
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository()); // memberService를 이 부분 호출해서 스프링 빈에 등록을 해준다.
    }

    @Bean
    public MemberRepository memberRepository() { // 인터페이스(MemberRepository), 구현체(MemoryMemberRepository)

        // return new MemoryMemberRepository(); // 인터페이스에는 new가 안됨
        // return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}

/* Spring이 memberService와 memberRepository를 스프링 빈에 등록해준다.
스프링 빈에 등록 되어 있는 memberRepository를 MemberService에 넣어준다.
Controller는 그대로 @Controller와 @Autowired 사용
 */