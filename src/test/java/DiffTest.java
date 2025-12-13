import domain.User;
import io.github.yiwyn.deltabean.DeltaBean;
import io.github.yiwyn.deltabean.entity.DiffContent;
import tmpl.UserDiffTmpl;

/**
 * @className: DiffTest
 * @author: Yiwyn
 * @date: 2025/12/13 23:25
 * @Version: 1.0
 * @description:
 */
public class DiffTest {


    public static void main(String[] args) throws Exception {

        User oldObj = new User();
        oldObj.setUsername("Yiwyn");

        User newObj = new User();
        newObj.setUsername("newYiwyn");

        DiffContent content = DeltaBean.diffExecutor().diff(oldObj, newObj);

        System.out.println(content);

    }


}
