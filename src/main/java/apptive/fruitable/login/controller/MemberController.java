package apptive.fruitable.login.controller;

import apptive.fruitable.login.dto.MemberDto;
import apptive.fruitable.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute MemberDto memberDto) {

        if (!(memberDto.getPwd().equals(memberDto.getPwd2()))) {
            return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
        }
        memberService.save(memberDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }

    @GetMapping("/nameDuplicate")
    public ResponseEntity<?> nameDuplicate(@ModelAttribute MemberDto memberDto) {
        if (memberService.nameDuplicate(memberDto.getName())) {
            return new ResponseEntity<>("닉네임 중복", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("닉네임 사용가능", HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute MemberDto memberDto, HttpSession session) {
        MemberDto loginResult = memberService.login(memberDto);

        if (loginResult != null) {
            session.setAttribute("id", loginResult.getId());
            session.setAttribute("email", loginResult.getEmail());
            session.setAttribute("name", loginResult.getName());
            session.setAttribute("pwd", loginResult.getPwd());
            session.setAttribute("role", loginResult.getRole());
            return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
    }

    // 비밀번호, 비밀번호2 재확인 후 일치하면 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@ModelAttribute MemberDto deleteMember, HttpSession session) throws Exception {

        String sessionEmail = (String) session.getAttribute("email");
        String sessionPwd = (String) session.getAttribute("pwd");

        if ((deleteMember.getPwd().equals(deleteMember.getPwd2())) && (deleteMember.getPwd().equals(sessionPwd))) {
            memberService.delete(sessionEmail);
            session.invalidate();
            return new ResponseEntity<>("탈퇴 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateName")
    public ResponseEntity<?> updateName(@ModelAttribute MemberDto updateMember, HttpSession session) {

        Long sessionId = (Long) session.getAttribute("id");
        String sessionEmail = (String) session.getAttribute("email");
        String sessionPwd = (String) session.getAttribute("pwd");
        String updateNewName = updateMember.getNewName();
        int sessionRole = (int) session.getAttribute("role");

        MemberDto memberDto = new MemberDto(sessionId, sessionEmail, sessionPwd, updateNewName, sessionRole);

        if (!memberService.nameDuplicate(updateMember.getNewName())) {
            memberService.updateName(memberDto);
            return new ResponseEntity<>("닉네임 수정 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("닉네임 중복", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updatePwd")
    public ResponseEntity<?> updatePwd(@ModelAttribute MemberDto updateMember, HttpSession session) {

        Long sessionId = (Long) session.getAttribute("id");
        String sessionEmail = (String) session.getAttribute("email");
        String sessionPwd = (String) session.getAttribute("pwd");
        String updateNewPwd = updateMember.getNewPwd();
        String sessionName = (String) session.getAttribute("name");
        int sessionRole = (int) session.getAttribute("role");

        MemberDto memberDto = new MemberDto(sessionId, sessionEmail, updateNewPwd, sessionName, sessionRole);

        if ((updateMember.getPwd().equals(sessionPwd) && (updateMember.getNewPwd().equals(updateMember.getNewPwd2())))) {
            memberService.updatePwd(memberDto);
            return new ResponseEntity<>("비밀번호 수정 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
        }
    }
}
