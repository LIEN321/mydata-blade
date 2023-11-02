package org.springblade.mydata.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.mydata.manage.dto.EnvVarDTO;
import org.springblade.mydata.manage.entity.EnvVar;
import org.springblade.mydata.manage.vo.EnvVarVO;

import java.util.List;

/**
 * 环境变量 服务类
 *
 * @author LIEN
 * @since 2023-11-01
 */
public interface IEnvVarService extends BaseService<EnvVar> {

    /**
     * 自定义分页
     *
     * @param page   分页
     * @param envVar 参数
     * @return 数据列表
     */
    IPage<EnvVarVO> selectEnvVarPage(IPage<EnvVarVO> page, EnvVarVO envVar);

    /**
     * 新增或更新
     *
     * @param envVarDTO 环境变量
     * @return 操作结果，true-成功，false-失败
     */
    boolean submit(EnvVarDTO envVarDTO);

    /**
     * 删除环境
     *
     * @param ids 环境id
     * @return 操作结果，true-成功，false-失败
     */
    boolean deleteEnvVar(List<Long> ids);
}
