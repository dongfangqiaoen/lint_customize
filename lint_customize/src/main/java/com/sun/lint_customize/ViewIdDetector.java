package com.sun.lint_customize;

import com.android.SdkConstants;
import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Collection;

public class ViewIdDetector extends Detector implements Detector.XmlScanner{
        /**
         * "ViewIdCheck" 是 Lint 规则的 id，必须是唯一的。
         * "ViewId命名不规范" 是简述。
         * "ViewIdName建议使用 view的缩写加上_xxx,例如tv_xxx, iv_xxx" 是详细解释。
         * 5 是优先级系数。必须是1到10之间的某个值。
         * ERROR 是严重程度
         * Implementation 是Detector间的桥梁，用于发现问题。Scope则用于确认分析范围。在本例中，我们必须处于资源文件层面才能分析前缀问题。
         */
        public static Issue ISSUE = Issue.create(
                "ViewIdCheck",
                "ViewId命名不规范",
                "ViewIdName建议使用 view的缩写加上_xxx,例如tv_xxx, iv_xxx",
                Category.create("自定义",91),
                5,
                Severity.INFORMATIONAL,
                new Implementation(ViewIdDetector.class, Scope.RESOURCE_FILE_SCOPE));

        /**
         * 自定义检测范围，layout文件
         * @param folderType
         * @return
         */
        @Override
        public boolean appliesTo(@NonNull ResourceFolderType folderType) {
            return folderType == ResourceFolderType.LAYOUT;
        }

        @Nullable
        @Override
        public Collection<String> getApplicableElements() {
            return Arrays.asList(SdkConstants.TEXT_VIEW, SdkConstants.IMAGE_VIEW, SdkConstants.BUTTON);
        }



        @Override
        public void visitElement(@NotNull XmlContext context, @NotNull Element element) {
            //判断是否设置了 id
            if (!element.hasAttributeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_ID)) {
                return;
            }
            //获取 id 命名，并进行前缀校验
            Attr attr = element.getAttributeNodeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_ID);
            String value = attr.getValue();
            if (value.startsWith(SdkConstants.NEW_ID_PREFIX)) {
                String idValue = value.substring(SdkConstants.NEW_ID_PREFIX.length());
                boolean matchRule = true;
                String expMsg = "";
                switch (element.getTagName()) {
                    case SdkConstants.TEXT_VIEW:
                        expMsg = "tv_";
                        matchRule = idValue.startsWith(expMsg);
                        break;
                    case SdkConstants.IMAGE_VIEW:
                        expMsg = "iv_";
                        matchRule = idValue.startsWith(expMsg);
                        break;
                    case SdkConstants.BUTTON:
                        expMsg = "btn_";
                        matchRule = idValue.startsWith(expMsg);
                        break;
                }
                if (!matchRule) {
                    context.report(ISSUE, attr, context.getLocation(attr),
                            "ViewIdName建议使用view的缩写_xxx; "+element.getTagName()+" 建议使用 "+ expMsg +" _xxx");
                }
            }
        }
    }
