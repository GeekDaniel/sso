package com.piaoniu.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@EnableTransactionManagement
public class DataConfig implements BeanDefinitionRegistryPostProcessor {


    Pattern daoExcludePattern = Pattern.compile(".*\\.EntityDao");

    @Bean(name="dataSource")
    public DataSource dataSource(){
        JdbcConfig jdbcConfig=new JdbcConfig();
        jdbcConfig.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false&characterEncoding=utf8");
        jdbcConfig.setUser("root");
        jdbcConfig.setPassword("passw0rd");
        jdbcConfig.setTimeOut(60000);
        return JdbcUtils.newDataSource("dataSource", jdbcConfig);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.piaoniu.**.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ClassPathMapperScanner scanner = new ClassPathMapperScanner(beanDefinitionRegistry);
        scanner.registerFilters();
        scanner.addExcludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                    throws IOException {
                Matcher matcher = daoExcludePattern.matcher(metadataReader.getClassMetadata().getClassName());
                return matcher.find();
            }
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }



}
