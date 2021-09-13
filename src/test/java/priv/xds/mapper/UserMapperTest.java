package priv.xds.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.xds.pojo.User;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void queryUserByQq() {
        User user = userMapper.queryUserByQq("2237803016");
        System.out.println(user);
    }

    @Test
    void updateSignDays() {
        System.out.println(userMapper.getUnsignedUser(new Date(System.currentTimeMillis())));
    }

    @Test
    void addUser() {
    }
}