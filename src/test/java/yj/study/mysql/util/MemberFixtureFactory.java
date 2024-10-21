package yj.study.mysql.util;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import yj.study.mysql.domain.member.entity.Member;

public class MemberFixtureFactory {

    static public Member create() {
        EasyRandomParameters param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Member.class);
    }

    static public Member create(Long seed) {
        EasyRandomParameters param = new EasyRandomParameters().seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }

}
