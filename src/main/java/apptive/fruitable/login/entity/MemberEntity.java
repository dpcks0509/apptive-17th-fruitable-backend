package apptive.fruitable.login.entity;

import apptive.fruitable.login.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 고유번호

    // 이메일 중복허용 X
    @Column(unique = true, nullable = false)
    @Email
    private String email;

    // 글자수 8~20
    @Column(nullable = false)
    @Size(min = 8, max = 20)
    private String pwd;

    // 닉네임 중복허용 X, 글자수 2~10
    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 10)
    private String name;

    @Column(nullable = true)
    private int role; // (기본값)1 = 구매자, 2 = 판매자(이메일 확인 후 변경)

    public static MemberEntity toSaveEntity(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPwd(memberDto.getPwd());
        memberEntity.setName(memberDto.getName());
        memberEntity.setRole(memberDto.getRole());
        return memberEntity;
    }

    public static MemberEntity toUpdateEntityName(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPwd(memberDto.getPwd());
        memberEntity.setName(memberDto.getName());
        memberEntity.setRole(memberDto.getRole());
        return memberEntity;
    }

    public static MemberEntity toUpdateEntityPwd(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPwd(memberDto.getPwd());
        memberEntity.setName(memberDto.getName());
        memberEntity.setRole(memberDto.getRole());
        return memberEntity;
    }
}