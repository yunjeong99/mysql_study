package yj.study.mysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
public class Post {

    private final Long id;                      // 식별자

    private final Long memberId;                // 작성 회원 id

    private final String contents;              // Post 내용

    /*
        02.28 추가
        - 데이터가 많은 테이블에 컬럼 추가 시 default 값을 넣어주면 테이블에 락이 걸릴 수 있음
           1. 별도의 Migration 배치를 통해 조금씩 데이터를 채워넣거나
           2. 조회 시점에 null이면 값을 채워준다.
     */
    private Long likeCount;                     // 좋아요 갯수

    private Long version;                       // 동시성 제어 - 버전

    private final LocalDate createdDate;        // 작성 날짜

    private final LocalDateTime createdAt;      // 레코드 생성 날짜

    @Builder
    public Post(Long id, Long memberId, String contents, Long likeCount, Long version, LocalDate createdDate, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.likeCount = likeCount == null ? 0 : likeCount ;
        this.version = version == null ? 0 : version ;
        this.createdDate = createdDate == null ? LocalDate.now() : createdDate;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void incrementLikeCount() {
        likeCount += 1;
    }
}
