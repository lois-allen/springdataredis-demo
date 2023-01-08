package com.jing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jing.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
class RedisStringTests {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testString() {
        stringRedisTemplate.opsForValue().set("name","虎哥");

        Object name = stringRedisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);
    }

    @Test
    void testSaveUser() throws JsonProcessingException {
        User user = new User("虎虎",21);

        String json = mapper.writeValueAsString(user);
        stringRedisTemplate.opsForValue().set("user:200",json);
        String str = stringRedisTemplate.opsForValue().get("user:200");
        User u = mapper.readValue(str,User.class);
        System.out.println("u = " + u);
    }
    
    @Test
    void testHash(){
        stringRedisTemplate.opsForHash().put("user:400","name","虎虎");
        stringRedisTemplate.opsForHash().put("user:400","age","21");

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("user:400");
        System.out.println("entries = " + entries);
    }
}
