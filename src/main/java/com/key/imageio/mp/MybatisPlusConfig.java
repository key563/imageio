package com.key.imageio.mp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tujia
 * @since 2020/7/15 20:16
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 自定义sql注入器
     * 包含自定义全局方法
     *
     * @return
     */
    @Bean
    public MyLogicSqlInjector myLogicSqlInjector() {
        return new MyLogicSqlInjector();
    }
}
