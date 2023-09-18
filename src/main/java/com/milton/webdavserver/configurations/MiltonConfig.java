package com.milton.webdavserver.configurations;

import io.milton.http.fs.NullSecurityManager;
import io.milton.servlet.DefaultMiltonConfigurator;

public class MiltonConfig extends DefaultMiltonConfigurator {
    private final NullSecurityManager securityManager;

    public MiltonConfig() {
        this.securityManager = new NullSecurityManager();
    }

    @Override
    protected void build() {
        builder.setSecurityManager(securityManager);
        super.build();
    }
}