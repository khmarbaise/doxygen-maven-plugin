/**
 * The Doxygen Maven Plugin (dmp)
 *
 * Copyright (c) 2010, 2011, 2012, 2013, 2014, 2015 by SoftwareEntwicklung Beratung Schulung (SoEBeS)
 * Copyright (c) 2010, 2011, 2012, 2013, 2014, 2015 by Karl Heinz Marbaise
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soebes.maven.plugins.doxygen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.reporting.MavenReportException;
import org.apache.maven.toolchain.Toolchain;
import org.apache.maven.toolchain.ToolchainManager;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.StreamConsumer;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

/**
 * This abstract class contains all configurable parameters for the doxygen plugin.
 * 
 * @author Karl Heinz Marbaise
 */
public abstract class AbstractDoxygenMojo
    extends AbstractDoxygenConfigurationMojo
{

    /**
     * The OUTPUT_DIRECTORY tag is used to specify the (relative or absolute) base path where the generated
     * documentation will be put. If a relative path is entered, it will be relative to the location where doxygen was
     * started. If left blank the current directory will be used.
     */
    @Parameter( property = "doxygen.outputDirectory", defaultValue = "${project.reporting.outputDirectory}" )
    private File outputDirectory;

    /**
     * This defines the name and/or the absolute path to he doxygen executable. Usually you shouldn't change this, cause
     * the plugin will automatically search for the executable.
     * 
     */
    @Parameter( property = "doxygen.executable", defaultValue = "doxygen" )
    private String executable;

    /**
     * The current build session instance. This is used for toolchain manager API calls.
     *
     */
    @Parameter ( defaultValue = "${session}", required = true, readonly = true)
    private MavenSession session;

    /**
     * This is the basedir.
     */
    @Parameter( property = "basedir", defaultValue = "${project.basedir}", required = true, readonly = true )
    private File basedir;

    /**
     * This is the skip parameter to simply skip the doxygen generation part.
     * 
     */
    @Parameter( property = "skip", defaultValue = "false", required = true, readonly = true )
    private boolean skip;

    protected File getOutputDirectory()
    {
        if ( !outputDirectory.isAbsolute() )
        {
            outputDirectory = new File( getBasedir(), outputDirectory.getPath() );
        }
        // Create the folder structure.
        if ( !outputDirectory.exists() )
        {
            outputDirectory.mkdirs();
        }

        return outputDirectory.getAbsoluteFile();
    }

    /**
     * This code is lent from
     * http://fisheye.codehaus.org/browse/~raw,r=8882/mojo/trunk/mojo/exec-maven-plugin/src/main/java/org/codehaus/mojo/
     * exec/ExecMojo.java
     * 
     * @return Toolchain instance.
     */
    private Toolchain getToolchain()
    {
        Toolchain tc = null;

        try
        {
            if ( session != null ) // session is null in tests..
            {
                ToolchainManager toolchainManager =
                    (ToolchainManager) session.getContainer().lookup( ToolchainManager.ROLE );

                if ( toolchainManager != null )
                {
                    tc = toolchainManager.getToolchainFromBuildContext( "jdk", session );
                }
            }
        }
        catch ( ComponentLookupException componentLookupException )
        {
            // just ignore, could happen in pre-2.0.9 builds..
        }
        return tc;
    }

    /**
     * This will check if the given information for the doxygen executable is enough. If not than we search on the path
     * for doxygen executable.
     * 
     * @return Path to the doxygen executable.
     */
    private String getExecutablePath()
    {
        File execFile = new File( executable );
        if ( execFile.exists() )
        {
            getLog().debug( "Toolchains are ignored, 'executable' parameter is set to " + executable );
            return execFile.getAbsolutePath();
        }
        else
        {
            Toolchain tc = getToolchain();

            // if the file doesn't exist & toolchain is null, the exec is probably in the PATH...
            // we should probably also test for isFile and canExecute, but the second one is only
            // available in SDK 6.
            if ( tc != null )
            {
                getLog().info( "Toolchain in doxygen plugin: " + tc );
                executable = tc.findTool( executable );
            }
        }

        return executable;
    }

    /**
     * This method will get an existing configuration file for doxygen or will create a new one with the configured
     * parameters.
     * 
     * @return The configuration file.
     * @throws MavenReportException
     */
    public File buildConfigurationFile()
        throws MavenReportException
    {
        File ret = getConfigurationFile();

        if ( ret == null )
        {
            ret = new File( getOutputDirectory(), "doxygen.config" );
            buildConfigurationFile( ret );
        }
        else if ( !ret.exists() )
        {
            buildConfigurationFile( ret );
        }

        return ret;
    }

    /**
     * This method will create the configuration for calling doxygen. Furthermore is will put all configurable items
     * into the configuration file.
     * 
     * @param config The configuration.
     * @throws MavenReportException
     */
    public void buildConfigurationFile( File config )
        throws MavenReportException
    {
        config.getParentFile().mkdirs();

        PrintWriter out = null;
        try
        {
            out = new PrintWriter( new BufferedWriter( new FileWriter( config ) ) );
            addConfiguration( out, DoxygenParameters.DOXYFILE_ENCODING, getDoxyfileEncoding() );
            addConfiguration( out, DoxygenParameters.PROJECT_NAME, getProjectName() );
            addConfiguration( out, DoxygenParameters.PROJECT_NUMBER, getProjectNumber() );
            addConfiguration( out, DoxygenParameters.OUTPUT_DIRECTORY, getOutputDirectory().getAbsolutePath() );
            addConfiguration( out, DoxygenParameters.CREATE_SUBDIRS, isCreateSubdirs() );
            addConfiguration( out, DoxygenParameters.OUTPUT_LANGUAGE, getOutputLanguage() );
            addConfiguration( out, DoxygenParameters.BRIEF_MEMBER_DESC, isBriefMemberDesc() );
            addConfiguration( out, DoxygenParameters.REPEAT_BRIEF, isRepeatBrief() );
            addConfiguration( out, DoxygenParameters.ABBREVIATE_BRIEF, getAbbreviateBrief() );
            addConfiguration( out, DoxygenParameters.ALWAYS_DETAILED_SEC, isAlwaysDetailedSec() );
            addConfiguration( out, DoxygenParameters.INLINE_INHERITED_MEMB, isInlineInheritedMemb() );
            addConfiguration( out, DoxygenParameters.FULL_PATH_NAMES, isFullPathNames() );
            addConfiguration( out, DoxygenParameters.STRIP_FROM_PATH, getStripFromPath() );
            addConfiguration( out, DoxygenParameters.STRIP_FROM_INC_PATH, getStripFromIncPath() );
            addConfiguration( out, DoxygenParameters.SHORT_NAMES, isShortNames() );
            addConfiguration( out, DoxygenParameters.JAVADOC_AUTOBRIEF, isJavadocAutobrief() );
            addConfiguration( out, DoxygenParameters.QT_AUTOBRIEF, isQtAutobrief() );
            addConfiguration( out, DoxygenParameters.MULTILINE_CPP_IS_BRIEF, isMultilineCppIsBrief() );
            addConfiguration( out, DoxygenParameters.INHERIT_DOCS, isInheritDocs() );
            addConfiguration( out, DoxygenParameters.SEPARATE_MEMBER_PAGES, isSeparateMemberPages() );
            addConfiguration( out, DoxygenParameters.TAB_SIZE, getTabSize() );
            addConfiguration( out, DoxygenParameters.ALIASES, getAliases() );
            addConfiguration( out, DoxygenParameters.OPTIMIZE_OUTPUT_FOR_C, isOptimizeOutputForC() );
            addConfiguration( out, DoxygenParameters.OPTIMIZE_OUTPUT_JAVA, isOptimizeOutputJava() );
            addConfiguration( out, DoxygenParameters.OPTIMIZE_FOR_FORTRAN, isOptimizeForFortran() );
            addConfiguration( out, DoxygenParameters.OPTIMIZE_OUTPUT_VHDL, isOptimizeOutputVhdl() );
            addConfiguration( out, DoxygenParameters.BUILTIN_STL_SUPPORT, isBuiltinStlSupport() );
            addConfiguration( out, DoxygenParameters.CPP_CLI_SUPPORT, isCppCliSupport() );
            addConfiguration( out, DoxygenParameters.SIP_SUPPORT, isSipSupport() );
            addConfiguration( out, DoxygenParameters.IDL_PROPERTY_SUPPORT, isIdlPropertySupport() );
            addConfiguration( out, DoxygenParameters.DISTRIBUTE_GROUP_DOC, isDistributeGroupDoc() );
            addConfiguration( out, DoxygenParameters.SUBGROUPING, isSubgrouping() );
            addConfiguration( out, DoxygenParameters.TYPEDEF_HIDES_STRUCT, isTypedefHidesStruct() );
            addConfiguration( out, DoxygenParameters.SYMBOL_CACHE_SIZE, getSymbolCacheSize() );
            addConfiguration( out, DoxygenParameters.EXTRACT_ALL, isExtractAll() );
            addConfiguration( out, DoxygenParameters.EXTRACT_PRIVATE, isExtractPrivate() );
            addConfiguration( out, DoxygenParameters.EXTRACT_STATIC, isExtractStatic() );
            addConfiguration( out, DoxygenParameters.EXTRACT_LOCAL_CLASSES, isExtractLocalClasses() );
            addConfiguration( out, DoxygenParameters.EXTRACT_LOCAL_METHODS, isExtractLocalMethods() );
            addConfiguration( out, DoxygenParameters.EXTRACT_ANON_NSPACES, isExtractAnonNspaces() );
            addConfiguration( out, DoxygenParameters.HIDE_UNDOC_MEMBERS, isHideUndocMembers() );
            addConfiguration( out, DoxygenParameters.HIDE_UNDOC_CLASSES, isHideUndocClasses() );
            addConfiguration( out, DoxygenParameters.HIDE_FRIEND_COMPOUNDS, isHideFriendCompounds() );
            addConfiguration( out, DoxygenParameters.HIDE_IN_BODY_DOCS, isHideInBodyDocs() );
            addConfiguration( out, DoxygenParameters.INTERNAL_DOCS, isInternalDocs() );
            addConfiguration( out, DoxygenParameters.CASE_SENSE_NAMES, isCaseSenseNames() );
            addConfiguration( out, DoxygenParameters.HIDE_SCOPE_NAMES, isHideScopeNames() );
            addConfiguration( out, DoxygenParameters.SHOW_INCLUDE_FILES, isShowIncludeFiles() );
            addConfiguration( out, DoxygenParameters.INLINE_INFO, isInlineInfo() );
            addConfiguration( out, DoxygenParameters.SORT_MEMBER_DOCS, isSortMemberDocs() );
            addConfiguration( out, DoxygenParameters.SORT_BRIEF_DOCS, isSortBriefDocs() );
            addConfiguration( out, DoxygenParameters.SORT_GROUP_NAMES, isSortGroupNames() );
            addConfiguration( out, DoxygenParameters.SORT_BY_SCOPE_NAME, isSortByScopeName() );
            addConfiguration( out, DoxygenParameters.GENERATE_TODOLIST, isGenerateTodolist() );
            addConfiguration( out, DoxygenParameters.GENERATE_TESTLIST, isGenerateTestlist() );
            addConfiguration( out, DoxygenParameters.GENERATE_BUGLIST, isGenerateBuglist() );
            addConfiguration( out, DoxygenParameters.GENERATE_DEPRECATEDLIST, isGenerateDeprecatedlist() );
            addConfiguration( out, DoxygenParameters.ENABLED_SECTIONS, getEnabledSections() );
            addConfiguration( out, DoxygenParameters.MAX_INITIALIZER_LINES, getMaxInitializerLines() );
            addConfiguration( out, DoxygenParameters.SHOW_USED_FILES, isShowUsedFiles() );
            addConfiguration( out, DoxygenParameters.SHOW_DIRECTORIES, isShowDirectories() );
            addConfiguration( out, DoxygenParameters.SHOW_FILES, isShowFiles() );
            addConfiguration( out, DoxygenParameters.SHOW_NAMESPACES, isShowNamespaces() );
            addConfiguration( out, DoxygenParameters.FILE_VERSION_FILTER, getFileVersionFilter() );
            addConfiguration( out, DoxygenParameters.LAYOUT_FILE, getLayoutFile() );
            addConfiguration( out, DoxygenParameters.QUIET, isQuiet() );
            addConfiguration( out, DoxygenParameters.WARNINGS, isWarnings() );
            addConfiguration( out, DoxygenParameters.WARN_IF_UNDOCUMENTED, isWarnIfUndocumented() );
            addConfiguration( out, DoxygenParameters.WARN_IF_DOC_ERROR, isWarnIfDocError() );
            addConfiguration( out, DoxygenParameters.WARN_NO_PARAMDOC, isWarnNoParamdoc() );
            addConfiguration( out, DoxygenParameters.WARN_FORMAT, getWarnFormat() );
            addConfiguration( out, DoxygenParameters.WARN_LOGFILE, getWarnLogfile() );
            addConfiguration( out, DoxygenParameters.INPUT, getInput() );
            addConfiguration( out, DoxygenParameters.INPUT_ENCODING, getInputEncoding() );
            addConfiguration( out, DoxygenParameters.FILE_PATTERNS, getFilePatterns() );
            addConfiguration( out, DoxygenParameters.RECURSIVE, isRecursive() );
            addConfiguration( out, DoxygenParameters.EXCLUDE, getExclude() );
            addConfiguration( out, DoxygenParameters.EXCLUDE_SYMLINKS, isExcludeSymlinks() );
            addConfiguration( out, DoxygenParameters.EXCLUDE_PATTERNS, getExcludePatterns() );
            addConfiguration( out, DoxygenParameters.EXCLUDE_SYMBOLS, getExcludeSymbols() );
            addConfiguration( out, DoxygenParameters.EXAMPLE_PATH, getExamplePath() );
            addConfiguration( out, DoxygenParameters.EXAMPLE_PATTERNS, getExamplePatterns() );
            addConfiguration( out, DoxygenParameters.EXAMPLE_RECURSIVE, isExampleRecursive() );
            addConfiguration( out, DoxygenParameters.IMAGE_PATH, getImagePath() );
            addConfiguration( out, DoxygenParameters.INPUT_FILTER, getInputFilter() );
            addConfiguration( out, DoxygenParameters.FILTER_PATTERNS, getFilterPatterns() );
            addConfiguration( out, DoxygenParameters.FILTER_SOURCE_FILES, isFilterSourceFiles() );
            addConfiguration( out, DoxygenParameters.SOURCE_BROWSER, isSourceBrowser() );
            addConfiguration( out, DoxygenParameters.INLINE_SOURCES, isInlineSources() );
            addConfiguration( out, DoxygenParameters.STRIP_CODE_COMMENTS, isStripCodeComments() );
            addConfiguration( out, DoxygenParameters.REFERENCED_BY_RELATION, isReferencedByRelation() );
            addConfiguration( out, DoxygenParameters.REFERENCES_RELATION, isReferencesRelation() );
            addConfiguration( out, DoxygenParameters.REFERENCES_LINK_SOURCE, isReferencesLinkSource() );
            addConfiguration( out, DoxygenParameters.USE_HTAGS, isUseHtags() );
            addConfiguration( out, DoxygenParameters.VERBATIM_HEADERS, isVerbatimHeaders() );
            addConfiguration( out, DoxygenParameters.ALPHABETICAL_INDEX, isAlphabeticalIndex() );
            addConfiguration( out, DoxygenParameters.COLS_IN_ALPHA_INDEX, getColsInAlphaIndex() );
            addConfiguration( out, DoxygenParameters.IGNORE_PREFIX, getIgnorePrefix() );
            addConfiguration( out, DoxygenParameters.GENERATE_HTML, isGenerateHtml() );
            addConfiguration( out, DoxygenParameters.HTML_OUTPUT, getHtmlOutput() );
            addConfiguration( out, DoxygenParameters.HTML_FILE_EXTENSION, getHtmlFileExtension() );
            addConfiguration( out, DoxygenParameters.HTML_HEADER, getHtmlHeader() );
            addConfiguration( out, DoxygenParameters.HTML_FOOTER, getHtmlFooter() );
            addConfiguration( out, DoxygenParameters.HTML_STYLESHEET, getHtmlStylesheet() );
            addConfiguration( out, DoxygenParameters.HTML_ALIGN_MEMBERS, isHtmlAlignMembers() );
            addConfiguration( out, DoxygenParameters.HTML_DYNAMIC_SECTIONS, isHtmlDynamicSections() );
            addConfiguration( out, DoxygenParameters.GENERATE_DOCSET, isGenerateDocset() );
            addConfiguration( out, DoxygenParameters.DOCSET_FEEDNAME, getDocsetFeedname() );
            addConfiguration( out, DoxygenParameters.DOCSET_BUNDLE_ID, getDocsetBundleId() );
            addConfiguration( out, DoxygenParameters.GENERATE_HTMLHELP, isGenerateHtmlhelp() );
            addConfiguration( out, DoxygenParameters.CHM_FILE, getChmFile() );
            addConfiguration( out, DoxygenParameters.HHC_LOCATION, getHhcLocation() );
            addConfiguration( out, DoxygenParameters.GENERATE_CHI, isGenerateChi() );
            addConfiguration( out, DoxygenParameters.CHM_INDEX_ENCODING, getChmIndexEncoding() );
            addConfiguration( out, DoxygenParameters.BINARY_TOC, isBinaryToc() );
            addConfiguration( out, DoxygenParameters.TOC_EXPAND, isTocExpand() );
            addConfiguration( out, DoxygenParameters.GENERATE_QHP, isGenerateQhp() );
            addConfiguration( out, DoxygenParameters.QCH_FILE, getQchFile() );
            addConfiguration( out, DoxygenParameters.QHP_NAMESPACE, getQhpNamespace() );
            addConfiguration( out, DoxygenParameters.QHP_VIRTUAL_FOLDER, getQhpVirtualFolder() );
            addConfiguration( out, DoxygenParameters.QHG_LOCATION, getQhgLocation() );
            addConfiguration( out, DoxygenParameters.DISABLE_INDEX, isDisableIndex() );
            addConfiguration( out, DoxygenParameters.ENUM_VALUES_PER_LINE, getEnumValuesPerLine() );
            addConfiguration( out, DoxygenParameters.GENERATE_TREEVIEW, getGenerateTreeview() );
            addConfiguration( out, DoxygenParameters.TREEVIEW_WIDTH, getTreeviewWidth() );
            addConfiguration( out, DoxygenParameters.FORMULA_FONTSIZE, getFormulaFontsize() );
            addConfiguration( out, DoxygenParameters.GENERATE_LATEX, isGenerateLatex() );
            addConfiguration( out, DoxygenParameters.LATEX_OUTPUT, getLatexOutput() );
            addConfiguration( out, DoxygenParameters.LATEX_CMD_NAME, getLatexCmdName() );
            addConfiguration( out, DoxygenParameters.MAKEINDEX_CMD_NAME, getMakeindexCmdName() );
            addConfiguration( out, DoxygenParameters.COMPACT_LATEX, isCompactLatex() );
            addConfiguration( out, DoxygenParameters.PAPER_TYPE, getPaperType() );
            addConfiguration( out, DoxygenParameters.EXTRA_PACKAGES, getExtraPackages() );
            addConfiguration( out, DoxygenParameters.LATEX_HEADER, getLatexHeader() );
            addConfiguration( out, DoxygenParameters.PDF_HYPERLINKS, isPdfHyperlinks() );
            addConfiguration( out, DoxygenParameters.USE_PDFLATEX, isUsePdflatex() );
            addConfiguration( out, DoxygenParameters.LATEX_BATCHMODE, isLatexBatchmode() );
            addConfiguration( out, DoxygenParameters.LATEX_HIDE_INDICES, isLatexHideIndices() );
            addConfiguration( out, DoxygenParameters.GENERATE_RTF, isGenerateRtf() );
            addConfiguration( out, DoxygenParameters.RTF_OUTPUT, getRtfOutput() );
            addConfiguration( out, DoxygenParameters.COMPACT_RTF, isCompactRtf() );
            addConfiguration( out, DoxygenParameters.RTF_HYPERLINKS, isRtfHyperlinks() );
            addConfiguration( out, DoxygenParameters.RTF_STYLESHEET_FILE, getRtfStylesheetFile() );
            addConfiguration( out, DoxygenParameters.RTF_EXTENSIONS_FILE, getRtfExtensionsFile() );
            addConfiguration( out, DoxygenParameters.GENERATE_MAN, isGenerateMan() );
            addConfiguration( out, DoxygenParameters.MAN_OUTPUT, getManOutput() );
            addConfiguration( out, DoxygenParameters.MAN_EXTENSION, getManExtension() );
            addConfiguration( out, DoxygenParameters.MAN_LINKS, isManLinks() );
            addConfiguration( out, DoxygenParameters.GENERATE_XML, isGenerateXml() );
            addConfiguration( out, DoxygenParameters.XML_OUTPUT, getXmlOutput() );
            addConfiguration( out, DoxygenParameters.XML_SCHEMA, getXmlSchema() );
            addConfiguration( out, DoxygenParameters.XML_DTD, getXmlDtd() );
            addConfiguration( out, DoxygenParameters.XML_PROGRAMLISTING, isXmlProgramlisting() );
            addConfiguration( out, DoxygenParameters.GENERATE_AUTOGEN_DEF, isGenerateAutogenDef() );
            addConfiguration( out, DoxygenParameters.GENERATE_PERLMOD, isGeneratePerlmod() );
            addConfiguration( out, DoxygenParameters.PERLMOD_LATEX, isPerlmodLatex() );
            addConfiguration( out, DoxygenParameters.PERLMOD_PRETTY, isPerlmodPretty() );
            addConfiguration( out, DoxygenParameters.PERLMOD_MAKEVAR_PREFIX, getPerlmodMakevarPrefix() );
            addConfiguration( out, DoxygenParameters.ENABLE_PREPROCESSING, isEnablePreprocessing() );
            addConfiguration( out, DoxygenParameters.MACRO_EXPANSION, isMacroExpansion() );
            addConfiguration( out, DoxygenParameters.EXPAND_ONLY_PREDEF, isExpandOnlyPredef() );
            addConfiguration( out, DoxygenParameters.SEARCH_INCLUDES, isSearchIncludes() );
            addConfiguration( out, DoxygenParameters.INCLUDE_PATH, getIncludePath() );
            addConfiguration( out, DoxygenParameters.INCLUDE_FILE_PATTERNS, getIncludeFilePatterns() );
            addConfiguration( out, DoxygenParameters.PREDEFINED, getPredefined() );
            addConfiguration( out, DoxygenParameters.EXPAND_AS_DEFINED, getExpandAsDefined() );
            addConfiguration( out, DoxygenParameters.SKIP_FUNCTION_MACROS, isSkipFunctionMacros() );
            addConfiguration( out, DoxygenParameters.TAGFILES, getTagfiles() );
            addConfiguration( out, DoxygenParameters.GENERATE_TAGFILE, getGenerateTagfile() );
            addConfiguration( out, DoxygenParameters.ALLEXTERNALS, isAllexternals() );
            addConfiguration( out, DoxygenParameters.EXTERNAL_GROUPS, isExternalGroups() );
            addConfiguration( out, DoxygenParameters.PERL_PATH, getPerlPath() );
            addConfiguration( out, DoxygenParameters.CLASS_DIAGRAMS, isClassDiagrams() );
            addConfiguration( out, DoxygenParameters.MSCGEN_PATH, getMscgenPath() );
            addConfiguration( out, DoxygenParameters.HIDE_UNDOC_RELATIONS, isHideUndocRelations() );
            addConfiguration( out, DoxygenParameters.HAVE_DOT, isHaveDot() );
            addConfiguration( out, DoxygenParameters.DOT_FONTNAME, getDotFontname() );
            addConfiguration( out, DoxygenParameters.DOT_FONTSIZE, getDotFontsize() );
            addConfiguration( out, DoxygenParameters.DOT_FONTPATH, getDotFontpath() );
            addConfiguration( out, DoxygenParameters.CLASS_GRAPH, isClassGraph() );
            addConfiguration( out, DoxygenParameters.COLLABORATION_GRAPH, isCollaborationGraph() );
            addConfiguration( out, DoxygenParameters.GROUP_GRAPHS, isGroupGraphs() );
            addConfiguration( out, DoxygenParameters.UML_LOOK, isUmlLook() );
            addConfiguration( out, DoxygenParameters.TEMPLATE_RELATIONS, isTemplateRelations() );
            addConfiguration( out, DoxygenParameters.INCLUDE_GRAPH, isIncludeGraph() );
            addConfiguration( out, DoxygenParameters.INCLUDED_BY_GRAPH, isIncludedByGraph() );
            addConfiguration( out, DoxygenParameters.CALL_GRAPH, isCallGraph() );
            addConfiguration( out, DoxygenParameters.CALLER_GRAPH, isCallerGraph() );
            addConfiguration( out, DoxygenParameters.GRAPHICAL_HIERARCHY, isGraphicalHierarchy() );
            addConfiguration( out, DoxygenParameters.DIRECTORY_GRAPH, isDirectoryGraph() );
            addConfiguration( out, DoxygenParameters.DOT_IMAGE_FORMAT, getDotImageFormat() );
            addConfiguration( out, DoxygenParameters.DOT_PATH, getDotPath() );
            addConfiguration( out, DoxygenParameters.DOTFILE_DIRS, getDotfileDirs() );
            addConfiguration( out, DoxygenParameters.DOT_GRAPH_MAX_NODES, getDotGraphMaxNodes() );
            addConfiguration( out, DoxygenParameters.MAX_DOT_GRAPH_DEPTH, getMaxDotGraphDepth() );
            addConfiguration( out, DoxygenParameters.DOT_TRANSPARENT, isDotTransparent() );
            addConfiguration( out, DoxygenParameters.DOT_MULTI_TARGETS, isDotMultiTargets() );
            addConfiguration( out, DoxygenParameters.GENERATE_LEGEND, isGenerateLegend() );
            addConfiguration( out, DoxygenParameters.DOT_CLEANUP, isDotCleanup() );
            addConfiguration( out, DoxygenParameters.SEARCHENGINE, isSearchengine() );

        }
        catch ( IOException ex )
        {
            throw new MavenReportException( "Error creating Doxygen configuration file '" + config.getAbsolutePath()
                + "'.", ex );
        }
        finally
        {
            if ( out != null )
            {
                out.close();
            }
        }
    }

    /**
     * This is called for boolean configuration items.
     * 
     * @param config The configuration where to write to.
     * @param key The configuration parameter name with it's default values etc.
     * @param value The reals value, base on the Plugin configuration.
     */
    private void addConfiguration( PrintWriter config, DoxygenParameters key, boolean value )
    {
        if ( value )
        {
            addConfiguration( config, key, "YES" );
        }
        else
        {
            addConfiguration( config, key, "NO" );
        }
    }

    /**
     * This is called for integer configuration items.
     * 
     * @param config The configuration where to write to.
     * @param key The configuration parameter name with it's default values etc.
     * @param value The reals value, base on the Plugin configuration.
     */
    private void addConfiguration( PrintWriter config, DoxygenParameters key, Integer value )
    {
        if ( value == null )
        {
            value = Integer.parseInt( key.getDefaultValue() );
        }
        addConfiguration( config, key, value.toString() );
    }

    /**
     * This is called for String configuration items.
     * 
     * @param config The configuration where to write to.
     * @param key The configuration parameter name with it's default values etc.
     * @param value The reals value, base on the Plugin configuration.
     */
    private void addConfiguration( PrintWriter config, DoxygenParameters key, String value )
    {
        if ( value == null )
        {
            value = key.getDefaultValue();
        }
        else if ( ( value.length() == 0 ) || ( value.trim().length() == 0 ) )
        {
            value = key.getDefaultValue();
        }

        // If we have quoted parameters.
        if ( key.getType().equals( DoxygenParameterType.STRING_QUOTED ) )
        {
            value = '"' + value + '"';
        }

        addConfiguration( config, key.getDescription(), key.name(), value );
    }

    /**
     * This will write all kind of configuration items into the configuration file and will format the file.
     * 
     * @param config The output file.
     * @param desc The description of the item.
     * @param key The name of the item
     * @param value The value of the item.
     */
    private void addConfiguration( PrintWriter config, String desc, String key, String value )
    {
        // Description first.
        config.print( desc );
        config.println();
        config.printf( "%-22s", key );
        config.print( " = " );
        if ( value != null )
        {
            config.println( value );
        }
        else
        {
            config.println();
        }
        config.println();
    }

    /**
     * @param unusedLocale the wanted locale (actually unused).
     * @throws MavenReportException if any
     */
    protected void executeReport( Locale unusedLocale )
        throws MavenReportException
    {

        File config = buildConfigurationFile();

        Commandline cli = new Commandline();
        cli.setWorkingDirectory( getBasedir().getAbsolutePath() );
        cli.setExecutable( getExecutablePath() );
        cli.createArgument().setValue( config.getAbsolutePath() );

        Writer stringWriter = new StringWriter();
        StreamConsumer out = new WriterStreamConsumer( stringWriter );
        StreamConsumer err = new WriterStreamConsumer( stringWriter );

        try
        {
            int returnCode = CommandLineUtils.executeCommandLine( cli, out, err );

            if ( !isQuiet() )
            {
                // Get all output from doxygen and put it to the log out of Maven.
                String[] lines = stringWriter.toString().split( "\n" );
                for ( int i = 0; i < lines.length; i++ )
                {
                    lines[i] = lines[i].replaceAll( "\n|\r", "" );
                    getLog().info( "doxygen: " + lines[i] );
                }
            }

            if ( returnCode != 0 )
            {
                throw new MavenReportException( "Failed to generate Doxygen documentation." );
            }

        }
        catch ( CommandLineException ex )
        {
            throw new MavenReportException( "Error while executing Doxygen.", ex );
        }

    }

    public void setBasedir( File basedir )
    {
        this.basedir = basedir;
    }

    public File getBasedir()
    {
        return basedir;
    }

    public void setOutputDirectory( File outputDirectory )
    {
        this.outputDirectory = outputDirectory;
    }

    public boolean isSkip()
    {
        return skip;
    }

    public void setSkip( boolean skip )
    {
        this.skip = skip;
    }

}
