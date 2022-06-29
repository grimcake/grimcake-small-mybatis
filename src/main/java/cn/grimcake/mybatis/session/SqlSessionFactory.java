package cn.grimcake.mybatis.session;

public interface SqlSessionFactory {
    /**
     * 打开一个session
     * @return Sqlsession
     */
    SqlSession openSession();
}
