package com.ponmma.cl.service.impl;

import com.ponmma.cl.dao.PersonInfoDao;
import com.ponmma.cl.dao.SingleImageInfoDao;
import com.ponmma.cl.dto.PersonInfoExecution;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.entity.SingleImageInfo;
import com.ponmma.cl.enums.PersonInfoEnum;
import com.ponmma.cl.exceptions.PersonInfoException;
import com.ponmma.cl.service.PersonInfoService;
import com.ponmma.cl.util.ImageHolder;
import com.ponmma.cl.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    private SingleImageInfoDao singleImageInfoDao;
    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    @Transactional
    public PersonInfoExecution addPersonInfo(PersonInfo personInfo, ImageHolder thumbnail) throws PersonInfoException {
        SingleImageInfo singleImageInfo;
        // 判断手机号是否已存在
        try {
            if (personInfoDao.queryPersonInfoByPhoneAndPasswordAndRole(personInfo.getPhone(), null, personInfo.getRole()) != null) {
                return new PersonInfoExecution(PersonInfoEnum.PHONE_EXIST);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new PersonInfoException("查询手机号码失败");
        }

        // 添加图片
        if (thumbnail != null) {
            try {
                // 上传图片，并获取相对地址
                String seperator = System.getProperty("file.separator");
                String dest = "/upload/images/personInfo/";
                dest = dest.replace("/", seperator);
                String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
                // 添加单一图片信息
                singleImageInfo = new SingleImageInfo();
                singleImageInfo.setSrc(thumbnailAddr);
                int effectNum = singleImageInfoDao.insertSingleImageInfo(singleImageInfo);
                if (effectNum <= 0)
                    throw new PersonInfoException("添加单一图片信息失败");
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new PersonInfoException("添加单一图片信息失败");
            }
        }
        else
            return new PersonInfoExecution(PersonInfoEnum.IMAGE_EMPTY);

        //添加个人基本信息
        try {
            personInfo.setSingleImageInfo(singleImageInfo);
            int effectNum = personInfoDao.insertPersonInfo(personInfo);
            if (effectNum <= 0)
                throw new PersonInfoException("添加个人基本信息失败");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new PersonInfoException("添加个人基本信息失败");
        }

        return new PersonInfoExecution(PersonInfoEnum.ADD_SUCCEESS, personInfo);
    }

    @Override
    @Transactional
    public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo, ImageHolder thumbnail) throws PersonInfoException {
        //更新图片
        if (thumbnail != null) {
            try {
                // 删除图片
                SingleImageInfo singleImageInfo = personInfo.getSingleImageInfo();
                String thumbnailAddr = singleImageInfo.getSrc();
                ImageUtil.deleteFileOrPath(thumbnailAddr);

                // 上传图片，并获取相对地址
                String seperator = System.getProperty("file.separator");
                String dest = "/upload/images/personInfo/";
                dest = dest.replace("/", seperator);
                thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);

                // 更新单一图片信息
                singleImageInfo.setSrc(thumbnailAddr);
                int effectNum = singleImageInfoDao.updateSingleImageInfo(singleImageInfo);
                if (effectNum <= 0)
                    throw new PersonInfoException("更新单一图片信息失败");
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new PersonInfoException("更新单一图片信息失败");
            }
        }

        //更新个人基本信息
        try {
            int effectNum = personInfoDao.updatePersonInfo(personInfo);
            if (effectNum <= 0)
                throw new PersonInfoException("更新个人基本信息失败");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new PersonInfoException("更新个人基本信息失败");
        }

        return new PersonInfoExecution(PersonInfoEnum.UPDATE_SUCCESS, personInfo);
    }

    @Override
    @Transactional
    public PersonInfoExecution getPersonInfoByPhoneAndPasswordAndRole(String phone, String password, Integer role) throws PersonInfoException {
        PersonInfo personInfo;
        //查询个人基本信息
        try {
            personInfo = personInfoDao.queryPersonInfoByPhoneAndPasswordAndRole(phone, password, role);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new PersonInfoException("查询个人基本信息失败");
        }
        return new PersonInfoExecution(PersonInfoEnum.QUERY_SUCCESS, personInfo);
    }
}
