package apptive.fruitable.login.controller;

import apptive.fruitable.login.dto.MemberDto;
import apptive.fruitable.login.service.EmailService;
import apptive.fruitable.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final MemberService memberService;
    private final EmailService emailService;

    @GetMapping("/emailConfirm") // 이메일 중복 확인 + 이메일 인증코드 전송
    public String emailConfirm(@RequestBody MemberDto email, BindingResult bindingResult)
            throws MessagingException, UnsupportedEncodingException {

        if(memberService.emailDuplicate(email.getEmail())) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이메일이 중복됩니다.");
            System.out.println("이메일 중복");
        } else {
            String authCode = emailService.sendEmail(email.getEmail());
            System.out.println(authCode);
        }
        return "save";
    }
}
