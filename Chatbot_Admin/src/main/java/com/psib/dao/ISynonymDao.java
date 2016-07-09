package com.psib.dao;

import com.psib.model.Synonym;

import java.util.List;

public interface ISynonymDao {

    long countOriginBySearchPhrase(String searchPhrase);

    List<Synonym> getOriginBySearchPhraseAndSort(String searchPhrase, String sortName, int maxResult, int skipResult);

    long countSynonymsBySearchPhrase(String searchPhrase, int originId);

    List<Synonym> getSynonymsBySearchPhraseAndSort(String searchPhrase, String sortName, int maxResult, int skipResult
            , int originId);

    void insertWord(Synonym synonym);

    void deleteById(Synonym synonym);

    int checkWordExist(Synonym synonym);
}
