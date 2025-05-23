package com.jmk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    /* 관리자 메인 페이지 이동 */
    @RequestMapping(value = "/admin/main", method = RequestMethod.GET)
    public String adminMainGET() {
        logger.info("관리자 페이지 이동");
        return "admin/main"; // 뷰 이름 반환
    }
}
