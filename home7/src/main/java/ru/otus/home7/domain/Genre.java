package ru.otus.home7.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {
    private long id;
    private String name;
}
