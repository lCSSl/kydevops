package work.kaiyu.system.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import work.kaiyu.common.core.constant.ServiceNameConstants;
import work.kaiyu.common.core.domain.R;
import work.kaiyu.system.api.factory.RemoteUserFallbackFactory;
import work.kaiyu.system.api.model.UserInfo;

/**
 * 用户服务
 *
 * @author css
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService
{
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    @GetMapping(value = "/user/info/{username}")
    public R<UserInfo> getUserInfo(@PathVariable("username") String username);
}
