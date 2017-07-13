package com.rng.site.web.validators;

import com.rng.entities.Results;
import com.rng.security.SecurityService;
import com.rng.site.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by moldovanm2 on 7/11/2017.
 */

@Component
public class ResultValidator {
    @Autowired
    protected MessageSource messageSource;
    @Autowired protected SecurityService securityService;
    @Autowired protected ResultService resultService;


    public String validate(Object target1,Object target2)
    {
        String errors="";
        Integer test_id = (Integer) target1;
        Integer sample_id = (Integer) target2;
        List<Results> testsResult = resultService.getResultsByTestAndSampleId(test_id,sample_id);
        if(testsResult != null){
            errors="Results for test_id" +test_id.toString()+" already exist";
        }
        return errors;

    }


}
