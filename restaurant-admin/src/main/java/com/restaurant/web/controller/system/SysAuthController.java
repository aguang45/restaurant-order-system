package com.restaurant.web.controller.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson2.JSONObject;
import com.restaurant.common.constant.Constants;
import com.restaurant.common.core.controller.BaseController;
import com.restaurant.common.core.domain.AjaxResult;
import com.restaurant.common.core.domain.entity.SysUser;
import com.restaurant.common.core.domain.model.LoginUser;
import com.restaurant.common.enums.UserStatus;
import com.restaurant.common.exception.ServiceException;
import com.restaurant.common.utils.AuthUtils;
import com.restaurant.common.utils.SecurityUtils;
import com.restaurant.common.utils.StringUtils;
import com.restaurant.framework.web.service.SysPermissionService;
import com.restaurant.framework.web.service.TokenService;
import com.restaurant.system.domain.SysAuthUser;
import com.restaurant.system.mapper.SysUserMapper;
import com.restaurant.system.service.ISysUserService;
import me.zhyd.oauth.cache.AuthDefaultStateCache;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

/**
 * 第三方认证授权处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/auth")
public class SysAuthController extends BaseController
{
    private AuthStateCache authStateCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysUserMapper userMapper;

    private final static Map<String, String> auths = new HashMap<String, String>();
    {
        auths.put("gitee", "{\"clientId\":\"****\",\"clientSecret\":\"****\",\"redirectUri\":\"****?source=gitee\"}");
        auths.put("github", "{\"clientId\":\"****\",\"clientSecret\":\"****\",\"redirectUri\":\"****?source=github\"}");
        authStateCache = AuthDefaultStateCache.INSTANCE;
    }

    /**
     * 认证授权
     * 
     * @param source
     * @throws IOException
     */
    @GetMapping("/binding/{source}")
    @ResponseBody
    public AjaxResult authBinding(@PathVariable("source") String source, HttpServletRequest request) throws IOException
    {
        LoginUser tokenUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(tokenUser) && userMapper.checkAuthUser(tokenUser.getUserId(), source) > 0)
        {
            return error(source + "平台账号已经绑定");
        }

        String obj = auths.get(source);
        if (StringUtils.isEmpty(obj))
        {
            return error(source + "平台账号暂不支持");
        }
        JSONObject json = JSONObject.parseObject(obj);
        AuthRequest authRequest = AuthUtils.getAuthRequest(source, json.getString("clientId"), json.getString("clientSecret"), json.getString("redirectUri"), authStateCache);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        return success(authorizeUrl);
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/social-login/{source}")
    public AjaxResult socialLogin(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request)
    {
        String obj = auths.get(source);
        if (StringUtils.isEmpty(obj))
        {
            return AjaxResult.error(10002, "第三方平台系统不支持或未提供来源");
        }
        JSONObject json = JSONObject.parseObject(obj);
        AuthRequest authRequest = AuthUtils.getAuthRequest(source, json.getString("clientId"), json.getString("clientSecret"), json.getString("redirectUri"), authStateCache);
        AuthResponse<AuthUser> response = authRequest.login(callback);
        if (response.ok())
        {
            LoginUser tokenUser = tokenService.getLoginUser(request);
            if (StringUtils.isNotNull(tokenUser))
            {
                SysUser user = userMapper.selectAuthUserByUuid(source + response.getData().getUuid());
                if (StringUtils.isNotNull(user))
                {
                    String token = tokenService.createToken(SecurityUtils.getLoginUser());
                    return success().put(Constants.TOKEN, token);
                }
                // 若已经登录则直接绑定系统账号
                SysAuthUser authUser = new SysAuthUser();
                authUser.setAvatar(response.getData().getAvatar());
                authUser.setUuid(source + response.getData().getUuid());
                authUser.setUserId(SecurityUtils.getUserId());
                authUser.setUserName(response.getData().getUsername());
                authUser.setNickName(response.getData().getNickname());
                authUser.setEmail(response.getData().getEmail());
                authUser.setSource(source);
                userMapper.insertAuthUser(authUser);
                String token = tokenService.createToken(SecurityUtils.getLoginUser());
                return success().put(Constants.TOKEN, token);
            }
            SysUser authUser = userMapper.selectAuthUserByUuid(source + response.getData().getUuid());
            if (StringUtils.isNotNull(authUser))
            {
                SysUser user = userService.selectUserByUserName(authUser.getUserName());
                if (StringUtils.isNull(user))
                {
                    throw new ServiceException("登录用户：" + user.getUserName() + " 不存在");
                }
                else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
                {
                    throw new ServiceException("对不起，您的账号：" + user.getUserName() + " 已被删除");
                }
                else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
                {
                    throw new ServiceException("对不起，您的账号：" + user.getUserName() + " 已停用");
                }
                LoginUser loginUser = new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
                String token = tokenService.createToken(loginUser);
                return success().put(Constants.TOKEN, token);
            }
            else
            {
                return AjaxResult.error(10002, "对不起，您没有绑定注册用户，请先注册后在个人中心绑定第三方授权信息！");
            }
        }
        return AjaxResult.error(10002, "对不起，授权信息验证不通过，请联系管理员");
    }

    /**
     * 取消授权
     */
    @DeleteMapping(value = "/unlock/{authId}")
    public AjaxResult unlockAuth(@PathVariable Long authId)
    {
        return toAjax(userMapper.deleteAuthUser(authId));
    }
}
