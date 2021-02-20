package work.kaiyu.common.datascope.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.kaiyu.common.core.domain.R;
import work.kaiyu.common.core.utils.StringUtils;
import work.kaiyu.common.security.utils.SecurityUtils;
import work.kaiyu.system.api.RemoteUserService;
import work.kaiyu.system.api.model.UserInfo;

/**
 * 同步调用用户服务
 *
 * @author css
 */
@Service
public class AwaitUserService {
    private static final Logger log = LoggerFactory.getLogger(AwaitUserService.class);

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 查询当前用户信息
     *
     * @return 用户基本信息
     */
    public UserInfo info() {
        String username = SecurityUtils.getUsername();
        R<UserInfo> userResult = remoteUserService.getUserInfo(username);
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            log.info("数据权限范围查询用户：{} 不存在.", username);
            return null;
        }
        return userResult.getData();
    }
}
