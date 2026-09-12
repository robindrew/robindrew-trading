package com.robindrew.taskmanager.config;

import static com.robindrew.common.http.Bootstrap.*;

import com.robindrew.spring.config.admin.AdminServletConfig;
import com.robindrew.spring.config.velocity.VelocityTemplateConfig;
import com.robindrew.spring.security.BasicAuthSecurityConfig;
import com.robindrew.spring.web.template.admin.index.IndexLinkMap;
import com.robindrew.taskmanager.servlet.HomeServlet;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AdminServletConfig.class, VelocityTemplateConfig.class, BasicAuthSecurityConfig.class})
@ServletComponentScan(basePackageClasses = HomeServlet.class)
public class TradingManagerServletConfig {

    @Autowired
    private IndexLinkMap linkMap;

    @PostConstruct
    public void registerLinks() {
        linkMap.add(HomeServlet.class, "Home", COLOR_PRIMARY);
    }
}
