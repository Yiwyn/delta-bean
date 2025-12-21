import domain.User;
import fun.lifepoem.tool.deltabean.DeltaBean;
import fun.lifepoem.tool.deltabean.entity.DiffContent;
import fun.lifepoem.tool.deltabean.entity.DiffItem;
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

        DiffContent content = DeltaBean.diffExecutor(User.class)
                .diffTemplate(new UserDiffTmpl()).diff(oldObj, newObj);


        for (DiffItem diffItem : content.getDiffItems()) {
            System.out.println("diffItem = " + diffItem);
        }

    }


}
