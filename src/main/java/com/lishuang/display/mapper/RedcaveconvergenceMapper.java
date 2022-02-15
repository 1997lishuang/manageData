package com.lishuang.display.mapper;

import com.lishuang.display.model.Redcaveconvergence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 李爽
 * @since 2022-01-11
 */
public interface RedcaveconvergenceMapper extends BaseMapper<Redcaveconvergence> {
    //清空指定表
    @Update("truncate table redcaveconvergence")
    void deleteRedcave();
}
