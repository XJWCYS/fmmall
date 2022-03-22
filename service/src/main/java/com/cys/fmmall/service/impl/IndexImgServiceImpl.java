package com.cys.fmmall.service.impl;

import com.cys.fmmall.dao.IndexImgMapper;
import com.cys.fmmall.entity.IndexImg;
import com.cys.fmmall.service.IndexImgService;
import com.cys.fmmall.vo.ResStatus;
import com.cys.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexImgServiceImpl implements IndexImgService {
    @Autowired
    private IndexImgMapper indexImgMapper;
    @Override
    public ResultVO listIndexImgs() {
        List<IndexImg> indexImgs = indexImgMapper.listIndexImgs();
        if(indexImgs.size()==0){
            return new ResultVO(ResStatus.NO,"fail",null);
        }else{
            return new ResultVO(ResStatus.OK,"success",indexImgs);
        }
    }



}
