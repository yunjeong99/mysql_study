package yj.study.mysql.domain.post.dto;

import java.time.LocalDate;

public record DailyPostCount (Long memberId, LocalDate date, Long postCount){
}
