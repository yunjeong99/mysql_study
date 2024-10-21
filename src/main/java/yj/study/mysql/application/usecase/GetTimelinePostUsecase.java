package yj.study.mysql.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yj.study.mysql.domain.follow.entity.Follow;
import yj.study.mysql.domain.follow.service.FollowReadService;
import yj.study.mysql.domain.post.entity.Post;
import yj.study.mysql.domain.post.entity.Timeline;
import yj.study.mysql.domain.post.repository.TimelineReadService;
import yj.study.mysql.domain.post.service.PostReadService;
import yj.study.mysql.util.CursorRequest;
import yj.study.mysql.util.PageCursor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimelinePostUsecase {

    private final FollowReadService followReadService;
    private final PostReadService postReadService;
    private final TimelineReadService timelineReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        /*
            1. memberId -> follow 조회
            2. 1번 결과로 게시물 조회
         */
        List<Follow> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        /*
            1. Timeline 테이블 조회
            2. 1번에 해당하는 게시물을 조회
         */
//        List<Follow> followings = followReadService.getFollowings(memberId);
//        List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
//        return postReadService.getPosts(followingMemberIds, cursorRequest);

        PageCursor<Timeline> pageTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        List<Long> postIds = pageTimelines.body().stream().map(Timeline::getPostId).toList();
        List<Post> posts = postReadService.getPosts(postIds);

        return new PageCursor<>(pageTimelines.nextCursorRequest(), posts);
    }
}
