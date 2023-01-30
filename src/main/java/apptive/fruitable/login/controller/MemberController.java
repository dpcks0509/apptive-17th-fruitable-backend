package apptive.fruitable.login.controller;

import apptive.fruitable.board.domain.post.Post;
import apptive.fruitable.board.repository.PostRepository;
import apptive.fruitable.board.service.PostService;
import apptive.fruitable.login.dto.MemberDto;
import apptive.fruitable.login.entity.MemberEntity;
import apptive.fruitable.login.repository.MemberRepository;
import apptive.fruitable.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MemberDto memberDto) {

        if (!(memberDto.getPwd().equals(memberDto.getPwd2()))) {
            return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
        }
        memberService.save(memberDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }

    @GetMapping("/nameDuplicate")
    public ResponseEntity<?> nameDuplicate(@RequestParam("name") String name) {

        if (memberService.nameDuplicate(name)) {
            return new ResponseEntity<>("닉네임 중복", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("닉네임 사용가능", HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("pwd") String pwd,
                                   HttpServletResponse response) {
        MemberDto loginResult = memberService.login(email, pwd);

        if (loginResult != null) {

            Cookie cookieId = new Cookie("id", String.valueOf(loginResult.getId()));
            Cookie cookieEmail = new Cookie("email", loginResult.getEmail());
            Cookie cookiePwd = new Cookie("pwd", loginResult.getPwd());
            Cookie cookieName = new Cookie("name", loginResult.getName());
            Cookie cookieRole = new Cookie("role", loginResult.getRole());
            Cookie cookieDate = new Cookie("date", loginResult.getDate());

            // 쿠키 로그인 유효기간 30일
            cookieId.setMaxAge(60 * 60 * 24 * 30);
            cookieEmail.setMaxAge(60 * 60 * 24 * 30);
            cookiePwd.setMaxAge(60 * 60 * 24 * 30);
            cookieName.setMaxAge(60 * 60 * 24 * 30);
            cookieRole.setMaxAge(60 * 60 * 24 * 30);
            cookieDate.setMaxAge(60 * 60 * 24 * 30);

            cookieId.setPath("/");
            cookieEmail.setPath("/");
            cookiePwd.setPath("/");
            cookieName.setPath("/");
            cookieRole.setPath("/");
            cookieDate.setPath("/");

            response.addCookie(cookieId);
            response.addCookie(cookieEmail);
            response.addCookie(cookieName);
            response.addCookie(cookiePwd);
            response.addCookie(cookieRole);
            response.addCookie(cookieDate);

            return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        Cookie cookieId = new Cookie("id", null);
        Cookie cookieEmail = new Cookie("email", null);
        Cookie cookiePwd = new Cookie("pwd", null);
        Cookie cookieName = new Cookie("name", null);
        Cookie cookieRole = new Cookie("role", null);
        Cookie cookieDate = new Cookie("date", null);

        cookieId.setMaxAge(0);
        cookieEmail.setMaxAge(0);
        cookiePwd.setMaxAge(0);
        cookieName.setMaxAge(0);
        cookieRole.setMaxAge(0);
        cookieDate.setMaxAge(0);

        response.addCookie(cookieId);
        response.addCookie(cookieEmail);
        response.addCookie(cookiePwd);
        response.addCookie(cookieName);
        response.addCookie(cookieRole);
        response.addCookie(cookieDate);

        return new ResponseEntity<>("로그아웃 완료", HttpStatus.OK);
    }

    // 비밀번호, 비밀번호2 재확인 후 일치하면 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody MemberDto deleteMember,
                                    HttpServletResponse response, HttpServletRequest request) throws Exception {

        Long cookieId = 0L;
        String cookieEmail = "";
        String cookiePwd = "";

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id"))
                    cookieId = Long.valueOf(cookie.getValue());
                else if (cookie.getName().equals("email"))
                    cookieEmail = cookie.getValue();
                else if (cookie.getName().equals("pwd"))
                    cookiePwd = cookie.getValue();
            }
        }

        if ((deleteMember.getPwd().equals(deleteMember.getPwd2())) && (deleteMember.getPwd().equals(cookiePwd))) {
            // 게시글 삭제
            Optional<MemberEntity> memberEntity = memberRepository.findById(cookieId);
            if (postRepository.existsByUserId(memberEntity.get())) {
                List<Post> postEntity = postRepository.findByUserId(memberEntity.get());
                for (int i = 0; i < postEntity.size(); i++) {
                    postRepository.deleteById(postEntity.get(i).getId());
                }
            }

            memberService.delete(cookieEmail);

            Cookie deleteId = new Cookie("id", null);
            Cookie deleteEmail = new Cookie("email", null);
            Cookie deletePwd = new Cookie("pwd", null);
            Cookie deleteName = new Cookie("name", null);
            Cookie deleteRole = new Cookie("role", null);
            Cookie deleteDate = new Cookie("date", null);

            deleteId.setMaxAge(0);
            deleteEmail.setMaxAge(0);
            deletePwd.setMaxAge(0);
            deleteName.setMaxAge(0);
            deleteRole.setMaxAge(0);
            deleteDate.setMaxAge(0);

            response.addCookie(deleteId);
            response.addCookie(deleteEmail);
            response.addCookie(deletePwd);
            response.addCookie(deleteName);
            response.addCookie(deleteRole);
            response.addCookie(deleteDate);

            return new ResponseEntity<>("탈퇴 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateName")
    public ResponseEntity<?> updateName(@RequestBody MemberDto updateMember,
                                        HttpServletResponse response, HttpServletRequest request) {

        Long cookieId = 0L;
        String cookieEmail = "";
        String cookiePwd = "";
        String updateName = updateMember.getNewName();
        String cookieRole = "";
        String cookieDate = "";

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id"))
                    cookieId = Long.valueOf(cookie.getValue());
                else if (cookie.getName().equals("email"))
                    cookieEmail = cookie.getValue();
                else if (cookie.getName().equals("pwd"))
                    cookiePwd = cookie.getValue();
                else if (cookie.getName().equals("role"))
                    cookieRole = cookie.getValue();
                else if (cookie.getName().equals("date"))
                    cookieDate = cookie.getValue();
            }
        }

        MemberDto memberDto = new MemberDto(cookieId, cookieEmail, cookiePwd, updateName, cookieRole, cookieDate);

        if (!memberService.nameDuplicate(updateMember.getNewName())) {
            memberService.updateName(memberDto);
            Cookie cookieNewName = new Cookie("name", updateName);
            cookieNewName.setMaxAge(60 * 60 * 24 * 30);
            cookieNewName.setPath("/");
            response.addCookie(cookieNewName);
            return new ResponseEntity<>("닉네임 수정 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("닉네임 중복", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updatePwd")
    public ResponseEntity<?> updatePwd(@RequestBody MemberDto updateMember,
                                       HttpServletResponse response, HttpServletRequest request) {

        Long cookieId = 0L;
        String cookieEmail = "";
        String cookiePwd = "";
        String updatePwd = updateMember.getNewPwd();
        String cookieName = "";
        String cookieRole = "";
        String cookieDate = "";

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id"))
                    cookieId = Long.valueOf(cookie.getValue());
                else if (cookie.getName().equals("email"))
                    cookieEmail = cookie.getValue();
                else if (cookie.getName().equals("pwd"))
                    cookiePwd = cookie.getValue();
                else if (cookie.getName().equals("name"))
                    cookieName = cookie.getValue();
                else if (cookie.getName().equals("role"))
                    cookieRole = cookie.getValue();
                else if (cookie.getName().equals("date"))
                    cookieDate = cookie.getValue();
            }
        }

        MemberDto memberDto = new MemberDto(cookieId, cookieEmail, updatePwd, cookieName, cookieRole, cookieDate);

        if ((updateMember.getPwd().equals(cookiePwd) && (updateMember.getNewPwd().equals(updateMember.getNewPwd2())))) {
            memberService.updatePwd(memberDto);
            Cookie cookieNewPwd = new Cookie("pwd", updatePwd);
            cookieNewPwd.setMaxAge(60 * 60 * 24 * 30);
            cookieNewPwd.setPath("/");
            response.addCookie(cookieNewPwd);
            return new ResponseEntity<>("비밀번호 수정 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("비밀번호 불일치", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/isSeller")
    public ResponseEntity<?> isSeller(HttpServletRequest request) {
        String cookieRole = "";

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("role"))
                    cookieRole = cookie.getValue();
            }
        }

        if (cookieRole.equals("Seller"))
            return new ResponseEntity<>("판매자", HttpStatus.OK);
        else
            return new ResponseEntity<>("구매자", HttpStatus.BAD_REQUEST);
    }
}
