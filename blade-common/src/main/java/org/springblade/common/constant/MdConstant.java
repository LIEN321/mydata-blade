package org.springblade.common.constant;

/**
 * mydata 业务常量
 *
 * @author LIEN
 * @date 2022/7/9
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
     * URL 长度
     */
    int MAX_URL_LENGTH = 255;

    // ---------- 数据的操作类型 ----------
    /**
     * 数据操作类型：数据提供者
     */
    int DATA_PRODUCER = 1;
    /**
     * 数据操作类型：数据消费者
     */
    int DATA_CONSUMER = 2;

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
}