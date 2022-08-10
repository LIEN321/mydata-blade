package org.springblade.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mydata.manage.entity.Env;
import org.springblade.mydata.manage.vo.EnvVO;

import java.util.List;

/**
 * 环境配置 Mapper 接口
 *
 * @author LIEN
 * @since 2022-07-11
 */
public interface EnvMapper extends BaseMapper<Env> {

    /**
     * 自定义分页
     *
     * @param page
     * @param env
     * @return
     */
    List<EnvVO> selectEnvPage(IPage page, EnvVO env);

}
