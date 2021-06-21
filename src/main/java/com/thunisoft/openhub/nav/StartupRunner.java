package com.thunisoft.openhub.nav;

import com.thunisoft.openhub.nav.service.ImportService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 应用启动完毕后的运行器.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@Slf4j
@Component
public class StartupRunner implements ApplicationRunner {

    @Resource
    private ImportService importService;

    @Value("${server.port}")
    private String port;

    /**
     * 是否从 JSON 配置文件中加载保存网址导航分类和网址信息.
     */
    @Value("${system.load-json-data}")
    private Boolean loadJsonData;

    /**
     * 应用启动完毕后执行的方法.
     *
     * @param args 参数
     */
    @Override
    public void run(ApplicationArguments args) {
        if (Boolean.TRUE.equals(loadJsonData)) {
            this.importService.doImport();
        }
        log.warn("【启动服务 -> 完成】已成功启动了【nav-server】服务，服务端口为:【{}】.", port);
    }

}
