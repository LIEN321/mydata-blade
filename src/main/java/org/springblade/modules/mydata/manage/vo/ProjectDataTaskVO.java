package org.springblade.modules.mydata.manage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 项目中 数据项的任务视图实体类
 *
 * @author LIEN
 * @since 2023/12/20
 */
@Data
public class ProjectDataTaskVO implements Serializable {
    private static final long serialVersionUID = -7872303777424559343L;

    /**
     * 提供数据的任务
     */
    private List<TaskVO> producerTasks;

    /**
     * 消费数据的任务
     */
    private List<TaskVO> consumerTasks;
}
