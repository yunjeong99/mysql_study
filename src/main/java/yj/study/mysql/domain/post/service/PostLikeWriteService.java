package yj.study.mysql.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yj.study.mysql.domain.member.dto.MemberDto;
import yj.study.mysql.domain.post.entity.Post;
import yj.study.mysql.domain.post.entity.PostLike;
import yj.study.mysql.domain.post.repository.PostLikeRepository;

@Service
@RequiredArgsConstructor
public class PostLikeWriteService {

    private final PostLikeRepository postLikeRepository;

    public Long create(Post post, MemberDto memberDto) {
        PostLike postLike = PostLike.builder()
                .postId(post.getId())
                .memberId(memberDto.id())
                .build();

        return postLikeRepository.save(postLike).getPostId();
    }
}
