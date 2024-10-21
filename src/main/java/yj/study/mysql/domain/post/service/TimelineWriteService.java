package yj.study.mysql.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yj.study.mysql.domain.post.entity.Timeline;
import yj.study.mysql.domain.post.repository.TimelineRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimelineWriteService {
    
    private final TimelineRepository timelineRepository;
    
    public void deliveryToTimeline(Long postId, List<Long> toMemberIds) {
        List<Timeline> timelines = toMemberIds.stream()
                .map((memberId) -> toTimeline(postId, memberId))
                .toList();

        timelineRepository.bulkInsert(timelines);
    }

    private static Timeline toTimeline(Long postId, Long memberId) {
        return Timeline.builder().memberId(memberId).postId(postId).build();
    }
}
