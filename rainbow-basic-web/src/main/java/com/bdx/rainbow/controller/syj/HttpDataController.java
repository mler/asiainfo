package com.bdx.rainbow.controller.syj;

import com.bdx.rainbow.entity.etl.FoodSafetyStandard;
import com.bdx.rainbow.entity.etl.SyjTableBean;
import com.bdx.rainbow.entity.etl.SyjTableBeanExample;
import com.bdx.rainbow.etl.dispatcher.impl.HttpSeedDispatcher;
import com.bdx.rainbow.etl.entity.seed.HttpSeed;
import com.bdx.rainbow.etl.pool.LocalTaskExcutor;
import com.bdx.rainbow.etl.task.impl.HttpSeed2Task;
import com.bdx.rainbow.service.etl.ISyjTableBeanService;
import com.bdx.rainbow.service.etl.analyze.FoodSafetyStandardAnalyze;
import com.bdx.rainbow.service.etl.analyze.SYJHttpAnalyze;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.*;

/**
 * 食药监数据抓取类
 * Created by Administrator on 2015/8/24.
 */
@Controller
@RequestMapping("syj")
public class HttpDataController {

    private final Logger logger = LoggerFactory.getLogger(HttpDataController.class);

    @Autowired
    private ISyjTableBeanService syjTableBeanService;

    /**
     * 食药监总局官网数据抓取
     * @return
     */
    @RequestMapping(value="data", method = RequestMethod.GET)
    @ResponseBody
    public String data(@RequestParam(value = "ids", required = false) String ids) {
        try {
            ExecuteThread executeThread = new ExecuteThread(ids);

            Thread thread = new Thread(executeThread);
            thread.start();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "fail";
        }

        return "success";
    }

    @RequestMapping(value = "back", method = RequestMethod.GET)
    @ResponseBody
    public String back(@RequestParam(value = "ids", required = false) String ids) {
        try {
            long t1 = System.currentTimeMillis();
            SyjTableBeanExample example = new SyjTableBeanExample();


            SyjTableBeanExample.Criteria criteria = example.createCriteria();
            example.setOrderByClause("id asc");

            if(!StringUtils.isEmpty(ids)) {
                List<Long> idList = new ArrayList<Long>();

                String[] idArr = ids.split(",");
                for (int i = 0; i < idArr.length; i++) {
                    if(StringUtils.isEmpty(idArr[i])) {
                        continue;
                    }

                    idList.add(Long.valueOf(idArr[i]));
                    criteria.andIdIn(idList);
                }
            }

            List<SyjTableBean> list = syjTableBeanService.list(example);

            for (SyjTableBean syjTableBean : list) {
                // 清除数据
                syjTableBeanService.truncateTable(syjTableBean.getTableName());

                // 更改状态
                syjTableBean.setCreateTime(null);
                syjTableBean.setUpdateTime(null);
                syjTableBean.setStatus(0 + "");
                syjTableBean.setCorrect(0l);
                syjTableBean.setError(0l);

                syjTableBeanService.update(syjTableBean);
            }
            logger.info("============================[回退完成处理时间：" + (System.currentTimeMillis() - t1) + " ms ]===================================");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "fail";
        }

        return "success";
    }

    /**
     * 食品安全标准数据抓取
     * @return
     */
    @RequestMapping(value = "standard", method = RequestMethod.GET)
    @ResponseBody
    public String findStandard() {
        try {
            long t1 = System.currentTimeMillis();
            logger.info("执行[食品安全标准]执行开始>>>>>>>>>>>>>>>>>>>>>>");

            HttpSeed seed = new HttpSeed();
            seed.setCreateTime(System.currentTimeMillis());
            seed.setUrl(FoodSafetyStandardAnalyze.FOOD_SAFETY_STANTARD_URL);
            Set<String> resolveTypes = new HashSet<String>();
            resolveTypes.add(HttpSeed.RESOLVETYPE_SEED_COMMON);
            seed.setResolveTypes(resolveTypes);

            Collection<HttpSeed> root_seeds = new ArrayList<HttpSeed>();
            root_seeds.add(seed);

            FoodSafetyStandardAnalyze analyze = new FoodSafetyStandardAnalyze();

            LocalTaskExcutor<HttpSeed, Collection<FoodSafetyStandard>> executor = new LocalTaskExcutor<HttpSeed, Collection<FoodSafetyStandard>>();
            HttpSeedDispatcher<Collection<FoodSafetyStandard>> dispatcher = new HttpSeedDispatcher<Collection<FoodSafetyStandard>>(executor, analyze);
            HttpSeed2Task<Collection<FoodSafetyStandard>> root_task = new HttpSeed2Task<Collection<FoodSafetyStandard>>(root_seeds ,dispatcher);

            executor.runRootTask(root_task);

            logger.info("执行[食品安全标准]执行结束>>>>>>>>>>>>>>>>>>>>>>");
            logger.info("执行[食品安全标准]执行时间[" + (System.currentTimeMillis() - t1) + "ms]>>>>>>>>>>>>>>>>>>>>>>");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return "foodSafetyStandard";
    }

    /**
     * 启动抓数据线程类
     */
    class ExecuteThread implements Runnable {

        private String ids;

        public ExecuteThread(String ids) {
            this.ids = ids;
        }

        @Override
        public void run() {

            try {
                long t1 = System.currentTimeMillis();
                SyjTableBeanExample example = new SyjTableBeanExample();


                SyjTableBeanExample.Criteria criteria = example.createCriteria();
                criteria.andStatusEqualTo("0");
                example.setOrderByClause("id asc");

                if(!StringUtils.isEmpty(ids)) {
                    List<Long> idList = new ArrayList<Long>();

                    String[] idArr = ids.split(",");
                    for (int i = 0; i < idArr.length; i++) {
                        if(StringUtils.isEmpty(idArr[i])) {
                            continue;
                        }

                        idList.add(Long.valueOf(idArr[i]));
                        criteria.andIdIn(idList);
                    }
                }

                List<SyjTableBean> list = syjTableBeanService.list(example);

                // 设置任务状态为执行中状态为1
                for (SyjTableBean syjTableBean : list) {
                    syjTableBean.setStatus(1 + "");
                    syjTableBean.setCorrect(0l);
                    syjTableBean.setError(0l);
                    syjTableBeanService.update(syjTableBean);
                }

                for (SyjTableBean syjTableBean : list) {
                    logger.info("执行[" + syjTableBean.getTitle() + "]执行开始>>>>>>>>>>>>>>>>>>>>>>");
                    syjTableBean.setStatus(2 + "");
                    syjTableBean.setCreateTime(new Timestamp(new Date().getTime()));
                    syjTableBeanService.update(syjTableBean);

                    HttpSeed seed = new HttpSeed();
                    seed.setCreateTime(System.currentTimeMillis());
                    seed.setUrl(SYJHttpAnalyze.DOMAIN + syjTableBean.getUrl());
                    Set<String> resolveTypes = new HashSet<String>();
                    resolveTypes.add(HttpSeed.RESOLVETYPE_SEED_PAGE);
                    seed.setResolveTypes(resolveTypes);

                    Collection<HttpSeed> root_seeds = new ArrayList<HttpSeed>();
                    root_seeds.add(seed);

                    SYJHttpAnalyze syjHttpAnalyze = new SYJHttpAnalyze();
                    syjHttpAnalyze.setSyjTableBean(syjTableBean);

                    LocalTaskExcutor<HttpSeed, Collection<Object>> executor = new LocalTaskExcutor<HttpSeed, Collection<Object>>();
                    HttpSeedDispatcher<Collection<Object>> dispatcher = new HttpSeedDispatcher<Collection<Object>>(executor,syjHttpAnalyze);
                    HttpSeed2Task<Collection<Object>> root_task = new HttpSeed2Task<Collection<Object>>(root_seeds ,dispatcher);

                    executor.runRootTask(root_task);

                    logger.info("执行[" + syjTableBean.getTitle() + "]执行结束>>>>>>>>>>>>>>>>>>>>>>总数量[" + syjTableBean.getTotal()+ "],入库数量[" + syjTableBean.getCorrect() + "],错误数量[" + syjTableBean.getError() + "]");
                    logger.info("执行[" + syjTableBean.getTitle() + "]执行时间[" + (System.currentTimeMillis() - t1) +"ms]>>>>>>>>>>>>>>>>>>>>>>");

                    // 执行结束
                    syjTableBean.setUpdateTime(new Timestamp(new Date().getTime()));
                    syjTableBean.setStatus(3 + "");

                    syjTableBeanService.update(syjTableBean);
                }
                logger.info("============================[完成结果处理时间：" + (System.currentTimeMillis() - t1) + " ms ]===================================");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

