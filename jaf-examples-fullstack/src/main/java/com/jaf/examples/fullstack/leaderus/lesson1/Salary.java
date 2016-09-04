package com.jaf.examples.fullstack.leaderus.lesson1;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jaf.examples.fullstack.leaderus.common.Config;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月3日
 * @since 1.0
 */
public class Salary {
	
	private String name;
	
	private int baseSalary;
	
	private int bonus;
	
	public Salary() {
	}
	
	public Salary(String name, int baseSalary, int bonus) {
		this.name = name;
		this.baseSalary = baseSalary;
		this.bonus = bonus;
	}
	
	public long getTotalIncome() {
		return baseSalary * 13 + bonus;
	}
	
	public String namePrefix() {
		return name.substring(0, 2);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getBaseSalary() {
		return baseSalary;
	}
	
	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
	}
	
	public int getBonus() {
		return bonus;
	}
	
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String toFileLine() {
		return new StringBuilder()
					.append(this.name).append(Config.SPLIT_CHARACTER)
					.append(this.baseSalary).append(Config.SPLIT_CHARACTER)
					.append(this.bonus)
					.toString();
	}
	
}
