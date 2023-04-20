package com.github.bannirui.ekko.common.ex;

/**
 * 业务自定义异常.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class BizException extends RuntimeException {

    public BizException(String message) {
        super(message);
    }
}
