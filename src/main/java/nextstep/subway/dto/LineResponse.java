package nextstep.subway.dto;

import nextstep.subway.domain.Line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LineResponse {
    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations;

    public LineResponse(){
    }

    public LineResponse(Long id, String name, String color, List<StationResponse> stations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.stations = stations;
    }

    public static LineResponse of(Line line) {
        Set<StationResponse> stations = new HashSet<>();
        stations.add(StationResponse.of(line.getUpStation()));
        stations.add(StationResponse.of(line.getDownStation()));
        return new LineResponse(line.getId(), line.getName(), line.getColor(), new ArrayList<>(stations));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<StationResponse> getStations() {
        return stations;
    }
}