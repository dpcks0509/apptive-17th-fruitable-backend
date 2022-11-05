package apptive.fruitable.login.controller;

import apptive.fruitable.login.dto.MemberDto;
import apptive.fruitable.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String savePage() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDto memberDto, @Valid MemberDto validMember, BindingResult bindingResult) throws Exception {

        if (!validMember.getPwd().equals(validMember.getPwd2())) {
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            System.out.println("비밀번호 불일치");
            return "save"; // 회원가입 실패 (패스워드 불일치)
        }
        memberService.save(memberDto);
        System.out.println("회원가입 성공");
        return "main"; // 회원가입 성공
    }

    @GetMapping("/idDuplicate")
    public String idDuplicate(@Valid MemberDto validMember, BindingResult bindingResult) {
        if(memberService.idDuplicate(validMember.getId())) {
            bindingResult.rejectValue("id", "IdInCorrect", "아이디가 중복됩니다.");
            System.out.println("아이디 중복");
        }
        return "save";
    }

    @GetMapping("/nameDuplicate")
    public String nameDuplicate(@Valid MemberDto validMember, BindingResult bindingResult) {
        if(memberService.nameDuplicate(validMember.getName())) {
            bindingResult.rejectValue("name", "NameInCorrect", "닉네임이 중복됩니다.");
            System.out.println("닉네임 중복");
        }
        return "save";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession session) {
        MemberDto loginResult = memberService.login(memberDto);

        if(loginResult != null) {
            session.setAttribute("id", loginResult.getId());
            session.setAttribute("name", loginResult.getName());
            session.setAttribute("pwd", loginResult.getPwd());
            System.out.println("로그인 성공");
            return "main"; // 로그인 성공
        } else {
            System.out.println("로그인 실패");
            return "login"; // 로그인 실패
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        System.out.println("로그아웃");
        return "main"; //로그아웃
    }

    @GetMapping("/delete")
    public String deletePage() {
        return "delete";
    }

    // 비밀번호, 비밀번호2 재확인 후 일치하면 회원 탈퇴
    @PostMapping("/delete")
    public String delete(@RequestBody MemberDto deleteMember, HttpSession session,
                         BindingResult bindingResult) throws Exception {
        String sessionId = (String)session.getAttribute("id");
        String sessionPwd = (String)session.getAttribute("pwd");

        if((deleteMember.getPwd().equals(deleteMember.getPwd2())) && (deleteMember.getPwd().equals(sessionPwd))) {
            memberService.delete(sessionId);
            session.invalidate();
            System.out.println("탈퇴 완료");
        } else {
            bindingResult.rejectValue("pwd", "PwdInCorrect", "비밀번호 불일치");
            System.out.println("비밀번호 불일치");
        }

        return "main"; //탈퇴 완료
    }



//    @GetMapping("/emailDuplicate")
//    public String emailDuplicate(@Valid MemberDto validMember, BindingResult bindingResult) {
//        if(memberService.emailDuplicate(validMember.getEmail())) {
//            bindingResult.rejectValue("email", "EmailInCorrect", "이메일이 중복됩니다.");
//            System.out.println("이메일 중복");
//        }
//        return "save";
//    }

//    //아이디 중복확인
//    @GetMapping("/{id}/Duplicate")
//    public ResponseEntity<Boolean> idDuplicate(@PathVariable String id) {
//        return ResponseEntity.ok(memberService.idDuplicate(id));
//    }
//
//    //닉네임 중복확인
//    @GetMapping("/{name}/Duplicate")
//    public ResponseEntity<Boolean> nameDuplicate(@PathVariable String name) {
//        return ResponseEntity.ok(memberService.nameDuplicate(name));
//    }
//
//    //이메일 중복확인
//    @GetMapping("/{email}/Duplicate")
//    public ResponseEntity<Boolean> emailDuplicate(@PathVariable String email) {
//        return ResponseEntity.ok(memberService.nameDuplicate(email));
//    }
}
