import com.controller.orderPayment.BaseOrderPaymentController;
import communal.util.param.ParameterUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TestUnit {

    private static Logger logger = LoggerFactory.getLogger(TestUnit.class);

    @Test
    public void test() {

        logger.info(
                System.getProperty("line.separator") +
                        "商户编号 : {}" + System.getProperty("line.separator") +
                        "下单时间 : {}" + System.getProperty("line.separator") +
                        "订单金额 : {}" + System.getProperty("line.separator") +
                        "订单编号 : {}",
                "20000004",
                "2019-03-03 13:57:12",
                "5000",
                "P01201903030125191350000");

/*

        System.out.println(new Date());

        Date d = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        System.out.println(sdf.format(d));
        System.out.println(sdf.format(new Date()));
*/

/*        List dataList = null;
//        dataList.add(1);
        if (null == dataList || (dataList.size() <= 0)) {
            System.out.println(1);
            return;
        } else {
            System.out.println(2);
        }*/

/*        String parameter = "  10 ";
        String defaultValue = "值";
        Integer defaultValueInt = null;

        System.out.println(ParameterUtil.integerCheck(parameter, defaultValueInt));*/
    }

    @Before
    public void before() throws Exception {System.out.println("before*******");}

    @After
    public void after() throws Exception {System.out.println("after*******");}

}
