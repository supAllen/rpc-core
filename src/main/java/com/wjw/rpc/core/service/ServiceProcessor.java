package com.wjw.rpc.core.service;

import com.wjw.rpc.core.command.RequestCommand;
import com.wjw.rpc.core.command.Response;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2020-09-29 17:00
 **/
public class ServiceProcessor implements Processor{
    private ServiceMeta serviceMeta;
    private Object serviceInstance;
    private Map<String, Method> methods = new HashMap<String, Method>();

    public ServiceProcessor(ServiceMeta serviceMeta, Object serviceInstance) {
        this.serviceMeta = serviceMeta;
        this.serviceInstance = serviceInstance;
        System.out.println("instance "+this.serviceInstance);
        setMethods();
    }

    private void setMethods() {
        String interfaceName = serviceMeta.getInterfaceName();
        try {
            Class<?> clazz = Class.forName(interfaceName);
            Method[] allMethod = clazz.getMethods();
            if (ArrayUtils.isEmpty(allMethod)) {
                throw new IllegalArgumentException("interface method is 0");
            }
            for (Method method : allMethod) {
                methods.put(method.getName(), method);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response handler(RequestCommand command) throws InvocationTargetException, IllegalAccessException {
        String methodName = command.getMethod();
        Map<String, Object> params = command.getParams();
        Method method = methods.get(methodName);
        System.out.println("instance "+this.serviceInstance);
        System.out.println("params: "+params.values());
        // 参数必须是个object数组，不能是列表
        Object res = method.invoke(this.serviceInstance, params.values().toArray());
        return new Response(res, command.getId());
    }
}
