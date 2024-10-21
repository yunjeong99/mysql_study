package yj.study.mysql.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yj.study.mysql.domain.member.dto.MemberDto;
import yj.study.mysql.domain.member.service.MemberReadService;
import yj.study.mysql.domain.post.entity.Post;
import yj.study.mysql.domain.post.service.PostLikeWriteService;
import yj.study.mysql.domain.post.service.PostReadService;

@Service
@RequiredArgsConstructor
public class CreatePostLikeUsecase {

    private final PostReadService postReadService;
    private final MemberReadService memberReadService;
    private final PostLikeWriteService postLikeWriteService;

    public void execute(Long postId, Long memberId) {
        Post post = postReadService.getPost(postId);
        MemberDto member = memberReadService.getMember(memberId);
        postLikeWriteService.create(post, member);
    }
}
