package com.cys.fmmall.dao;

import com.cys.fmmall.entity.IndexImg;
import com.cys.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IndexImgMapper extends GeneralDao<IndexImg> {
    List<IndexImg> listIndexImgs();
}