/**
 *    Copyright 2006-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.internal.types;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.sql.Types;

/**
 * 扩展的Java类型处理器
 * Created by brucezee on 2017/8/23.
 */
public class ExtendedJavaTypeResolverDefaultImpl extends JavaTypeResolverDefaultImpl {
    public ExtendedJavaTypeResolverDefaultImpl() {
        super();

        //覆盖
        typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Integer.class.getName())));
    }

    @Override
    protected FullyQualifiedJavaType calculateBigDecimalReplacement(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer;

        if (column.getScale() > 0 || column.getLength() > 18 || forceBigDecimals) {
            answer = defaultType;
        } else if (column.getLength() > 9) {
            answer = new FullyQualifiedJavaType(Long.class.getName());
//        } else if (column.getLength() > 4) {
//            answer = new FullyQualifiedJavaType(Integer.class.getName());
        } else {
            answer = new FullyQualifiedJavaType(Integer.class.getName());
        }

        return answer;
    }
}
