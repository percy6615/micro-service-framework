package com.companyname.framework;

import com.alibaba.druid.support.http.StatViewServlet;
import com.companyname.framework.mybatis.MultipleDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private MultipleDataSource multipleDataSource;

    /**
     * Druid监控页面配置
     *
     * @return
     */
    @Bean
    public RegistrationBean getDruidStatViewServlet() {
        StatViewServlet servlet = new StatViewServlet();
        ServletRegistrationBean bean = new ServletRegistrationBean();
        bean.setServlet(servlet);
        bean.addUrlMappings("/druid/*");
        bean.setLoadOnStartup(1);
        bean.addInitParameter("resetEnable", "false");
        return bean;
    }

    @Bean
    @Primary
    public SqlSessionFactoryBean getSqlSessionFactory() throws IOException {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:/mapper/*.xml");

        bean.setMapperLocations(resources);
        bean.setDataSource(multipleDataSource);
        return bean;
    }

}
