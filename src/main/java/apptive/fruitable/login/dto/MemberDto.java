package apptive.fruitable.login.dto;

import apptive.fruitable.login.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long n;
    private String id;
    private String pwd;
    private String pwd2; //비밀번호 재확인
    private String name;
    private String email;
    private String phone;
    private int role = 1; // 역할 기본값(판매자, 구매자 동일) -> 판매자 이메일 승인시 role=2로 판매자 전환

    public MemberDto(String id, String pwd, String pwd2, String name, String email, String phone) {
        this.id = id;
        this.pwd = pwd;
        this.pwd2 = pwd2;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public static MemberDto toMemberDto(MemberEntity memberEntity) {
        MemberDto memberDto = new MemberDto();
        memberDto.setN(memberEntity.getN());
        memberDto.setId(memberEntity.getId());
        memberDto.setPwd(memberEntity.getPwd());
        memberDto.setPwd2(memberEntity.getPwd());
        memberDto.setName(memberEntity.getName());
        memberDto.setEmail(memberEntity.getEmail());
        memberDto.setPhone(memberEntity.getPhone());
        memberDto.setRole(memberEntity.getRole());
        return memberDto;
    }



}
