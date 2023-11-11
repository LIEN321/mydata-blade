package org.springblade.common.constant;

/**
 * mydata 业务常量
 *
 * @author LIEN
 * @since 2022/7/9
 */
public interface MdConstant {
    // ---------- 字段常量 ----------

    /**
     * 编号长度
     */
    int MAX_CODE_LENGTH = 64;

    /**
     * 名称长度
     */
    int MAX_NAME_LENGTH = 64;

    /**
     * URI 长度
     */
    int MAX_URI_LENGTH = 128;

    /**
     * 512位长度
     */
    int MAX_LENGTH_512 = 512;

    // ---------- 数据常量 ----------

    /**
     * 数据操作类型：数据提供者
     */
    int DATA_PRODUCER = 1;

    /**
     * 数据操作类型：数据消费者
     */
    int DATA_CONSUMER = 2;

    /**
     * 数据过滤操作：等于
     */
    String DATA_OP_EQ = "=";

    /**
     * 数据过滤操作：等于
     */
    String DATA_OP_NE = "!=";

    /**
     * 数据过滤操作：大于
     */
    String DATA_OP_GT = ">";

    /**
     * 数据过滤操作：大于或等于
     */
    String DATA_OP_GTE = ">=";

    /**
     * 数据过滤操作：小于
     */
    String DATA_OP_LT = "<";

    /**
     * 数据过滤操作：小于或等于
     */
    String DATA_OP_LTE = "<=";

    /**
     * 数据过滤参数名：条件名
     */
    String DATA_KEY = "k";

    /**
     * 数据过滤参数名，条件操作
     */
    String DATA_OP = "op";

    /**
     * 数据过滤参数名，条件值
     */
    String DATA_VALUE = "v";

    /**
     * 业务数据字段名称：最后更新时间
     */
    String DATA_COLUMN_UPDATE_TIME = "_MD_UPDATE_TIME_";

    /**
     * 业务数据常量值：任务最后成功时间
     */
    String DATA_VALUE_TASK_LAST_SUCCESS_TIME = "_MD_TASK_LAST_SUCCESS_";

    // ---------- 网络常量 ----------

    /**
     * Http方法枚举
     */
    enum HttpMethod {
        /**
         * GET
         */
        GET,
        /**
         * POST
         */
        POST,
        /**
         * PUT
         */
        PUT,
        /**
         * DELETE
         */
        DELETE
    }

    /**
     * 接口数据类型枚举
     */
    enum ApiDataType {

        /**
         * 接口类型：json
         */
        JSON;
    }

    /**
     * http协议
     */
    String HTTP = "http://";

    /**
     * https协议
     */
    String HTTPS = "https://";

    // ---------- 任务常量 ----------

    /**
     * 停止状态
     */
    int TASK_STATUS_STOPPED = 0;

    /**
     * 运行状态
     */
    int TASK_STATUS_RUNNING = 1;

    /**
     * 失败状态
     */
    int TASK_STATUS_FAILED = 2;

    /**
     * 任务结果，0-失败
     */
    int TASK_RESULT_FAILED = 0;

    /**
     * 任务结果，1-成功
     */
    int TASK_RESULT_SUCCESS = 1;

    /**
     * 是否唯一标识
     */
    Integer IS_ID_FIELD = 1;

    /**
     * API 每一轮发送的数据量
     */
    int ROUND_DATA_COUNT = 1000;

    /**
     * 每个任务最多失败次数
     */
    int TASK_MAX_FAIL_COUNT = 3;

    /**
     * 是订阅任务
     */
    Integer TASK_IS_SUBSCRIBED = 1;

    /**
     * 定时任务 默认执行次数
     */
    int TASK_JOB_DEFAULT_TIMES = Integer.MAX_VALUE;
}
