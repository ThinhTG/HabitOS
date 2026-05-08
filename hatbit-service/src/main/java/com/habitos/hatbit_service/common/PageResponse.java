package com.habitos.hatbit_service.common;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> items;

    private int offset;

    private int limit;

    private long total;
}