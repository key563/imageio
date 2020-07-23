package com.key.imageio.mp;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 自定义方法：删除全部
 *
 * @author tujia
 * @since 2020/7/15 20:10
 */
public class CustomDeleteAll extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sql = "<script>delete from %s</script>";
        final String sqlResult = String.format(sql, tableInfo.getTableName());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, "deleteAll", sqlSource, new NoKeyGenerator(), null, null);
    }


}
