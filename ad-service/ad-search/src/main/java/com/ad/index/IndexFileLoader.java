package com.ad.index;

import com.ad.dump.DConstant;
import com.ad.dump.table.*;
import com.ad.handler.AdLevelDataHandler;
import com.ad.mysql.constant.OpType;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : wangdi
 * @Date : create in 2019/6/19 23:48
 */
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    @PostConstruct
    public void init() {

        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_PLAN)
        );
        adPlanStrings.forEach(x -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(x, AdPlanTable.class), OpType.ADD
        ));

        List<String> adCreativesStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE)
        );
        adCreativesStrings.forEach(x -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(x, AdCreativeTable.class), OpType.ADD
        ));

        List<String> adUitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT)
        );
        adUitStrings.forEach(x -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(x, AdUnitTable.class), OpType.ADD
        ));

        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE_UNIT)
        );
        adCreativeUnitStrings.forEach(x -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(x, AdCreativeUnitTable.class), OpType.ADD
        ));

        List<String> adUnitKeywordStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_KEYWORD)
        );
        adUnitKeywordStrings.forEach(x -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(x, AdUnitKeywordTable.class), OpType.ADD
        ));

        List<String> adUnitItStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_IT)
        );
        adUnitItStrings.forEach(x -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(x, AdUnitItTable.class), OpType.ADD
        ));

        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(x -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(x, AdUnitDistrictTable.class), OpType.ADD
        ));
    }

    private List<String> loadDumpData(String fileName) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
