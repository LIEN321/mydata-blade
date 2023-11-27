package org.springblade.modules.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.mydata.manage.entity.EnvVar;
import org.springblade.modules.mydata.manage.vo.EnvVarVO;

import java.util.List;

/**
 * 环境变量 Mapper 接口
 *
 * @author LIEN
 * @since 2023-11-01
 */
public interface EnvVarMapper extends BaseMapper<EnvVar> {

    /**
     * 自定义分页
     *
     * @param page
     * @param envVar
     * @return
     */
    List<EnvVarVO> selectEnvVarPage(IPage page, EnvVarVO envVar);

}
