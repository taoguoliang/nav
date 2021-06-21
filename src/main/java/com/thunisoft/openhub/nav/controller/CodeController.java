package com.thunisoft.openhub.nav.controller;

import com.thunisoft.openhub.nav.bean.Code;
import com.thunisoft.openhub.nav.consts.Const;
import com.thunisoft.openhub.nav.consts.PageEnum;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单值代码相关的控制器类.
 *
 * @author chenjiayin on 2020-08-02.
 * @since v1.0.0
 */
@CrossOrigin
@RestController
@RequestMapping(Const.API_V1 + "/codes")
public class CodeController {

    /**
     * 获取分类所属页面的代码列表数据.
     *
     * @return 代码列表
     */
    @GetMapping("/page")
    public ResponseEntity<List<Code>> getPageCodes() {
        return ResponseEntity.ok(PageEnum.getCodeList());
    }

}
