package com.wanguo.quick_batch.jpa;

import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.pojo.LotteryOpportunityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface LotteryOpportunityRecordJpa extends JpaRepository<LotteryOpportunityRecord, Integer>, JpaSpecificationExecutor<LotteryOpportunityRecord> {

    //LotteryOpportunityRecord findByBoostIntervalAndCustomer(String boostInterval, Customer customer);

}
