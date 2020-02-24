package com.upgrad.streams.kafkastreamspoc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageView {
    private Long userId;
    private String page;
    private int duration;
}
