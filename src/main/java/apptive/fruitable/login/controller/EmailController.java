package apptive.fruitable.login.controller;

import apptive.fruitable.login.dto.MemberDto;
import apptive.fruitable.login.service.EmailService;
import apptive.fruitable.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final MemberService memberService;
    private final EmailService emailService;

    // 이메일 중복 확인 + 이메일 인증코드 전송
    @GetMapping("/email/send")
    public ResponseEntity<?> emailSend(@ModelAttribute MemberDto memberDto, HttpSession session) throws MessagingException {

        if (memberService.emailDuplicate(memberDto.getEmail())) {
            return new ResponseEntity<>("이메일 중복", HttpStatus.BAD_REQUEST);
        } else {
            String emailCode = emailService.sendEmail(memberDto.getEmail());
            session.setAttribute("emailCode", emailCode);
            return new ResponseEntity<>("이메일 전송", HttpStatus.OK);
        }
    }

    // 이메일 인증코드 확인
    @GetMapping("/email/confirm")
    public ResponseEntity<?> emailConfirm(@ModelAttribute MemberDto memberDto, HttpSession session) {

        String sessionEmailCode = (String) session.getAttribute("emailCode");
        if (!memberDto.getEmailCode().equals(sessionEmailCode)) {
            return new ResponseEntity<>("인증번호 불일치", HttpStatus.BAD_REQUEST);
        } else {
            session.invalidate();
            return new ResponseEntity<>("인증번호 일치", HttpStatus.OK);
        }
    }
}
