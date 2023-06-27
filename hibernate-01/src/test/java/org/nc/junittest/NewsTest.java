package org.nc.junittest;

import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;
import org.nc.bean.News;

import java.sql.Date;

/**
 * Unit test for simple App.
 */
public class NewsTest {
    /**
     * hibernate 使用demo测试
     */
    @Test
    public void testHibernate() {
        // 1. 创建 Configuration 对象
        Configuration configure = new Configuration().configure();

        // 2. 创建 SessionFactory 对象
        // hibernate 4.x 之前通过此方式创建 SessionFactory 对象，4.x之后此方式被弃用
//        SessionFactory sessionFactory = configure.buildSessionFactory();

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings(configure.getProperties())
                .buildServiceRegistry();
        SessionFactory sessionFactory = configure.buildSessionFactory(serviceRegistry);

        // 3. 创建 Session 对象
        Session session = sessionFactory.openSession();

        // 4. 开启事务
        Transaction transaction = session.beginTransaction();

        // 5. 执行操作
//        News news = new News("Hibernate Demo", "nc", new Date(new java.util.Date().getTime()));
//        session.save(news);

        News news = (News) session.get(News.class, 1);
        System.out.println(news);

        // 6. 提交事务
        transaction.commit();

        // 7. 关闭 Session 对象
        session.close();

        // 8. 关闭 SessionFactory 对象
        sessionFactory.close();

    }
}
