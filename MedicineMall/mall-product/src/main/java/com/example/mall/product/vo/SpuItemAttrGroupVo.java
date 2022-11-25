package com.example.mall.product.vo;

import com.example.mall.product.vo.Attr;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class SpuItemAttrGroupVo {

    private String groupName;

    private List<Attr> attrs;

}
