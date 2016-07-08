package com.psib.service.impl;

import com.psib.dao.ISynonymDao;
import com.psib.dto.BootGirdDto;
import com.psib.dto.SynonymJsonDto;
import com.psib.model.Synonym;
import com.psib.service.ISynonymManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SynonymManager implements ISynonymManager {

    private static final Logger LOG = Logger.getLogger(SynonymManager.class);

    @Autowired
    private ISynonymDao synonymDao;

    @Override
    public BootGirdDto getAllOriginForPaging(int current, int rowCount, String searchPhrase, String sortName) {
        LOG.info(new StringBuilder("[getAllOriginForPaging] Start: current = ").append(current)
                .append(" ,rowCount = ").append(rowCount)
                .append(" ,searchPhrase = ").append(searchPhrase)
                .append(" sortName = ").append(sortName));

        List<Synonym> list;
        int start = current * rowCount - rowCount;

        list = synonymDao.getOriginBySearchPhraseAndSort(searchPhrase, sortName, rowCount, start);

        List<SynonymJsonDto> synonymJsonDtoList = new ArrayList<>();
        long size = list.size();

        for (int i = 0; i < size; i++) {
            synonymJsonDtoList.add(new SynonymJsonDto((start + i + 1), list.get(i)));
        }

        BootGirdDto dto = new BootGirdDto();
        dto.setCurrent(current);
        dto.setRowCount(rowCount);
        dto.setRows(synonymJsonDtoList);
        dto.setTotal(synonymDao.countOriginBySearchPhrase(searchPhrase));

        LOG.info("[getAllOriginForPaging] End");
        return dto;
    }

    @Override
    public BootGirdDto getAllSynonymsForPaging(int current, int rowCount, String searchPhrase, String sortName) {
        LOG.info(new StringBuilder("[getAllSynonymsForPaging] Start: current = ").append(current)
                .append(" ,rowCount = ").append(rowCount)
                .append(" ,searchPhrase = ").append(searchPhrase)
                .append(" sortName = ").append(sortName));

        List<Synonym> list;
        int start = current * rowCount - rowCount;

        list = synonymDao.getSynonymsBySearchPhraseAndSort(searchPhrase, sortName, rowCount, start);

        List<SynonymJsonDto> synonymJsonDtoList = new ArrayList<>();
        long size = list.size();

        for (int i = 0; i < size; i++) {
            synonymJsonDtoList.add(new SynonymJsonDto((start + i + 1), list.get(i)));
        }

        BootGirdDto dto = new BootGirdDto();
        dto.setCurrent(current);
        dto.setRowCount(rowCount);
        dto.setRows(synonymJsonDtoList);
        dto.setTotal(synonymDao.countSynonymsBySearchPhrase(searchPhrase));

        LOG.info("[getAllSynonymsForPaging] End");
        return dto;
    }
}
