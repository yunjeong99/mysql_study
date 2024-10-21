package yj.study.mysql.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yj.study.mysql.domain.post.dto.PostCommand;
import yj.study.mysql.domain.post.entity.Post;
import yj.study.mysql.domain.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostWriteService {

    private final PostRepository postRepository;

    public Long create(PostCommand command) {
        var post = Post.builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build();

        return postRepository.save(post).getId();
    }

    /*
        잠금 획득을 위해서는 반드시 Transaction을 걸어줘야 한다
     */
    @Transactional
    public void likePost(Long postId) {
        Post post = postRepository.findById(postId, true).orElseThrow();
        post.incrementLikeCount();
        postRepository.save(post);
    }

    public void likePostByOptimisticLock(Long postId) {
        Post post = postRepository.findById(postId, false).orElseThrow();
        post.incrementLikeCount();
        postRepository.save(post);
    }


}
