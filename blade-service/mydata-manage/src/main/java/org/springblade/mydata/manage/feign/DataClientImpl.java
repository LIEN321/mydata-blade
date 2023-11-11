package org.springblade.mydata.manage.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.mydata.manage.service.IDataService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据Feign 实现类
 *
 * @author LIEN
 * @since 2022/7/14
 */
@RestController
@AllArgsConstructor
public class DataClientImpl implements IDataClient {

    private final IDataService dataService;

    /**
     * 更新业务数据量
     *
     * @param tenantId 租户id
     * @param id       数据项id
     * @return 任务列表
     */
    @Override
    public R updateDataCount(String tenantId, Long id) {
        return R.status(dataService.updateDataCount(tenantId, id));
    }
}
