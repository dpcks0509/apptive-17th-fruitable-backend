package apptive.fruitable.login.controller;

import apptive.fruitable.login.dto.MemberDto;
import apptive.fruitable.login.service.EmailService;
import apptive.fruitable.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final MemberService memberService;
    private final EmailService emailService;

    // 이메일 중복 확인 + 이메일 인증코드 전송
    @GetMapping("/send")
    public ResponseEntity<?> emailSend(@RequestParam("email") String email, HttpServletResponse response) throws MessagingException {

        if (memberService.emailDuplicate(email)) {
            return new ResponseEntity<>("이메일 중복", HttpStatus.BAD_REQUEST);
        } else {
            String emailCode = emailService.sendEmail(email);

            Cookie cookieEmailCode = new Cookie("emailCode", emailCode);
            // emailCode 유효기간 3분
            cookieEmailCode.setMaxAge(60 * 3);
            response.addCookie(cookieEmailCode);

            return new ResponseEntity<>("이메일 전송\n" + emailCode, HttpStatus.OK);
        }
    }

    // 이메일 인증코드 확인
    @GetMapping("/confirm")
    public ResponseEntity<?> emailConfirm(@RequestParam("emailCode") String emailCode,
                                          HttpServletResponse response, HttpServletRequest request) {

        String cookieEmailCode = "";

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("emailCode"))
                    cookieEmailCode = cookie.getValue();
            }
        }

        if (!emailCode.equals(cookieEmailCode)) {
            return new ResponseEntity<>("인증번호 불일치", HttpStatus.BAD_REQUEST);
        } else {
//            Cookie deleteEmailCode = new Cookie("emailCode", null);
//            deleteEmailCode.setMaxAge(0);
//            response.addCookie(deleteEmailCode);
            return new ResponseEntity<>("인증번호 일치", HttpStatus.OK);
        }
    }
}
