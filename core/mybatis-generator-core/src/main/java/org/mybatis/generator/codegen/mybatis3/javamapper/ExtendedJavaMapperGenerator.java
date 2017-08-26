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
package org.mybatis.generator.codegen.mybatis3.javamapper;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.ExtendedXMLMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 *
 * Created by brucezee on 2017/8/22.
 */
public class ExtendedJavaMapperGenerator extends JavaMapperGenerator {

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        String rootInterface = introspectedTable
                .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                    .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType());

            interfaze.addImportedType(fqjt);
            interfaze.addImportedType(modelType);
            interfaze.addImportedType(exampleType);

            fqjt.addTypeArgument(modelType);
            fqjt.addTypeArgument(exampleType);

            interfaze.addSuperInterface(fqjt);

            interfaze.addAnnotation("@Repository");
            interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        }

//        addCountByExampleMethod(interfaze);
//        addDeleteByExampleMethod(interfaze);
//        addDeleteByPrimaryKeyMethod(interfaze);
//        addInsertMethod(interfaze);
//        addInsertSelectiveMethod(interfaze);
//        addSelectByExampleWithBLOBsMethod(interfaze);
//        addSelectByExampleWithoutBLOBsMethod(interfaze);
//        addSelectByPrimaryKeyMethod(interfaze);
//        addUpdateByExampleSelectiveMethod(interfaze);
//        addUpdateByExampleWithBLOBsMethod(interfaze);
//        addUpdateByExampleWithoutBLOBsMethod(interfaze);
//        addUpdateByPrimaryKeySelectiveMethod(interfaze);
//        addUpdateByPrimaryKeyWithBLOBsMethod(interfaze);
//        addUpdateByPrimaryKeyWithoutBLOBsMethod(interfaze);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(interfaze, null,
                introspectedTable)) {
            answer.add(interfaze);
        }

        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }

        return answer;
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return new ExtendedXMLMapperGenerator();
    }
}
