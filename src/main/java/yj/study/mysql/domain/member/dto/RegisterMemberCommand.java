package yj.study.mysql.domain.member.dto;

import java.time.LocalDate;

/**
 * record
 * -------
 * getter, setter 자동으로 만들어줌
 * 프로퍼티 형식으로 사용 가능
 */
public record RegisterMemberCommand(
        String email,
        String nickname,
        LocalDate birthday
) {

}
