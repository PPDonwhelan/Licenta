package com.rng.site.service;

import com.rng.catalog.SubjectsRepository;
import com.rng.catalog.TestCategoryRepository;
import com.rng.catalog.TestsRepository;
import com.rng.entities.Results;
import com.rng.entities.Sample;
import com.rng.entities.TestCategory;
import com.rng.entities.Tests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoldovanM2 on 7/15/2017.
 */
@Service
public class StatisticsService {
    @Autowired
    TestCategoryRepository categoryRepository;
    @Autowired
    SubjectsRepository subjectsRepository;
    @Autowired
    TestsRepository testsRepository;

    public List<Integer> getStatisticsCategory(TestCategory category) {

        int good=0;
        int bad=0;
        List<Integer> results_list=new ArrayList<>();
        for (int i=0; i<category.getSamples().size(); i++)
        {
            Sample sample=category.getSamples().get(i);
            List<Results> results=sample.getResults();
            int all_good=1;
            int all_bad=1;
            for (int j =0; j<results.size(); j++)
            {
                Double p_value=results.get(j).getP_value();
                Double low= results.get(j).getLow();
                Double high= results.get(j).getHigh();
                if (!((p_value>low)&&(p_value<high))){
                    all_good=0;
                }else all_bad=0;


            }
            if (all_good==1){
                good++;
            }
            else{
                if (all_bad==1) {
                    bad++;
                }
            }
        }

        results_list.add(good);
        results_list.add(bad);
        return results_list;
    }

    public List<Integer> getStatisticsPerTest(Tests test) {

        int good=0;
        int bad=0;
        List<Integer> results_list=new ArrayList<>();
        List<Results> results =test.getResults();
            int all_good=1;
            int all_bad=1;
            for (int j =0; j<results.size(); j++)
            {
                Double p_value=results.get(j).getP_value();
                Double low= results.get(j).getLow();
                Double high= results.get(j).getHigh();
                if (!((p_value>low)&&(p_value<high))){
                    all_good=0;
                }else all_bad=0;


            }
            if (all_good==1){
                good++;
            }
            else{
                if (all_bad==1) {
                    bad++;
                }
            }


        results_list.add(good);
        results_list.add(bad);
        return results_list;
    }


    public List<Integer> getStatisticsTests(TestCategory category) {

        List<Integer> results_list=new ArrayList<>();
        int frecuency=0;
        int gap=0;
        int poker=0;
        int coupon=0;
        int max_of_t=0;
        for (int i=0; i<category.getSamples().size(); i++)
        {
            Sample sample=category.getSamples().get(i);
            List<Results> results=sample.getResults();
            for (int j =0; j<results.size(); j++)
            {
                Double p_value=results.get(j).getP_value();
                Double low= results.get(j).getLow();
                Double high= results.get(j).getHigh();
                if ((p_value>low)&&(p_value<high)){
                    if(results.get(j).getTest().getId()==1){
                        frecuency++;
                    }

                    if (results.get(j).getTest().getId()==3){
                            gap++;
                        }
                    if (results.get(j).getTest().getId()==4){
                            poker++;
                        }
                    if (results.get(j).getTest().getId()==5){
                            coupon++;}
                    if (results.get(j).getTest().getId()==10){
                            max_of_t++;
                        }
                    }
                }


            }

        results_list.add(frecuency);
        results_list.add(gap);
        results_list.add(poker);
        results_list.add(coupon);
        if(max_of_t!=0) {
            results_list.add(max_of_t);
        }



        return results_list;
    }
}
