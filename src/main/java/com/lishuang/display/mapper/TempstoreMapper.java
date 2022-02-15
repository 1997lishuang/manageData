package com.lishuang.display.mapper;

import com.lishuang.display.model.Tempstore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 李爽
 * @since 2022-01-15
 */
public interface TempstoreMapper extends BaseMapper<Tempstore> {

    //清空指定表
    @Update("truncate table tempstore")
    void deleteTempstore();

}
