package com.wanguo.quick_batch.jpa;

import com.wanguo.quick_batch.pojo.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface QRCodeJpa extends JpaRepository<QRCode, Integer>, JpaSpecificationExecutor<QRCode> {

    QRCode findBySceneAndPage(String scene, String page);

    QRCode findByScene(String scene);
}
