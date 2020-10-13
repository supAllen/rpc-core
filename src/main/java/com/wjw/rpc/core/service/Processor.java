package com.wjw.rpc.core.service;

import com.wjw.rpc.core.command.RequestCommand;
import com.wjw.rpc.core.command.Response;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-29 17:19
 **/
public interface Processor {

    /**
     *
     * @param command
     * @return
     */
    Response handler(RequestCommand command);
}
