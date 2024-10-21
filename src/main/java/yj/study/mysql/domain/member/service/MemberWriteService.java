package yj.study.mysql.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yj.study.mysql.domain.member.dto.RegisterMemberCommand;
import yj.study.mysql.domain.member.entity.Member;
import yj.study.mysql.domain.member.entity.MemberNicknameHistory;
import yj.study.mysql.domain.member.repository.MemberNicknameHistoryRepository;
import yj.study.mysql.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor // 주입
public class MemberWriteService {

    final private MemberRepository memberRepository;

    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    @Transactional
    public Member register(RegisterMemberCommand command) {
        /*
            목표 - 회원정보(이메일, 닉네임, 생년월일) 등록
                - 닉네임은 10자를 넘길 수 없다.
         */

        Member member = Member.builder()
                .nickname(command.nickname())
                .birthday(command.birthday())
                .email(command.email())
                .build();

        Member savedMember = memberRepository.save(member);

        saveMemberNicknameHistory(savedMember);

        return member;
    }

    @Transactional
    public Member changeNickname(Long id, String nickname) {
        /*
            1. 회원의 이름을 변경
            2. 변경 내역 저장
         */
        Member member = memberRepository.findById(id).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);

        return member;
    }

    private void saveMemberNicknameHistory(Member member) {
        MemberNicknameHistory history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }


}
