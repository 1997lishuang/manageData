package com.lishuang.display.service.impl;

import com.lishuang.display.model.Redcaveconvergence;
import com.lishuang.display.mapper.RedcaveconvergenceMapper;
import com.lishuang.display.service.IRedcaveconvergenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 李爽
 * @since 2022-01-11
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RedcaveconvergenceServiceImpl extends ServiceImpl<RedcaveconvergenceMapper, Redcaveconvergence> implements IRedcaveconvergenceService {

}
