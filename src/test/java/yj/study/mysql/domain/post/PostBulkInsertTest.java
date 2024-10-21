package yj.study.mysql.domain.post;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import yj.study.mysql.domain.post.entity.Post;
import yj.study.mysql.domain.post.repository.PostRepository;
import yj.study.mysql.util.PostFixtureFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        System.out.println("\n-----------------------------------------------------------------------------------------\n");
        EasyRandom easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 2, 1)
        );

        /** 쿼리 하나씩 처리 Start */
//        IntStream.range(0, 5)
//                .mapToObj(i -> easyRandom.nextObject(Post.class))
//                .forEach(x ->
//                        postRepository.save(x)
//                );
        /** 쿼리 하나씩 처리 End */

        /** 쿼리 한번에 처리 START */
//        List<Post> posts = IntStream.range(0, 5)
//                .mapToObj(i -> easyRandom.nextObject(Post.class))
//                .toList();
//
//        postRepository.bulkInsert(posts);
        /** 쿼리 한번에 처리 END */

        /** 객체 생성 시간 측정 Start */
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int _1만 = 10000;
        List<Post> posts = IntStream.range(0, _1만 * 100)
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        stopWatch.stop();

        System.out.println("객체 생성 시간 :: " + stopWatch.getTotalTimeSeconds());
        /** 객체 생성 시간 측정 End */

        /** 쿼리 시간 측정 Start */

        StopWatch queryStopWatch = new StopWatch();
        queryStopWatch.start();

//        postRepository.bulkInsert(posts);

        int batchSize = 10000; // 배치 단위 크기
        int listSize = posts.size(); // 전체 데이터 크기

        for (int i=0; i<listSize; i+=batchSize) {
            int endIndex = i + batchSize; // 최소 index를 구한다. (save 할 때 batch 단위만큼 subList로 짜르기 위함)
            List<Post> batchList = posts.subList(i, endIndex);
            postRepository.bulkInsert(batchList);
        }

        queryStopWatch.stop();
        System.out.println("DB Insert 시간 :: "+queryStopWatch.getTotalTimeSeconds());

        /** 쿼리 시간 측정 Start */

        System.out.println("\n-----------------------------------------------------------------------------------------");
    }
}
