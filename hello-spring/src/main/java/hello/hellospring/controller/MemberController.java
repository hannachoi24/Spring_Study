package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController { // Controller 어노테이션을 보고 spring이 뜰 때 MemberController 객체를 생성해 넣어줌 그리고 spring이 관리함

    private final MemberService memberService;

    @Autowired // @Autowired가 있으면 memberService를 Spring이 Spring 컨테이너에 있는 MemberService와 연결시켜 준다.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}

/* 생성자로 등록한 방법
Spring 컨테이너가 Controller를 객체로 생성할 때, 생성자 호출을 하게 되는데 생성자에 @Autowired가있으면
memberService를 Spring이 Spring 컨테이너에 있는 MemberService와 연결시켜준다.
하지만 이렇게 자동으로 연결되기 위해서 Service에서도 @Service Annotation을 표시해줘야 한다.(MemberService.java 파일 상단에)
Spring이 Service 어노테이션(@Service)을 보고 Spring 컨테이너에 MemberService를 등록시켜준다.
즉, Spring 컨테이너에서 Service를 객체로 관리하게 된다.
마찬가지로 Repository는 @Repository를 작성해 컨테이너에서 관리될 수 있도록 한다.
 */