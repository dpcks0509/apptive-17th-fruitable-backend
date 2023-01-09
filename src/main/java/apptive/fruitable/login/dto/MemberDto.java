package apptive.fruitable.login.dto;

import apptive.fruitable.login.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String emailCode; // 이메일 인증코드
    private String pwd;
    private String pwd2; // 비밀번호 재확인
    private String newPwd; // 새 비밀번호
    private String newPwd2; // 새 비밀번호 재확인
    private String name;
    private String newName; // 새 닉네임
    private String role = "Buyer"; // 역할 기본값(구매자) -> 판매자 이메일 승인시 role="Seller"로 판매자 전환

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    Date now = new Date();
    private String date = dateFormat.format(now);

    public MemberDto(Long id, String email, String pwd, String name, String role, String date) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.role = role;
        this.date = date;
    }

    public static MemberDto toMemberDto(MemberEntity memberEntity) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberEntity.getId());
        memberDto.setEmail(memberEntity.getEmail());
        memberDto.setEmailCode(memberDto.getEmailCode());
        memberDto.setPwd(memberEntity.getPwd());
        memberDto.setPwd2(memberEntity.getPwd());
        memberDto.setNewPwd(memberDto.getNewPwd());
        memberDto.setNewPwd2(memberDto.getNewPwd2());
        memberDto.setName(memberEntity.getName());
        memberDto.setNewName(memberDto.getNewName());
        memberDto.setRole(memberEntity.getRole());
        memberDto.setDate(memberEntity.getDate());
        return memberDto;
    }
}
