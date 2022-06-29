package cn.grimcake.mybatis.session.defaults;

import cn.grimcake.mybatis.binding.MapperRegistry;
import cn.grimcake.mybatis.session.SqlSession;
import cn.grimcake.mybatis.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }
}
