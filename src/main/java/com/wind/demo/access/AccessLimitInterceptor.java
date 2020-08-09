package com.wind.demo.access;

import com.alibaba.fastjson.JSON;
import com.wind.demo.model.response.base.CommonCode;
import com.wind.demo.model.response.base.ResponseResult;
import com.wind.demo.model.response.base.ResultCode;
import com.wind.demo.redis.AccessKey;
import com.wind.demo.redis.RedisService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author Saint
 * @createTime 2020-05-22 15:48
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            //这个HandlerMrthod可以拿到注解、等很多东西
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(null == accessLimit){
                return true;
            }
            //获取时间限制
            int second = accessLimit.seconds();
            //最大访问次数
            int maxCount = accessLimit.maxCount();
            //是否需要登录，这里暂时不使用
            boolean nededLogin = accessLimit.needLogin();
            //获取用户登录的路径
            String key =request.getRequestURI();
            //设置数据的超时时间
            AccessKey accessKey =AccessKey.withExpire(second);
            synchronized (this) {
                //获取redis中的访问次数
                Integer count = redisService.get(accessKey, key, Integer.class);
                System.out.println(count);
                if (count == null) {
                    redisService.set(accessKey, key, 1);
                } else if (count < maxCount) {
                    redisService.incr(accessKey, key);
                } else {
                    //请求次数过多
                    render(response, new ResponseResult(CommonCode.LIMITED_ACCESS));
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * 将返回信息添加到response中
     * @param response
     * @param responseResult
     * @throws Exception
     */
    private void render(HttpServletResponse response, ResponseResult responseResult) throws Exception {
        //如果不给response设置文本类型，返回的错误码是乱码--显示不出来
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        //返回到前端的错误码
        String str = JSON.toJSONString(responseResult);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
