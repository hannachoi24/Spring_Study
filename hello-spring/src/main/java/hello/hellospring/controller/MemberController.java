package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController { // Controller 어노테이션을 보고 spring이 뜰 때 MemberController 객체를 생성해 넣어줌 그리고 spring이 관리함

    private final MemberService memberService;

    @Autowired // @Autowired가 있으면 memberService를 Spring이 Spring 컨테이너에 있는 MemberService와 연결시켜 준다.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm"; // viewresolver를 통해 선택됨
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) { // MemberForm의 name에 사용자가 입력한 name 값이 들어옴
        Member member = new Member();
        member.setName(form.getName());
        
        memberService.join(member);
        
        return  "redirect:/"; // 회원 가입이 끝났으면 홈화면으로 이동
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members); // members를 model에 담아서 화면에 보냄
        return "members/memberList";
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