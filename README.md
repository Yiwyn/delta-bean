### 快速开始


```java
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
```