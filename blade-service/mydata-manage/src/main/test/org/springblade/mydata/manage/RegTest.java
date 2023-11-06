package org.springblade.mydata.manage;

import org.junit.jupiter.api.Test;
import org.springblade.common.util.MdUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表达式 测试类
 *
 * @author LIEN
 * @since 2023/11/6
 */
public class RegTest {
    @Test
    public void testParamVar() {
        List<String> varNames = MdUtil.parseVarNames("from header - ${token}, my name is ${name}, age ${age}");
        System.out.println(varNames);
    }

    @Test
    public void testReplaceVar() {
        Map<String, String> varMap = new HashMap<>();
        varMap.put("name", "zhangsan");
        varMap.put("age", "21");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("header key", "my name is ${name}, age is ${age}");
        System.out.println(MdUtil.replaceVarValues(headerMap, varMap));

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("header key", "my name is ${name}, age is ${age}");
        System.out.println(MdUtil.replaceVarValues(paramMap, varMap));
    }
}
