package org.mixit.util.string

import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.springframework.stereotype.Service

@Service
class MarkdownRenderer {
    val flavour by lazy { CommonMarkFlavourDescriptor() }

    fun render(value: String): String {
        val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(value)
        return HtmlGenerator(value, parsedTree, flavour).generateHtml()
    }
}
