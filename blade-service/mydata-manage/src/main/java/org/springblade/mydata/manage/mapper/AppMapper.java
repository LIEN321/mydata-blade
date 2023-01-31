package org.springblade.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mydata.manage.entity.App;
import org.springblade.mydata.manage.vo.AppVO;

import java.util.List;

/**
 * 应用 Mapper 接口
 *
 * @author LIEN
 * @since 2023-01-31
 */
public interface AppMapper extends BaseMapper<App> {

    /**
     * 自定义分页
     *
     * @param page
     * @param app
     * @return
     */
    List<AppVO> selectAppPage(IPage page, AppVO app);

}
