package cn.grimcake.mybatis.binding;

import cn.grimcake.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import cn.hutool.core.lang.ClassScanner;


public class MapperRegistry {

    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if(mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + "is not know to the MapperRegistry");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    public <T> void addMapper(Class<T> type) {
        // Mapper必须是接口才会注册
        if(type.isInterface()) {
            if(hasMapper(type)) {
                throw new RuntimeException("Type " + type + "is already know to the MapperRegistry.");
            }
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    public void addMappers(String packageName) {
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for(Class<?> mapperClass : mapperSet) {
            addMapper(mapperClass);
        }
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

}
