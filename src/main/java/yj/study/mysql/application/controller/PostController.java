package yj.study.mysql.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import yj.study.mysql.application.usecase.CreatePostLikeUsecase;
import yj.study.mysql.application.usecase.CreatePostUsecase;
import yj.study.mysql.application.usecase.GetTimelinePostUsecase;
import yj.study.mysql.domain.post.dto.DailyPostCount;
import yj.study.mysql.domain.post.dto.DailyPostCountRequest;
import yj.study.mysql.domain.post.dto.PostCommand;
import yj.study.mysql.domain.post.dto.PostDto;
import yj.study.mysql.domain.post.entity.Post;
import yj.study.mysql.domain.post.service.PostReadService;
import yj.study.mysql.domain.post.service.PostWriteService;
import yj.study.mysql.util.CursorRequest;
import yj.study.mysql.util.PageCursor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostWriteService postWriteService;

    private final PostReadService postReadService;

    private final GetTimelinePostUsecase getTimelinePostUsecase;

    private final CreatePostUsecase createPostUsecase;

    private final CreatePostLikeUsecase createPostLikeUsecase;

    @PostMapping("")
    public Long create(PostCommand command) {
//        return postWriteService.create(command);
        return createPostUsecase.execute(command);
    }

    @GetMapping("daily-post-counts")
    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("members/{memberId}")
    public Page<PostDto> getPosts(@PathVariable Long memberId, Pageable pageable) {
        return postReadService.getPosts(memberId, pageable);
    }

    @GetMapping("members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(@PathVariable Long memberId, CursorRequest cursorRequest) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("members/{memberId}/timeline")
    public PageCursor<Post> getTimeline(@PathVariable Long memberId, CursorRequest cursorRequest) {
//        return getTimelinePostUsecase.execute(memberId, cursorRequest);
        return getTimelinePostUsecase.executeByTimeline(memberId, cursorRequest);
    }

    @PostMapping("{postId}/like/v1")
    public void likePostV1(@PathVariable Long postId) {
//        postWriteService.likePost(postId);
        postWriteService.likePostByOptimisticLock(postId);
    }

    @PostMapping("{postId}/like/v2")
    public void likePostV2(@PathVariable Long postId, @RequestParam Long memberId) {
        createPostLikeUsecase.execute(postId, memberId);
    }
}
