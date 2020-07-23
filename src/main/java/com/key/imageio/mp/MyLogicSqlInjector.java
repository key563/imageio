package com.key.imageio.mp;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * 注册自定义方法 集成默认注入器
 *
 * @author tujia
 * @since 2020/7/15 20:04
 */
public class MyLogicSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new MysqlInsertAllBatch());
        methodList.add(new CustomDeleteAll());
        return methodList;
    }
}
