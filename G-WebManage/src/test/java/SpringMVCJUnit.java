
import auxiliary.subassembly.tree.treeview.ITreeView;
import com.Manage;
import model.vo.TreeViewNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = Manage.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class SpringMVCJUnit {

    @Autowired
    @Qualifier("officeTreeViewImpl")
    private ITreeView officeTreeViewImpl;

    @Test
    public void test() {

        List<TreeViewNode> officeTreeViewNode = officeTreeViewImpl.getTree();
        System.out.println(officeTreeViewNode.size());

    }
}
