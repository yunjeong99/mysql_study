package yj.study.mysql.application.usecase;
;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yj.study.mysql.domain.follow.service.FollowWriteService;
import yj.study.mysql.domain.member.dto.MemberDto;
import yj.study.mysql.domain.member.service.MemberReadService;

@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsercase {

    private final MemberReadService memberReadService;

    private final FollowWriteService followWriteService;

    public void excute(Long fromMemberId, Long toMemberId) {
        /*
            1. 입력받은 memberId로 회원 조회
            2. FollowWriteService.creatE() 호출
         */

        MemberDto fromMember = memberReadService.getMember(fromMemberId);
        MemberDto toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember, toMember);

    }
}
