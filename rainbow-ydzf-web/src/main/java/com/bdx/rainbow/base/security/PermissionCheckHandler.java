package com.bdx.rainbow.base.security;

import com.bdx.rainbow.base.annotation.Security;


public interface PermissionCheckHandler {

	public boolean isPermission(Security security) throws Exception;
}
