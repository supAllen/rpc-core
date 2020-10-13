package com.wjw.rpc.core;

import com.wjw.rpc.core.constant.RecConstants;
import com.wjw.rpc.core.remote.RpcServer;
import com.wjw.rpc.core.service.*;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-29 16:29
 **/
public class BootStrap {
    private Object instance;
    private ServiceMeta serviceMeta = new ServiceMeta();

    public BootStrap(String serviceUri, String interfaceName, Object instance) {
        setServiceUri(serviceUri);
        setInterface(interfaceName);
        setInstance(instance);
        init();
    }

    private void init() {
        checkArgs();
        try {
            String rpcPort = System.getProperty("rpcPort", "15000");
            System.out.println(rpcPort);
            new RpcServer(Integer.valueOf(rpcPort)).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Processor serviceProcessor = new ServiceProcessor(serviceMeta, instance);
        RpcContext rpcContext = DefaultRpcContext.instance;
        rpcContext.put(RecConstants.RPC_SERVICE, serviceMeta.getServiceUri(), serviceProcessor);
        rpcContext.put(RecConstants.RPC_META, serviceMeta.getServiceUri(), serviceMeta);
        System.out.println("初始化完成");
    }

    private void checkArgs() {
        // TODO
//        Objects.requireNonNull(instance);
//        try {
//            Class<?> clazz = Class.forName(serviceMeta.getInterfaceName());
//            if (!clazz.isInterface()) {
//                throw new IllegalArgumentException("interfaceName must interface");
//            }
//            Method[] methods = clazz.getMethods();
//            if (ArrayUtils.isEmpty(methods)) {
//                throw new IllegalArgumentException("interface method not able 0");
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Objects.requireNonNull(serviceMeta.getServiceUri());
    }

    public void setInterface(String interfaceName) {
        serviceMeta.setInterfaceName(interfaceName);
    }

    public void setServiceUri(String serviceUri) {
        serviceMeta.setServiceUri(serviceUri);
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
