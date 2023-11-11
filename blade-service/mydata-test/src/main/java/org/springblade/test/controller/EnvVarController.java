package org.springblade.test.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 接收环境变量
 *
 * @author LIEN
 * @since 2023/11/6
 */
@RestController
@AllArgsConstructor
@RequestMapping("/var")
@Slf4j
public class EnvVarController {
    @GetMapping("/test")
    public R listHrUser(@RequestHeader HttpHeaders headers, @RequestParam(required = false, name = "token") String token) {
        String headerToken = headers.getFirst("token");
        log.info("header token = {}", headerToken);
        log.info("param token = {}", token);
        return R.status(true);
    }
}
