package com.ldc.common.repository;

import org.springframework.stereotype.Repository;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
@Repository
public interface DistributeIdDao {

    /**
     * CREATE TABLE `ldc_sequence` (
       `id` int(11) NOT NULL,
       `key_name` varchar(255) NOT NULL,
       `current_value` bigint(20) NOT NULL,
       `max_value` bigint(20) DEFAULT NULL,
        PRIMARY KEY (`id`),
        UNIQUE KEY `sequence_name_index` (`sequence_name`)
       ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     *
     */

    /**
     * SELECT current_value FROM ldc_sequence where key_name = #{keyName,jdbcType=VARCHAR} for UPDATE
     * @param idKeyName
     * @return
     */
    Long getIdValueByKeyName(String idKeyName);

    /**
     * UPDATE vcp_sequence SET current_value=#{newIdValue,jdbcType=BIGINT}
     *        where current_value=#{currentIdValue,jdbcType=BIGINT} and key_name=#{keyName,jdbcType=VARCHAR};
     * @param newIdValue
     * @param idKeyName
     * @param currentIdValue
     * @return
     */
    Integer updateIdValueByKeyNameAndCurrentValue(long newIdValue, String idKeyName, Long currentIdValue);
}
