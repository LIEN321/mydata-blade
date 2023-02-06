package org.springblade.mydata.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mydata.manage.entity.Task;
import org.springblade.mydata.manage.vo.TaskVO;

import java.util.List;

/**
 * 集成任务 Mapper 接口
 *
 * @author LIEN
 * @since 2022-07-11
 */
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 自定义分页
     *
     * @param page
     * @param task
     * @return
     */
    List<TaskVO> selectTaskPage(IPage page, TaskVO task);

}