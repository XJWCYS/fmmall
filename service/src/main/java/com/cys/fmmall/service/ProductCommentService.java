package com.cys.fmmall.service;

import com.cys.fmmall.entity.ProductComments;
import com.cys.fmmall.vo.ResultVO;

public interface ProductCommentService {
    ResultVO selectCommentsByProductId(String productId,int pageNum,int limit);
    ResultVO selectCommentsCountById(String productId);
    ResultVO addComment(String orderId, ProductComments productComments);
}
