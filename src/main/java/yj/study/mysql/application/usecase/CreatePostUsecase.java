package yj.study.mysql.application.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yj.study.mysql.domain.follow.service.FollowReadService;
import yj.study.mysql.domain.post.dto.PostCommand;
import yj.study.mysql.domain.post.service.PostWriteService;
import yj.study.mysql.domain.post.service.TimelineWriteService;
import yj.study.mysql.domain.follow.entity.Follow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePostUsecase {

    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final TimelineWriteService timelineWriteService;

//    @Transactional
    public Long execute(PostCommand postCommand) {
        Long postId = postWriteService.create(postCommand);

        List<Long> followers = followReadService.getFollowers(postCommand.memberId()).stream()
                .map(Follow::getFromMemberId)
                .toList();

        timelineWriteService.deliveryToTimeline(postId, followers);

        return postId;
    }
}
