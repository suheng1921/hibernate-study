package org.nc.junittest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nc.hibernate.pojo.News;

public class HibernateJunitTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() throws Exception {
        // 1. 创建 Configuration 对象
        Configuration configure = new Configuration().configure();
        System.out.println("Configuration created.");

        // 2. 创建 SessionFactory 对象
        // hibernate 4.x 之前通过此方式创建 SessionFactory 对象，4.x之后此方式被弃用
//        SessionFactory sessionFactory = configure.buildSessionFactory();

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configure.getProperties())
                .buildServiceRegistry();
        sessionFactory = configure.buildSessionFactory(serviceRegistry);
        System.out.println("SessionFactory created.");

        // 3. 创建 Session 对象
        session = sessionFactory.openSession();
        System.out.println("Session created.");

        // 4. 开启事务
        transaction = session.beginTransaction();
        System.out.println("Transaction opened.");
        System.out.println("\r\n");
    }

    @After
    public void destroy() throws Exception {
        System.out.println("\r\n");
        // 6. 提交事务
        transaction.commit();
        System.out.println("Transaction committed.");

        // 7. 关闭 Session 对象
        session.close();
        System.out.println("Session closed.");

        // 8. 关闭 SessionFactory 对象
        sessionFactory.close();
        System.out.println("SessionFactory closed.");
    }

    @Test
    public void testSessionCache() {
        News news = (News) session.get(News.class, 1);
        News news2 = (News) session.get(News.class, 1);
        System.out.println(news);
        // 两次查询，控制台只输出了一次查询sql语句，两个查询返回对象也相同
        System.out.println(news == news2); // result: true

    }

}
