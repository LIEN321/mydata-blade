package org.springblade.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mydata.manage.entity.TaskLog;
import org.springblade.mydata.manage.vo.TaskLogVO;

import java.util.List;

/**
 * 集成任务日志 Mapper 接口
 *
 * @author LIEN
 * @since 2022-07-18
 */
public interface TaskLogMapper extends BaseMapper<TaskLog> {

    /**
     * 自定义分页
     *
     * @param page
     * @param taskLog
     * @return
     */
    List<TaskLogVO> selectTaskLogPage(IPage page, TaskLogVO taskLog);

}
