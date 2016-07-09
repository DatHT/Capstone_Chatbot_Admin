/**
 *
 */
package com.psib.controller;

import com.psib.common.DatabaseException;
import com.psib.common.JsonParser;
import com.psib.dto.BootGirdDto;
import com.psib.service.ISynonymManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

/**
 * @author DatHT
 *         Jun 4, 2016
 */

@Controller
public class SynonymController {

    private static final Logger LOG = Logger.getLogger(SynonymController.class);

    private static final String ERROR = null;

    @Autowired
    private ISynonymManager synonymManager;

    @RequestMapping(value = "/synonym", method = RequestMethod.GET)
    public ModelAndView loadSynonym() {
        LOG.info("[loadSynonym] Start");
        ModelAndView model = new ModelAndView("synonym");
        LOG.info("[loadSynonym] Emd");
        return model;
    }

    @RequestMapping(value = "/deleteWord", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteProduct(@RequestParam(name = "deleteWordId") String deleteWordId) {
        LOG.info("[deleteWord] Start");
        try {
            synonymManager.deleteWord(Integer.parseInt(deleteWordId));
            LOG.info("[deleteWord] End");
            return JsonParser.toJson(true);
        } catch (DatabaseException e) {
            LOG.error("[deleteProduct] DatabaseException: " + e.getMessage());
            return JsonParser.toJson(false);
        }

    }

    @RequestMapping(value = "/loadOrigin", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loadOrigin(@RequestParam(name = "current") int current,
                             @RequestParam(name = "rowCount") int rowCount,
                             @RequestParam(name = "searchPhrase") String searchPhrase,
                             @RequestParam(name = "sort[name]", required = false) String sortProductName) {
        return JsonParser.toJson(synonymManager.getAllOriginForPaging(current, rowCount, searchPhrase, sortProductName));
    }

    @RequestMapping(value = "/loadSynonyms", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loadSynonyms(@RequestParam(name = "current") int current,
                               @RequestParam(name = "rowCount") int rowCount,
                               @RequestParam(name = "searchPhrase") String searchPhrase,
                               @RequestParam(name = "sort[name]", required = false) String sortProductName,
                               @RequestParam(name = "originId") String originId) {
        if (originId.equals("")) {
            return "";
        }
        return JsonParser.toJson(synonymManager.getAllSynonymsForPaging(current, rowCount, searchPhrase,
                sortProductName, Integer.parseInt(originId)));
    }
}
