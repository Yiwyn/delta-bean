import domain.User;
import io.github.yiwyn.deltabean.DeltaBean;
import io.github.yiwyn.deltabean.annotation.Diff;
import io.github.yiwyn.deltabean.entity.DiffContent;
import io.github.yiwyn.deltabean.entity.DiffItem;
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
        oldObj.setPhone("123");
        oldObj.setGender(1);

        User newObj = new User();
        newObj.setUsername("newYiwyn");
        newObj.setPhone("456");
        newObj.setGender(0);

        DiffContent content = DeltaBean.diffExecutor().diffTemplate(new UserDiffTmpl()).diff(oldObj, newObj);


        for (DiffItem diffItem : content.getDiffItems()) {
            System.out.println("diffItem = " + diffItem);
        }

    }


}
