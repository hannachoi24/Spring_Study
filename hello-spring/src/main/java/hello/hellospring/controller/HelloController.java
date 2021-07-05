package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    // 웹어플리케이션에서 /hello로 들어오면 아래의 메소드 호출
    @GetMapping("hello")
    public String hello(Model model) {
    model.addAttribute("data", "hello!!"); // key: data, value: hello!!
    return "hello"; // templates에 hello.html을 찾아서 렌더링
    }
}
