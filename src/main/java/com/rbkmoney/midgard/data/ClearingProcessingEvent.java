package com.rbkmoney.midgard.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClearingProcessingEvent {

    private ClearingAdapter clearingAdapter;
    private Long clearingId;

}
