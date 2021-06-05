package nextstep.subway.line;

import nextstep.subway.exception.DuplicateDataException;
import nextstep.subway.exception.NoSuchDataException;
import nextstep.subway.line.application.LineService;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LinesSubResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LineServiceTest {
    @Mock
    LineRepository lineRepository;

    @InjectMocks
    LineService lineService;

    @DisplayName("노선이름 중복검사: 중복시 예외발생")
    @Test
    public void 노선이름_중복검사_중복시_예외발생() {
        //given
        LineRequest lineRequest = new LineRequest("testName", "testColor");
        when(lineRepository.existsByName("testName")).thenReturn(true);

        //when
        assertThatThrownBy(() -> lineService.validateDuplicatedName(lineRequest))
                .isInstanceOf(DuplicateDataException.class);
    }

    @DisplayName("노선 조회")
    @Test
    public void 노선조회시_노선확인() {
        //given
        Long lineId = 1L;
        Line line = new Line(lineId, "testName", "testColor");
        when(lineRepository.findById(1L)).thenReturn(Optional.ofNullable(line));

        //when
        LinesSubResponse linesSubResponse = lineService.readLine(1L);

        //then
        assertThat(linesSubResponse.getName()).isEqualTo("testName");
    }

    @DisplayName("존재하지 않는 노선 ID로 조회시 예외")
    @Test
    public void 존재하지않는노선아이디로_조회시_예외발생() {
        //given
        Long lineId = 1L;
        when(lineRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        //when
        //then
        assertThatThrownBy(() -> lineService.readLine(1L)).isInstanceOf(NoSuchDataException.class);
    }

    @DisplayName("노선 목록 조회")
    @Test
    public void 노선목록조회시_노선목록확인() {
        //given
        Line line1 = new Line(1L, "testName1", "testColor1");
        Line line2 = new Line(2L, "testName2", "testColor2");
        List<Line> lines = new ArrayList<>(Arrays.asList(line1, line2));
        when(lineRepository.findAll()).thenReturn(lines);

        //when
        List<LinesSubResponse> linesResponse = lineService.readLineAll();

        //then
        assertThat(linesResponse.size()).isEqualTo(2);
        assertThat(linesResponse.contains(LinesSubResponse.of(line1))).isTrue();
        assertThat(linesResponse.contains(LinesSubResponse.of(line2))).isTrue();
    }

    @DisplayName("노선 삭제")
    @Test
    public void 노선삭제시_노석확인() {
        //given
        Long lineId = 1L;

        //when
        lineService.removeLine(lineId);

        //then
        when(lineRepository.findById(lineId)).thenThrow(NoSuchDataException.class);
        assertThatThrownBy(() -> lineService.readLine(lineId)).isInstanceOf(NoSuchDataException.class);
    }
}
