package com.sun.lint_customize;

import com.android.annotations.NonNull;
import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.SourceCodeScanner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UFile;
import org.jetbrains.uast.java.JavaUFile;
import org.jetbrains.uast.visitor.AbstractUastVisitor;

import java.util.Collections;
import java.util.List;
//UAST - Unified Abstract Syntax Tree
public class DataClassDetector extends Detector implements SourceCodeScanner {

    public static Issue ISSUE = Issue.create("DataClassCheck",
            "DataClass不规范",
            "DataClass没有继承基类",
            Category.create("自定义Category",91),
            6,
            Severity.WARNING,
            new Implementation(DataClassDetector.class, Scope.JAVA_FILE_SCOPE));


    @com.android.annotations.Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        System.out.println("DataClassDetector  getApplicableUastTypes");//可以通过gradlew lint 看到打印信息
        return null;
    }


    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new DataClassChecker(context);
    }

    private static class DataClassChecker extends UElementHandler {
        private final JavaContext mContext;

        DataClassChecker(JavaContext context) {
            mContext = context;
        }

        @Override
        public void visitClass(@NotNull UClass node) {
            System.out.println("DataClassDetector  visitClass");//可以通过gradlew lint 看到打印信息
        }

        @Override
        public void visitFile(@NonNull UFile node) {
            System.out.println("DataClassDetector  visitFile");//可以通过gradlew lint 看到打印信息
            mContext.report(ISSUE,node,mContext.getLocation(node),"数据类没有实现BaseModel接");
            node.accept(new DataClassVisitor(mContext));
        }
    }

    private static class DataClassVisitor extends AbstractUastVisitor {
        private final JavaContext mContext;

        private DataClassVisitor(JavaContext context) {
            mContext = context;
        }

        @Override
        public boolean visitElement(@NotNull UElement node) {
            System.out.println("DataClassVisitor  visitElement");//可以通过gradlew lint 看到打印信息
            mContext.report(ISSUE,node,mContext.getLocation(node),"数据类没有实现BaseModel接");
            return super.visitElement(node);
        }

        @Override
        public boolean visitClass(@NotNull UClass node) {
            System.out.println("DataClassVisitor  visitClass");//可以通过gradlew lint 看到打印信息
            mContext.report(ISSUE,node,mContext.getNameLocation(node),"数据类没有实现BaseModel接");
            return super.visitClass(node);
        }

        @Override
        public boolean visitFile(@NotNull UFile node) {
            System.out.println("DataClassVisitor  visitFile");//可以通过gradlew lint 看到打印信息
            mContext.report(ISSUE,node,mContext.getLocation(node),"数据类没有实现BaseModel接");
            return super.visitFile(node);
        }
    }


//    @Nullable
//    @Override
//    public List<String> getApplicableMethodNames() {
//        System.out.println("DataClassDetector  getApplicableMethodNames  declaration.getText()");//可以通过gradlew lint 看到打印信息
//        return Collections.singletonList("onCreate");
//    }
//
//    @Override
//    public void visitMethodCall(@NotNull JavaContext context, @NotNull UCallExpression node,
//            @NotNull PsiMethod method) {
//        System.out.println("DataClassDetector  visitMethodCall  declaration.getText()");//可以通过gradlew lint 看到打印信息
//        context.report(ISSUE,node,context.getCallLocation(node, false, false),"数据类没有实现BaseModel接");
//    }




//    @NotNull
//    @Override
//    public EnumSet<Scope> getApplicableFiles() {
//        return Scope.ALL;
//    }
//
//    @Override
//    public void checkClass(@NotNull ClassContext context, @NotNull ClassNode classNode) {
//        super.checkClass(context, classNode);
//        System.out.println("DataClassDetector  afterCheckFile");//可以通过gradlew lint 看到打印信息
//        context.report(ISSUE, context.getLocation(classNode),"数据类没有实现BaseModel"
//                + "接checkClass");
//    }
//
//    @Override
//    public void afterCheckFile(@NotNull Context context) {
//        super.afterCheckFile(context);
//        System.out.println("DataClassDetector  afterCheckFile");//可以通过gradlew lint 看到打印信息
//        context.report(ISSUE, Location.create(context.file),"数据类没有实现BaseModel接after");
//
//    }
//
//    @Override
//    public void beforeCheckFile(@NotNull Context context) {
//        System.out.println("DataClassDetector  beforeCheckFile");//可以通过gradlew lint 看到打印信息
//        context.report(ISSUE, Location.create(context.file),"数据类没有实现BaseModel接");
//    }

    //    @Override
//    public void visitElement(@NotNull XmlContext context, @NotNull Element element) {
//        System.out.println("DataClassDetector  visitElement");//可以通过gradlew lint 看到打印信息
//    }
//
//    @Override
//    public void visitClass(@NotNull JavaContext context, @NotNull UClass declaration) {
//        System.out.println("DataClassDetector  visitElement");//可以通过gradlew lint 看到打印信息
//    }
//


//    @Override
//    public void visitClass(@NotNull JavaContext context, @NotNull UClass declaration) {
//        System.out.println("DataClassDetector  visitClass  declaration.getText()");//可以通过gradlew lint 看到打印信息
////        if(declaration.getText().startsWith("data class")){
////        declaration.getIdentifyingElement().
//            context.report(ISSUE,declaration,context.getLocation(declaration.getIdentifyingElement()
//            ),"数据类没有实现BaseModel接");
////        }
//    }


}
