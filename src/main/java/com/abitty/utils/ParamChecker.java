package com.abitty.utils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * Created by kkk on 16/3/14.
 */
public class ParamChecker {

    public static String getConstraintMessage(Object object) {
        if (object == null) {
            return "null object";
        }

        Validator validator = new Validator();

        List<ConstraintViolation> constraintList = validator.validate(object);

        if (CollectionUtils.isEmpty(constraintList)) {
            return null;
        } else {
            return Joiner.on(";").join(Lists.transform(constraintList, new Function<ConstraintViolation, String>() {
                public String apply(ConstraintViolation input) {
                    return input.getMessage();
                }
            }));
        }
    }
}
