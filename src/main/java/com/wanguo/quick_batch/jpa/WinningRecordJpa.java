package com.wanguo.quick_batch.jpa;

import com.wanguo.quick_batch.pojo.Customer;
import com.wanguo.quick_batch.pojo.Prize;
import com.wanguo.quick_batch.pojo.WinningRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 描述：中奖记录
 *
 * @author Badguy
 */
public interface WinningRecordJpa extends JpaRepository<WinningRecord, Integer>, JpaSpecificationExecutor<WinningRecord> {

    List<WinningRecord> findAllByCustomerOrderByCreateTimeDesc(Customer customer);

    WinningRecord findByCustomerAndPrize(Customer customer, Prize prize);

    List<WinningRecord> findAllByOrderByCreateTimeDesc();

}
