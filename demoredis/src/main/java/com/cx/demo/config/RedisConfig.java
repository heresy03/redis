package com.cx.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;


import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    /**
     * 缓存数据时key生成器，可以依据幺五五和技术场景自行定制
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator(){

        return new KeyGenerator(){
            @Override
            public Object generate(Object target, Method method, Object... params){
                System.out.println("generator-----------------------");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(target.getClass().getName());
                stringBuilder.append(method.getName());
                for (Object obj:params){
                    stringBuilder.append(obj.toString());
                }
                return stringBuilder.toString();
            }
        };
    }

    /**
     * 配置，可以直接在注解的形式中实现json的redis存储而不用再去多写配置
     * @return
     */
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
//        //初始化一个RedisCacheWriter
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
//        //设置CacheManager的值序列化方式为json序列化
//        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
//        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
//                .fromSerializer(jsonSerializer);
//        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(pair);
//        //设置默认超过期时间是30秒
//        defaultCacheConfig.entryTtl(Duration.ofSeconds(60));
//        //初始化RedisCacheManager
//        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
//    }
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory rcf){
//        StringRedisTemplate template = new StringRedisTemplate(rcf);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }

    /**
     *  默认情况下的模板只能支持 RedisTemplate<String,String>，只能存入字符串，很多时候，
     *  我们需要自定义 RedisTemplate ，设置序列化器，这样我们可以很方便的操作实例对象
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
//        RedisTemplate<String,Serializable> template = new RedisTemplate <>();
        RedisTemplate<String,Object> template = new RedisTemplate <>();
        template.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

    @Bean(name = "cacheManager")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        // 使用Jackson2JsnRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();
        return  RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }


    /**
     * 由于原生的redis自动装配，在存储key和value时，没有设置序列化方式，故自己创建redisTemplate实例
     */
    //@Resource
    //  private LettuceConnectionFactory lettuceConnectionFactory;
//    @Value("${spring.redis.timeout}")
//    private Duration timeToLive = Duration.ZERO;
//    @Bean(name = "redisTemplate")
//    public RedisTemplate<String, Object> redisTemplate(@Qualifier("objectMapper")ObjectMapper objectMapper) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        // 配置连接工厂
//        template.setConnectionFactory(lettuceConnectionFactory);
//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
////        ObjectMapper om = new ObjectMapper();
//        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jacksonSeial.setObjectMapper(objectMapper);
//        // 值采用json序列化
//        template.setValueSerializer(jacksonSeial);
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//        // 设置hash key 和value序列化模式
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(jacksonSeial);
//        template.afterPropertiesSet();
//        return template;
//    }


}
