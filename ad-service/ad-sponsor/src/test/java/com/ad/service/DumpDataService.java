package com.ad.service;

import com.ad.Application;
import com.ad.constant.CommonStatus;
import com.ad.dao.AdPlanRepository;
import com.ad.dao.AdUnitRepository;
import com.ad.dao.CreativeRepository;
import com.ad.dao.CreativeUnitRepository;
import com.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.ad.dao.unit_condition.AdUnitItRepository;
import com.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.ad.dump.DConstant;
import com.ad.dump.table.*;
import com.ad.entity.AdPlan;
import com.ad.entity.AdUnit;
import com.ad.entity.Creative;
import com.ad.entity.CreativeUnit;
import com.ad.entity.unit_condition.AdUnitDistrict;
import com.ad.entity.unit_condition.AdUnitIt;
import com.ad.entity.unit_condition.AdUnitKeyword;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/17 23:53
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}
,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private AdUnitRepository adUnitRepository;

    @Autowired
    private AdUnitKeywordRepository keywordRepository;

    @Autowired
    private AdUnitItRepository itRepository;

    @Autowired
    private AdUnitDistrictRepository districtRepository;

    @Autowired
    private CreativeRepository creativeRepository;

    @Autowired
    private CreativeUnitRepository creativeUnitRepository;

    @Test
    public void dumpAdTableTable(){
        dumpAdPlanTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_PLAN)
        );
        dumpAdUnitTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT)
        );
        dumpAdUnitKeywordTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_KEYWORD)
        );
        dumpAdUnitItTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_IT)
        );
        dumpAdUnitDistrictTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_DISTRICT)
        );
        dumpAdCreativeTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE)
        );
        dumpAdCreativeUnitTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE_UNIT)
        );


    }

    private void dumpAdPlanTable(String fileName){
        List<AdPlan> adPlans = adPlanRepository.findAllByPlanStatus(
                CommonStatus.VALID.getStatus()
        );
        if (CollectionUtils.isEmpty(adPlans)){
            return;
        }
        List<AdPlanTable> planTables = new ArrayList<>();
        adPlans.forEach(x->{
            planTables.add(
                    new AdPlanTable(
                            x.getId(),
                            x.getUserId(),
                            x.getPlanStatus(),
                            x.getStartDate(),
                            x.getEndDate()
                    )
            );
        });
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdPlanTable planTable : planTables){
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            writer.close();
        }catch (IOException ex){
            log.error("dumpAdPlanTable error");
        }

    }

    private void dumpAdUnitTable(String fileName) {
        List<AdUnit> adUnits = adUnitRepository.findByUnitStatus(
                CommonStatus.VALID.getStatus()
        );
        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }
        List<AdUnitTable> unitTables = new ArrayList<>();
        adUnits.forEach(u -> unitTables.add(
                new AdUnitTable(
                        u.getId(),
                        u.getUnitStatus(),
                        u.getPositionType(),
                        u.getPlanId()
                )
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdUnitTable error");
        }
    }

    private void dumpAdUnitKeywordTable(String fileName) {
        List<AdUnitKeyword> unitKeywords = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }
        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(k -> unitKeywordTables.add(
                new AdUnitKeywordTable(
                        k.getUnitId(),
                        k.getKeyword()
                )
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdUnitItTable error");
        }
    }

    private void dumpAdUnitItTable(String fileName) {
        List<AdUnitIt> unitIts = itRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }
        List<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(i -> unitItTables.add(
                new AdUnitItTable(
                        i.getUnitId(),
                        i.getItTag()
                )
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitItTable unitItTable : unitItTables) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdUnitItTable error");
        }
    }

    private void dumpAdUnitDistrictTable(String fileName) {
        List<AdUnitDistrict> unitDistricts = districtRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }
        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(d -> unitDistrictTables.add(
                new AdUnitDistrictTable(
                        d.getUnitId(),
                        d.getProvince(),
                        d.getCity()
                )
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdUnitDistrictTable error");
        }
    }


    private void dumpAdCreativeTable(String fileName) {
        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(c -> creativeTables.add(
                new AdCreativeTable(
                        c.getId(),
                        c.getName(),
                        c.getType(),
                        c.getMaterialType(),
                        c.getHeight(),
                        c.getWidth(),
                        c.getAuditStatus(),
                        c.getUrl()
                )
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdCreativeTable error");
        }
    }

    private void dumpAdCreativeUnitTable(String fileName) {
        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }
        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c -> creativeUnitTables.add(
                new AdCreativeUnitTable(
                        c.getCreativeId(),
                        c.getUnitId()
                )
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTable creativeUnitTable : creativeUnitTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdCreativeUnit error");
        }
    }
}