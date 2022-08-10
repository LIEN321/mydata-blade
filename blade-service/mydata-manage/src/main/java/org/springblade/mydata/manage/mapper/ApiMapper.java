package org.springblade.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mydata.manage.entity.Api;
import org.springblade.mydata.manage.vo.ApiVO;

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

}
