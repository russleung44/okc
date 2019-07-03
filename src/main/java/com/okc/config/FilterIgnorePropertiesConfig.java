package com.okc.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tony
 * @date 2019/5/23 14:20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ignore")
@ConditionalOnExpression("!'${ignore}'.isEmpty()")
public class FilterIgnorePropertiesConfig {
    private List<String> urls = new ArrayList<>();
    private List<String> authenticates = new ArrayList<>();
}
