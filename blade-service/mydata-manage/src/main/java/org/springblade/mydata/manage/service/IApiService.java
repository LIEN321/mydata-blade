package org.springblade.mydata.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.mydata.manage.dto.ApiDTO;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.vo.ApiVO;

import java.util.List;

/**
 * 应用接口 服务类
 *
 * @author LIEN
 * @since 2022-07-08
 */
public interface IApiService extends BaseService<Api> {

    /**
     * 自定义分页
     *
     * @param page
     * @param api
     * @return
     */
    IPage<ApiVO> selectApiPage(IPage<ApiVO> page, ApiVO api);

    /**
     * 新增或更新
     *
     * @param apiDTO API
     * @return 操作结果，true-成功，false-失败
     */
    boolean submit(ApiDTO apiDTO);

    /**
     * 删除API记录
     *
     * @param ids API记录id
     * @return 操作结果，true-成功，false-失败
     */
    boolean deleteApi(List<Long> ids);

    /**
     * 将API的地址 同步更新到关联任务的地址，并重启任务
     *
     * @param id API id
     * @return 操作结果，true-成功，false-失败
     */
    boolean syncTask(Long id);
}
