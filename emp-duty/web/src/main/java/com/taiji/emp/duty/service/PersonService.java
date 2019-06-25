package com.taiji.emp.duty.service;

import com.taiji.emp.base.vo.ContactVo;
import com.taiji.emp.duty.feign.ContactsClient;
import com.taiji.emp.duty.feign.DutyTeamClient;
import com.taiji.emp.duty.feign.PersonClient;
import com.taiji.emp.duty.searchVo.PersonListVo;
import com.taiji.emp.duty.searchVo.PersonPageVo;
import com.taiji.emp.duty.vo.DutyTeamVo;
import com.taiji.emp.duty.vo.PersonVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.service.UtilsService;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService extends BaseService {

    @Autowired
    PersonClient personClient;

    @Autowired
    ContactsClient contactClient;

    @Autowired
    DutyTeamClient dutyTeamClient;

    @Autowired
    UtilsService utilsService;

    /**
     * 新增值班人员信息
     * @param vo
     * @param principal
     */
    public void create(PersonListVo vo, Principal principal) {

        String account = principal.getName();
     //   vo.setCreateBy(account); //创建人
     //   vo.setUpdateBy(account); //更新人
        //前台传入条件判断人员表中是否有数据，无数据查询通讯录表得到相应属性入库
        ResponseEntity<List<PersonVo>> list = personClient.findList(vo);
        List<PersonVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        if (CollectionUtils.isEmpty(voList)){
            //无数据查询通讯录
            List<String> addrIds = vo.getAddrIds();
            for (String addrId:addrIds) {
                ResponseEntity<ContactVo> contactVo = contactClient.findOne(addrId);
                ContactVo contactVoList = ResponseEntityUtils.achieveResponseEntityBody(contactVo);
                PersonVo personVo = new PersonVo();
                personVo.setAddrId(addrId);
                personVo.setAddrName(contactVoList.getName());
                personVo.setDutyTeamId(vo.getDutyTeamId());
                DutyTeamVo dutyTeamVo = getDutyTeamName(vo.getDutyTeamId());
                personVo.setDutyteamName(dutyTeamVo.getTeamName());
                personVo.setCreateBy(account);
                personVo.setCreateTime(utilsService.now());
                personClient.create(personVo);
            }
        }else{
            //添加其他人员
            List<String> addrIds = vo.getAddrIds();
            for (String addrId:addrIds) {
                PersonListVo personListVo = new PersonListVo();
                List<String> ids = new ArrayList<String>();
                personListVo.setDutyTeamId(vo.getDutyTeamId());
                ids.add(addrId);
                personListVo.setAddrIds(ids);
                ResponseEntity<List<PersonVo>> lists = personClient.findList(personListVo);
                List<PersonVo> voLists = ResponseEntityUtils.achieveResponseEntityBody(lists);
                if (CollectionUtils.isEmpty(voLists)){
                    ResponseEntity<ContactVo> contactVo = contactClient.findOne(addrId);
                    ContactVo contactVoList = ResponseEntityUtils.achieveResponseEntityBody(contactVo);
                    PersonVo personVo = new PersonVo();
                    personVo.setAddrId(addrId);
                    personVo.setAddrName(contactVoList.getName());
                    personVo.setDutyTeamId(vo.getDutyTeamId());
                    personVo.setCreateBy(account);
                    personVo.setCreateTime(utilsService.now());
                    DutyTeamVo dutyTeamVo = getDutyTeamName(vo.getDutyTeamId());
                    personVo.setDutyteamName(dutyTeamVo.getTeamName());
                    personClient.create(personVo);
                }
            }
        }
    }

    /**
     * 根据id删除某条值班人员信息
     * @param vo
     */
    public void deleteLogic(PersonListVo vo) {
        Assert.notNull(vo,"PersonListVo不能为null");
        ResponseEntity<Void> resultVo = personClient.deleteLogic(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 修改值班人员信息
     * @param vo
     * @param principal
     */
    public void update(PersonVo vo, Principal principal,String id) {

        //获取用户姓名
        String account = principal.getName();
        //更新人
        vo.setCreateBy(account);
        personClient.update(vo,id);
    }

    /**
     * 根据id查询值班人员信息
     * @param id
     * @return
     */
    public PersonVo findOne(String id) {
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PersonVo> resultVo = personClient.findOne(id);
        PersonVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据条件查询值班人员列表
     * @param listVo
     * @return
     */
    public List<PersonVo> findList(PersonListVo listVo) {
        Assert.notNull(listVo,"PersonListVo不能为null");
        ResponseEntity<List<PersonVo>> list = personClient.findList(listVo);
        List<PersonVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }

    /**
     * 根据条件查询值班人员列表——分页
     * @param pageVo
     * @return
     */
    public RestPageImpl<PersonVo> findPage(PersonPageVo pageVo) {
        Assert.notNull(pageVo,"PersonPageVo不能为null");
        ResponseEntity<RestPageImpl<PersonVo>> resultVo = personClient.findPage(pageVo);
        RestPageImpl<PersonVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
//        List<PersonVo> list = vo.getContent();
//        if (null != list && list.size() > 0){
//            for (PersonVo personVo : list){
//                Integer orderInTeam = personVo.getOrderInTeam();
//                if (null == orderInTeam){
//                    personVo.setOrderInTeam(0);
//                }
//                String addrId = personVo.getAddrId();
//                ResponseEntity<ContactVo> result = contactClient.findOne(addrId);
//                ContactVo contactVo = ResponseEntityUtils.achieveResponseEntityBody(result);
//                personVo.setMobile(contactVo.getMobile());
//                personVo.setTelephone(contactVo.getTelephone());
//                personVo.setOrgName(contactVo.getOrgName());
//                String dutyTeamId = personVo.getDutyTeamId();
//                ResponseEntity<DutyTeamVo> entityVo = dutyTeamClient.findOne(dutyTeamId);
//                DutyTeamVo dutyTeamVo = ResponseEntityUtils.achieveResponseEntityBody(entityVo);
//                if (null != dutyTeamVo) {
//                    personVo.setDutyteamName(dutyTeamVo.getTeamName());
//                }
//            }
//        }
        return vo;
    }

    private DutyTeamVo getDutyTeamName(String dutyTeamId){
        ResponseEntity<DutyTeamVo> dutyTeam = dutyTeamClient.findOne(dutyTeamId);
        DutyTeamVo dutyTeamVo = ResponseEntityUtils.achieveResponseEntityBody(dutyTeam);
        return dutyTeamVo;
    }
}
