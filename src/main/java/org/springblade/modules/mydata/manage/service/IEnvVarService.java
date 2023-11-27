package org.springblade.modules.mydata.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.modules.mydata.manage.dto.EnvVarDTO;
import org.springblade.modules.mydata.manage.entity.EnvVar;
import org.springblade.modules.mydata.manage.vo.EnvVarVO;

import java.util.Collection;
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

    /**
     * 在指定环境里 根据名称搜索唯一变量
     *
     * @param envId   环境id
     * @param varName 变量名
     * @return 环境变量
     */
    EnvVar findByNameInEnv(Long envId, String varName);

    List<EnvVar> findByNameInEnv(Long envId, Collection<String> varNames);

    /**
     * 在指定环境里 保存变量值
     *
     * @param envVar 变量
     * @return 操作结果，true-成功，false-失败
     */
    boolean saveByNameInEnv(EnvVar envVar);
}
