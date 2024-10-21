package yj.study.mysql.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yj.study.mysql.domain.member.dto.MemberDto;
import yj.study.mysql.domain.member.dto.MemberNicknameHistoryDto;
import yj.study.mysql.domain.member.entity.Member;
import yj.study.mysql.domain.member.entity.MemberNicknameHistory;
import yj.study.mysql.domain.member.repository.MemberNicknameHistoryRepository;
import yj.study.mysql.domain.member.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return toDto(member);
    }

    public List<MemberDto> getMembers(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        List<Member> members = memberRepository.findAllById(ids);
        return members.stream().map(this::toDto).toList();
    }

    public List<MemberNicknameHistoryDto> getNicknameHistory(Long memberId) {
        return memberNicknameHistoryRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

    public MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
        return new MemberNicknameHistoryDto(
                history.getId(), history.getMemberId(), history.getNickname(), history.getCreatedAt()
        );
    }
}
