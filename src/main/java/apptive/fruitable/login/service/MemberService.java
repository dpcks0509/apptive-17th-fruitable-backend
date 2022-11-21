package apptive.fruitable.login.service;

import apptive.fruitable.login.dto.MemberDto;
import apptive.fruitable.login.entity.MemberEntity;
import apptive.fruitable.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDto memberDto) {
        MemberEntity memberEntity;
        memberEntity = MemberEntity.toSaveEntity(memberDto);
        Long savedId = memberRepository.save(memberEntity).getId();
        return savedId;
    }

    public MemberDto login(MemberDto memberDto) {
        /**
         * login.html에서 아이디, 비번을 받아오고
         * DB로 부터 해당 아이디의 정보를 가져와서
         * 입력받은 비번과 DB에서 조회환 비번의 일치여부를 판단하여
         * 일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
         */

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByEmail(memberDto.getEmail());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity loginEntity = optionalMemberEntity.get();
            if (loginEntity.getPwd().equals(memberDto.getPwd())) {
                return MemberDto.toMemberDto(loginEntity); // 로그인 성공
            } else {
                return null; // 비밀번호 틀림
            }
        } else {
            return null; // 아이디 없음
        }
    }

    public MemberDto findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDto memberDto = MemberDto.toMemberDto(memberEntity);
            return memberDto;
        } else {
            return null;
        }
    }

    public boolean emailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean nameDuplicate(String name) {
        return memberRepository.existsByName(name);
    }

    public void delete(String email) throws Exception {
        memberRepository.deleteMemberEntityByEmail(email);
    }

    public void updateName(MemberDto memberDto) {
        memberRepository.save(MemberEntity.toUpdateEntityName(memberDto));
    }

    public void updatePwd(MemberDto memberDto) {
        memberRepository.save(MemberEntity.toUpdateEntityPwd(memberDto));
    }
}
