package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    // 웹어플리케이션에서 /hello로 들어오면 아래의 메소드 호출
    @GetMapping("hello")
    public String hello(Model model) {
    model.addAttribute("data", "hello!!"); // key: data, value: hello!!
    return "hello"; // templates에 hello.html을 찾아서 렌더링
        // 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버(View-Resolver)가 화면을 찾아서 처리한다.
        // View-Resolver(화면에 관련된 해결자): 뷰를 찾아주고 템플릿 엔진을 연결해주는 역할
        // 스프링 부트 템플릿 엔진 기본 viewName 매핑
        // `resources:templates/` + {ViewName} + `.html`
        // 해당 html 파일로 모델의 키값을 던져준다.
    }
    
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name); // 앞의 name이 key, 뒤의 name은 parameter로 넘어온 값


        return "hello-template"; // templates에 hello-template.html을 찾아서 렌더링
    }

    // API 방식
    @GetMapping("hello-string")
    @ResponseBody // http에 있는 body 파트를 의미. body 파트에 해당 데이터를 직접 넣겠다는 의미
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    /**
     * <ResponseBody 사용 원리>
     * 기존 template 방식은 viewResolver에게 맞는 템플릿을 요청함
     *ResponseBody 어노테이션이 있으면 http 응답에 그대로 데이터를 넘김
     * 문자가 아니라 객체라면?? 디폴트는 JSON 방식으로 만들어서 HTTP 응답에 반환한다.
     * 즉, HttpMessageConverter가 동작한다. 단순 문자라면 StringConverter 동작, 객체라면 JsonConverter 동작
     * [정리]
     * 1) HTTP의 Body에 문자 내용을 직접 반환
     * 2) viewResolver 대신에 HttpMessageConverter 동작
     * 3-1) 기본 문자처리: StringHttpMessageConverter
     * 3-2) 기본 객체처리: MappingJackson2HttpMessageConverter
     * (Jackson? 객체를 Json으로 바꾸는 라이브러리)
     * 4) byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있다.
     **/

    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // key-value (Json)구조로 나옴.
    }

    static class Hello {
        private String name;


        public String getName() {
            return name;
        }

        public  void  setName(String name) {
            this.name = name;
        }

    }
}
/*
 * 정적 컨텐츠 - 그냥 파일을 다 내려줌
 * MVC & Template Eng - Template 엔진을 Model-View-Controller 방식으로 나눠서 HTML을 좀더 프로그래밍하여 렌더링
 * API - 객체를 반환하는 형태(HttpConverter를 통해 Json 형태로 바꿔서 반환, view 없이 바로 http response에 값을 넣어서 반환)
 */