package com.rbkmoney.midgard.service.clearing.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.generated.midgard.tables.pojos.ClearingEventTransactionInfo;

import java.util.Comparator;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClearingEventUtils {

    public static long getLastRowId(List<ClearingEventTransactionInfo> transactionInfoList) {
        ClearingEventTransactionInfo lastTrxInfo = transactionInfoList.stream()
                .max(Comparator.comparing(ClearingEventTransactionInfo::getRowNumber))
                .orElse(null);
        return lastTrxInfo == null ? 0L : lastTrxInfo.getRowNumber();
    }

}