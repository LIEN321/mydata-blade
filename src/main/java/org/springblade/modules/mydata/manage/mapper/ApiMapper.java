package org.springblade.modules.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.mydata.manage.dto.ApiStatDTO;
import org.springblade.modules.mydata.manage.entity.Api;
import org.springblade.modules.mydata.manage.vo.ApiVO;

import java.util.List;

/**
 * 应用接口 Mapper 接口
 *
 * @author LIEN
 * @since 2022-07-08
 */
public interface ApiMapper extends BaseMapper<Api> {

    /**
     * 自定义分页
     *
     * @param page
     * @param api
     * @return
     */
    List<ApiVO> selectApiPage(IPage page, ApiVO api);

    /**
     * 查询API的概况统计
     *
     * @return
     */
    ApiStatDTO selectApiStat();

}
