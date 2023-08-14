package de.demo.app.conf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.demo.app.filter.TrailingSlashRedirectFilter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.Filter;
import org.apache.commons.collections4.CollectionUtils;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Configuration
@ComponentScan(
        basePackages = {"de.demo.app.controller"}
)
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public Filter trailingSlashRedirectFilter() {
        return new TrailingSlashRedirectFilter();
    }

    @Bean
    public FilterRegistrationBean<Filter> trailingSlashFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(trailingSlashRedirectFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler( "/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
        registry
                .addResourceHandler("/app/**")
                .addResourceLocations("classpath:/app/")
                .setCacheControl(CacheControl.noCache().sMaxAge(0, TimeUnit.SECONDS))
                .resourceChain(true)
                .addResolver(
                        new PathResourceResolver() {

                            @Override
                            protected Resource getResource(String resourcePath, Resource location) {
                                Resource requestedResource = null;
                                try {
                                    requestedResource = location.createRelative(resourcePath);
                                } catch (IOException e) {
                                    logger.error("Resource unable to find", e);
                                }
                                if (requestedResource != null
                                        && requestedResource.exists()
                                        && requestedResource.isReadable()) {
                                    return requestedResource;
                                } else {
                                    return new ClassPathResource("/app/index.html");
                                }
                            }
                        });

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (CollectionUtils.isEmpty(converters)) {
            converters.add(jacksonMessageConverter());
        } else {
            final Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() instanceof MappingJackson2HttpMessageConverter) {
                    iterator.remove();
                }
            }
            converters.add(jacksonMessageConverter());
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:" + "/swagger-ui/index.html");
    }

    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        final MappingJackson2HttpMessageConverter messageConverter =
                new MappingJackson2HttpMessageConverter();
        final ObjectMapper mapper =
                new ObjectMapper()
                        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .registerModule(new JavaTimeModule());
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        messageConverter.setObjectMapper(mapper);
        return messageConverter;
    }

    @Bean(name = "objectMapper")
    public ObjectMapper getObjectMapper() {
        return jacksonMessageConverter().getObjectMapper();
    }


    @Value("${server.servlet.context-path:#{null}}")
    private Optional<String> contextPath;

    @Bean
    public OpenAPI springOpenAPI() {
        final OpenAPI openAPI = new OpenAPI()
                .info(new Info().title("Demo API")
                        .description("Demo Web Interfaces")
                        .version("0.0.1"));

        if (contextPath.isPresent()) {
            return openAPI.servers(List.of(
                    new Server().url(contextPath.get() + "/")));
        } else {
            return openAPI;
        }
    }

    @Bean
    OpenApiCustomizer sortOpenApi() {
        return openApi -> {
            Paths paths = openApi.getPaths().entrySet()
                    .stream()
                    .sorted(Comparator.comparing(entry -> getOperationId(entry.getValue())))
                    .collect(Paths::new, (map, item) -> map.addPathItem(item.getKey(), item.getValue()), Paths::putAll);

            openApi.setPaths(paths);
        };
    }

    private String getOperationId(PathItem pathItem) {
        return Stream.of(pathItem.getGet(), pathItem.getPost(), pathItem.getDelete(), pathItem.getPut(),
                        pathItem.getHead(), pathItem.getOptions(), pathItem.getTrace(), pathItem.getPatch())
                .filter(Objects::nonNull)
                .map(Operation::getOperationId)
                .findFirst()
                .orElse("");
    }
}
