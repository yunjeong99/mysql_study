package yj.study.mysql.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yj.study.mysql.domain.member.dto.MemberDto;
import yj.study.mysql.domain.member.dto.MemberNicknameHistoryDto;
import yj.study.mysql.domain.member.dto.RegisterMemberCommand;
import yj.study.mysql.domain.member.entity.Member;
import yj.study.mysql.domain.member.service.MemberReadService;
import yj.study.mysql.domain.member.service.MemberWriteService;

import java.util.List;

@RestController
@RequiredArgsConstructor // 생성자
@RequestMapping("members")
public class MemberController {

    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    // 회원 등록 API
    @PostMapping
    public MemberDto register(@RequestBody RegisterMemberCommand command) {
        Member member = memberWriteService.register(command);
        return memberReadService.toDto(member);
    }

    // 회원 조회 API
    @GetMapping("{id}")
    public MemberDto getMapper(@PathVariable long id) {
        return memberReadService.getMember(id);
    }

    // 회원 닉네임 변경 API
    @PostMapping("{id}/name")
    public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname) {
        Member member = memberWriteService.changeNickname(id, nickname);
        return memberReadService.toDto(member);
    }

    // 회원 닉네임 변경 이력 조회 API
    @GetMapping("{memberId}/nickname-histories")
    public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable Long memberId) {
        return memberReadService.getNicknameHistory(memberId);
    }
}
