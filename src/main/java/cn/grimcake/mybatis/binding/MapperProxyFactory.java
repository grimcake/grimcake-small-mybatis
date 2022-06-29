package cn.grimcake.mybatis.binding;

import cn.grimcake.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 映射器代理工厂
 */
public class MapperProxyFactory<T> {
    /**
     * 提供给哪一个接口做代理操作
     */
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(SqlSession sqlSession) {
        MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface);
        return (T)Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
