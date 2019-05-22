package cn.gs.base;

import lombok.Data;

/**
 * 查询参数基类
 */
@Data
public class PageParam {
	private int page = 0;
	private int size = 20;
}
