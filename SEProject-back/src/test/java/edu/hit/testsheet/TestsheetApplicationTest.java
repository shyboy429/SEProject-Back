package edu.hit.testsheet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * ClassName:TestsheetApplicationTest
 * Package:edu.hit.testsheet
 * Description:
 *
 * @date:2024/6/28 18:54
 * @author:shyboy
 */
@SpringBootTest
@ActiveProfiles("test") 
public class TestsheetApplicationTest {
    @Test
    public void contextLoads() {
        // 如果应用程序上下文能够成功加载，则测试通过
    }
}

