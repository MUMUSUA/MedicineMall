package com.example.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

/**
 * 品牌
 * 
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 17:22:16
 */
@Data
@Validated
@TableName("brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名称不可为空")
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "品牌logo地址必须为url格式")
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	private String firstLetter;
	/**
	 * 排序
	 */
	private Integer sort;

}
