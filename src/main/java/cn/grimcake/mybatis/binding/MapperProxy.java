package cn.grimcake.mybatis.binding;


import cn.grimcake.mybatis.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 映射器
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final Long serialVersionUID = -6424540398559729838L;

    private SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    /**
     *
     * @param proxy 代理对象
     * @param method 目标对象方法
     * @param args 目标对象参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            return sqlSession.selectOne(method.getName(), args);
        }
    }
}
