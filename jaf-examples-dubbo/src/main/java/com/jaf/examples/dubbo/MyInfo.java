package com.jaf.examples.dubbo;

import java.util.Set;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月3日
 * @since 1.0
 */
public class MyInfo {

	private Set<String> enabledFeature;
	private boolean enabled;

	public Set<String> getEnabledFeature() {
		return enabledFeature;
	}

	public void setEnabledFeature(Set<String> enabledFeature) {
		this.enabledFeature = enabledFeature;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
