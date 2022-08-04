package com.sun.lint_customize

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

class CustomizeIssueRegistry: IssueRegistry() {
    override val issues: List<Issue>
        get() = mutableListOf(ViewIdDetector.ISSUE,DataClassDetector.ISSUE)
}