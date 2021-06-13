package nextstep.subway.section.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionTest {

    @DisplayName("구간의 앞단, 구간 중간의 앞단, 구간 중간의 뒷단은 인덱스 그대로이고, 구간의 뒷단은 인덱스가 1 증가한다.")
    @Test
    void 이어붙일_위치에_따른_인덱스값의_변화_테스트() {
        Position midfront = Position.isMidFront();
        Position rear = Position.isRear();

        int testIndex = 10;

        midfront.set(testIndex);
        rear.set(testIndex);

        assertThat(midfront.index()).isEqualTo(testIndex);
        assertThat(rear.index()).isEqualTo(++testIndex);
    }

    @Test
    void isDocked() {
        Position position1 = Position.isNone();
        assertThat(position1.isNotDockedYet()).isTrue();

        Position position2 = Position.isMidFront();
        assertThat(position2.isNotDockedYet()).isFalse();
    }

    @Test
    void create() {
        Position front = Position.isFront();

        int defaultIndex = -1;

        assertThat(front.index()).isEqualTo(defaultIndex);
    }

}