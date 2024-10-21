package yj.study.mysql.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yj.study.mysql.application.usecase.CreateFollowMemberUsercase;
import yj.study.mysql.application.usecase.GetFollowingMemberUsecase;
import yj.study.mysql.domain.member.dto.MemberDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("follow")
public class FollowController {

    private final CreateFollowMemberUsercase createFollowMemberUsercase;

    private final GetFollowingMemberUsecase getFollowingMemberUsecase;

    // 팔로우 신청 API
    @PostMapping("{fromId}/{toId}")
    public void register(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowMemberUsercase.excute(fromId, toId);
    }

    // 팔로우 목록 조회 API
    @PostMapping("members/{fromId}")
    public List<MemberDto> gg(@PathVariable Long fromId) {
        return getFollowingMemberUsecase.execute(fromId);
    }
}
