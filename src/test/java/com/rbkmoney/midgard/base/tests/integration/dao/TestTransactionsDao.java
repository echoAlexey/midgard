package com.rbkmoney.midgard.base.tests.integration.dao;

import com.rbkmoney.midgard.service.clearing.dao.common.AbstractGenericDao;
import com.rbkmoney.midgard.service.clearing.dao.common.RecordRowMapper;
import com.rbkmoney.midgard.service.clearing.exception.DaoException;
import org.jooq.Field;
import org.jooq.Query;
import org.jooq.Record1;
import org.jooq.generated.midgard.enums.TransactionClearingState;
import org.jooq.generated.midgard.tables.pojos.ClearingTransaction;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

import static org.jooq.generated.midgard.tables.ClearingTransaction.CLEARING_TRANSACTION;
import static org.jooq.impl.DSL.count;

public class TestTransactionsDao extends AbstractGenericDao {

    private final RowMapper<ClearingTransaction> transactionRowMapper;

    public TestTransactionsDao(DataSource dataSource) {
        super(dataSource);
        transactionRowMapper = new RecordRowMapper<>(CLEARING_TRANSACTION, ClearingTransaction.class);
    }

    public List<ClearingTransaction> getAllTransactionsByState(long clearingId,
                                                               TransactionClearingState state) {
        Query query = getDslContext().selectFrom(CLEARING_TRANSACTION)
                .where(CLEARING_TRANSACTION.CLEARING_ID.eq(clearingId))
                .and(CLEARING_TRANSACTION.TRANSACTION_CLEARING_STATE.eq(state));
        return fetch(query, transactionRowMapper);
    }

    public Integer getReadyClearingTransactionsCount(int providerId) throws DaoException {
        Field<Integer> rowCount = count(CLEARING_TRANSACTION.TRANSACTION_ID).as("rowCount");
        Record1<Integer> record = getDslContext().select(rowCount)
                .from(CLEARING_TRANSACTION)
                .where(CLEARING_TRANSACTION.PROVIDER_ID.eq(providerId))
                .fetchOne();
        return record.value1();
    }


}