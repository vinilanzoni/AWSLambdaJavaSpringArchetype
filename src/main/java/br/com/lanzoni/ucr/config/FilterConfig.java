package br.com.lanzoni.ucr.config;

import br.com.lanzoni.ucr.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> filterAuthBean() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        AuthFilter authFilter = new AuthFilter();

        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/user/*");
        return registrationBean;
    }

}
