package cn.grimcake.mybatis.test;

import cn.grimcake.mybatis.binding.MapperProxyFactory;
import cn.grimcake.mybatis.binding.MapperRegistry;
import cn.grimcake.mybatis.session.SqlSession;
import cn.grimcake.mybatis.session.SqlSessionFactory;
import cn.grimcake.mybatis.session.defaults.DefaultSqlSessionFactory;
import cn.grimcake.mybatis.test.dao.IUserDao;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ApiTest {
    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_MapperProxyFactory() {
        // 1、注册Mapper
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("cn.grimcake.mybatis.test.dao");

        // 2、从SqlSession工厂获取Session
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3、获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 4、测试验证
        String res = userDao.queryUserName("10001");
        logger.info("测试结果：{}", res);
    }

    @Test
    public void test_proxy_class() {
        IUserDao userDao = (IUserDao) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class},
                (((proxy, method, args) -> "你被代理了！"))
        );
        String result = userDao.queryUserName("1");
        logger.info("测试结果：{}", JSON.toJSONString(result));
    }
}
