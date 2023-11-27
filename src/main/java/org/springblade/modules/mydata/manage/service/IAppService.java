package org.springblade.modules.mydata.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.mydata.manage.dto.AppDTO;
import org.springblade.modules.mydata.manage.dto.AppStatDTO;
import org.springblade.modules.mydata.manage.entity.App;
import org.springblade.modules.mydata.manage.vo.AppVO;

import java.util.List;

/**
 * 应用 服务类
 *
 * @author LIEN
 * @since 2023-01-31
 */
public interface IAppService extends BaseService<App> {

    /**
     * 自定义分页
     *
     * @param page
     * @param app
     * @return
     */
    IPage<AppVO> selectAppPage(IPage<AppVO> page, AppVO app);

    /**
     * 新增或更新
     *
     * @param appDTO 应用
     * @return 操作结果，true-成功，false-失败
     */
    boolean submit(AppDTO appDTO);

    /**
     * 删除应用
     *
     * @param ids 应用id
     * @return 操作结果，true-成功，false-失败
     */
    boolean deleteApp(List<Long> ids);

    /**
     * 查询应用的概况统计
     *
     * @return 应用的概况统计
     */
    AppStatDTO getAppStat();

}
