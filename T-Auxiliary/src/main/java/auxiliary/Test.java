package auxiliary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Test {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String aaa(Integer a1, Long a2, String a3, Map parMap) throws Exception {

        Test2 test2 = null;
        test2.abc();

        if (true) {
            throw new Exception("未添加资产,请重新添加");
        }

        return "你好!";
    }
}
