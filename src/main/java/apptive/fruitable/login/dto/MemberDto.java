package apptive.fruitable.login.dto;

import apptive.fruitable.login.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private int role = 1; // 역할 기본값(판매자, 구매자 동일) -> 판매자 이메일 승인시 role=2로 판매자 전환

    public MemberDto(Long id, String email, String pwd, String name, int role) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.role = role;
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
        return memberDto;
    }
}
