package com.wjw.rpc.core.command;

import java.io.Serializable;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-29 16:35
 **/
public class Response implements Serializable {
    private static final long serialVersionUID = 0L;

    private String id = "";
    private Object resp;
}
