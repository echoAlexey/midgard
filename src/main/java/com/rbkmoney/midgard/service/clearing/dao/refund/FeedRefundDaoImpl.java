package com.rbkmoney.midgard.service.clearing.dao.refund;

import com.rbkmoney.midgard.service.clearing.exception.DaoException;
import com.rbkmoney.midgard.service.clearing.dao.common.AbstractGenericDao;
import com.rbkmoney.midgard.service.clearing.dao.common.RecordRowMapper;
import org.jooq.Query;
import org.jooq.generated.feed.enums.RefundStatus;
import org.jooq.generated.feed.tables.pojos.Refund;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.util.List;

import static org.jooq.generated.feed.Tables.PAYMENT;
import static org.jooq.generated.feed.Tables.REFUND;

@Component
public class FeedRefundDaoImpl extends AbstractGenericDao implements RefundDao {

    private final RowMapper<Refund> refundRowMapper;

    public FeedRefundDaoImpl(DataSource dataSource) {
        super(dataSource);
        refundRowMapper = new RecordRowMapper<>(REFUND, Refund.class);
    }

    @Override
    public List<Refund> getRefunds(long eventId, List<Integer> providerIds, int poolSize) throws DaoException {
        Query query = getDslContext().select()
                .from(REFUND)
                .join(PAYMENT).on(REFUND.INVOICE_ID.eq(PAYMENT.INVOICE_ID))
                .and(REFUND.PAYMENT_ID.eq(PAYMENT.PAYMENT_ID))
                .and(PAYMENT.ROUTE_PROVIDER_ID.in(providerIds))
                .where(REFUND.EVENT_ID.greaterThan(eventId))
                .and(REFUND.STATUS.eq(RefundStatus.succeeded))
                .orderBy(REFUND.EVENT_ID).limit(poolSize);
        return fetch(query, refundRowMapper);
    }

}