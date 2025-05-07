package cn.qb.scaffolding.config;

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@Configuration
public class RedisConfig {

    @Value("#{'${spring.data.redis.cluster.nodes}'.split(',')}")
    private List<String> clusterNodes;

    @Value("${spring.data.redis.cluster.max-redirects}")
    private Integer maxRedirects;

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.data.redis.timeout}")
    private Integer timeout;

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(clusterNodes);
        clusterConfig.setMaxRedirects(maxRedirects);
        clusterConfig.setPassword(password);
        return clusterConfig;
    }

    @Bean
    public LettuceClientConfiguration lettuceClientConfiguration() {
        return LettucePoolingClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .commandTimeout(Duration.ofMillis(timeout))
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(
            RedisClusterConfiguration clusterConfig,
            LettuceClientConfiguration lettuceConfig) {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(clusterConfig, lettuceConfig);
        factory.setValidateConnection(true);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
} 