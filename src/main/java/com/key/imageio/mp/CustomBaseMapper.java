package com.key.imageio.mp;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * 自定义BaseMapper
 *
 * @author tujia
 * @since 2020/7/15 20:07
 */
public interface CustomBaseMapper<T> extends BaseMapper<T> {
    Integer deleteAll();

    int customInsertAll();

    int mysqlInsertAllBatch(@Param("list") List<T> batchList);
}
