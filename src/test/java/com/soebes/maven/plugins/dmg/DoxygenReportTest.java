/**
 * The Doxygen Maven Plugin (dmp)
 *
 * Copyright (c) 2010, 2011 by SoftwareEntwicklung Beratung Schulung (SoEBeS)
 * Copyright (c) 2010, 2011 by Karl Heinz Marbaise
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
package com.soebes.maven.plugins.dmg;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import junit.framework.Assert;


import org.apache.maven.reporting.MavenReportException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.soebes.maven.plugins.dmg.DoxygenReport;


/**
 * These are the Unit test which check if a value will be written to
 * the configuration file.
 * In every test case the file with a value for a particular configuration
 * item is created than the file is read and the item is compared with the
 * stored value.
 * Afterwards the created file is removed.
 * 
 * @author Karl Heinz Marbaise
 *
 */
public class DoxygenReportTest extends TestDoxygenBase {

	private DoxygenReport dr = new DoxygenReport();
	private File resultConfigFile = new File(getTargetDir() + "/result.config");

	@BeforeMethod
	public void beforeMethod() {
		dr = new DoxygenReport();
		dr.setOutputDirectory(new File(getTestResourcesDirectory()));
		dr.setReportOutputDirectory(new File(getTestResourcesDirectory()));

		dr.setBasedir(new File(getTestResourcesDirectory()));
	}

	@AfterMethod
	public void afterMethod() {
		// Remove any existing file from earlier runnings...
		Assert.assertTrue(resultConfigFile.delete());
	}

	@Test
	public void firstTest() throws MavenReportException {
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
	}
	
	@Test
	public void doxyfileEncodingTest() throws MavenReportException, IOException {
		dr.setDoxyfileEncoding("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOXYFILE_ENCODING"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("DOXYFILE_ENCODING"));
	}
	@Test
	public void projectNameTest() throws MavenReportException, IOException {
		dr.setProjectName("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("PROJECT_NAME"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("PROJECT_NAME"));
	}
	@Test
	public void projectNumberTest() throws MavenReportException, IOException {
		dr.setProjectNumber("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("PROJECT_NUMBER"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("PROJECT_NUMBER"));
	}
//	@Test
//	public void outputDirectoryTest() throws MavenReportException, IOException {
//		dr.setOutputDirectory("ThisIsMoreThanEmpty");
//		dr.buildConfigurationFile(resultConfigFile);
//		Assert.assertEquals(true, resultConfigFile.exists());
//		HashMap<String, String> configList = readConfigFile(resultConfigFile);
//		Assert.assertEquals(true, configList.containsKey("OUTPUT_DIRECTORY"));
//		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("OUTPUT_DIRECTORY"));
//	}
	@Test
	public void createSubdirsTest() throws MavenReportException, IOException {
		dr.setCreateSubdirs(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("CREATE_SUBDIRS"));
		Assert.assertEquals("YES", configList.get("CREATE_SUBDIRS"));
	}
	@Test
	public void outputLanguageTest() throws MavenReportException, IOException {
		dr.setOutputLanguage("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("OUTPUT_LANGUAGE"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("OUTPUT_LANGUAGE"));
	}
	@Test
	public void briefMemberDescTest() throws MavenReportException, IOException {
		dr.setBriefMemberDesc(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("BRIEF_MEMBER_DESC"));
		Assert.assertEquals("NO", configList.get("BRIEF_MEMBER_DESC"));
	}
	@Test
	public void repeatBriefTest() throws MavenReportException, IOException {
		dr.setRepeatBrief(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("REPEAT_BRIEF"));
		Assert.assertEquals("NO", configList.get("REPEAT_BRIEF"));
	}
	@Test
	public void abbreviateBriefTest() throws MavenReportException, IOException {
		dr.setAbbreviateBrief("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("ABBREVIATE_BRIEF"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("ABBREVIATE_BRIEF"));
	}
	@Test
	public void alwaysDetailedSecTest() throws MavenReportException, IOException {
		dr.setAlwaysDetailedSec(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("ALWAYS_DETAILED_SEC"));
		Assert.assertEquals("YES", configList.get("ALWAYS_DETAILED_SEC"));
	}
	@Test
	public void inlineInheritedMembTest() throws MavenReportException, IOException {
		dr.setInlineInheritedMemb(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INLINE_INHERITED_MEMB"));
		Assert.assertEquals("YES", configList.get("INLINE_INHERITED_MEMB"));
	}
	@Test
	public void fullPathNamesTest() throws MavenReportException, IOException {
		dr.setFullPathNames(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("FULL_PATH_NAMES"));
		Assert.assertEquals("NO", configList.get("FULL_PATH_NAMES"));
	}
	@Test
	public void stripFromPathTest() throws MavenReportException, IOException {
		dr.setStripFromPath("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("STRIP_FROM_PATH"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("STRIP_FROM_PATH"));
	}
	@Test
	public void stripFromIncPathTest() throws MavenReportException, IOException {
		dr.setStripFromIncPath("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("STRIP_FROM_INC_PATH"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("STRIP_FROM_INC_PATH"));
	}
	@Test
	public void shortNamesTest() throws MavenReportException, IOException {
		dr.setShortNames(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SHORT_NAMES"));
		Assert.assertEquals("YES", configList.get("SHORT_NAMES"));
	}
	@Test
	public void javadocAutobriefTest() throws MavenReportException, IOException {
		dr.setJavadocAutobrief(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("JAVADOC_AUTOBRIEF"));
		Assert.assertEquals("YES", configList.get("JAVADOC_AUTOBRIEF"));
	}
	@Test
	public void qtAutobriefTest() throws MavenReportException, IOException {
		dr.setQtAutobrief(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("QT_AUTOBRIEF"));
		Assert.assertEquals("YES", configList.get("QT_AUTOBRIEF"));
	}
	@Test
	public void multilineCppIsBriefTest() throws MavenReportException, IOException {
		dr.setMultilineCppIsBrief(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("MULTILINE_CPP_IS_BRIEF"));
		Assert.assertEquals("YES", configList.get("MULTILINE_CPP_IS_BRIEF"));
	}
	@Test
	public void inheritDocsTest() throws MavenReportException, IOException {
		dr.setInheritDocs(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INHERIT_DOCS"));
		Assert.assertEquals("NO", configList.get("INHERIT_DOCS"));
	}
	@Test
	public void separateMemberPagesTest() throws MavenReportException, IOException {
		dr.setSeparateMemberPages(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SEPARATE_MEMBER_PAGES"));
		Assert.assertEquals("YES", configList.get("SEPARATE_MEMBER_PAGES"));
	}
	@Test
	public void tabSizeTest() throws MavenReportException, IOException {
		dr.setTabSize(9);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("TAB_SIZE"));
		Assert.assertEquals(new Integer(9).toString(), configList.get("TAB_SIZE"));
	}
	@Test
	public void aliasesTest() throws MavenReportException, IOException {
		dr.setAliases("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("ALIASES"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("ALIASES"));
	}
	@Test
	public void optimizeOutputForCTest() throws MavenReportException, IOException {
		dr.setOptimizeOutputForC(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("OPTIMIZE_OUTPUT_FOR_C"));
		Assert.assertEquals("YES", configList.get("OPTIMIZE_OUTPUT_FOR_C"));
	}
	@Test
	public void optimizeOutputJavaTest() throws MavenReportException, IOException {
		dr.setOptimizeOutputJava(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("OPTIMIZE_OUTPUT_JAVA"));
		Assert.assertEquals("YES", configList.get("OPTIMIZE_OUTPUT_JAVA"));
	}
	@Test
	public void optimizeForFortranTest() throws MavenReportException, IOException {
		dr.setOptimizeForFortran(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("OPTIMIZE_FOR_FORTRAN"));
		Assert.assertEquals("YES", configList.get("OPTIMIZE_FOR_FORTRAN"));
	}
	@Test
	public void optimizeOutputVhdlTest() throws MavenReportException, IOException {
		dr.setOptimizeOutputVhdl(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("OPTIMIZE_OUTPUT_VHDL"));
		Assert.assertEquals("YES", configList.get("OPTIMIZE_OUTPUT_VHDL"));
	}
	@Test
	public void builtinStlSupportTest() throws MavenReportException, IOException {
		dr.setBuiltinStlSupport(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("BUILTIN_STL_SUPPORT"));
		Assert.assertEquals("NO", configList.get("BUILTIN_STL_SUPPORT"));
	}
	@Test
	public void cppCliSupportTest() throws MavenReportException, IOException {
		dr.setCppCliSupport(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("CPP_CLI_SUPPORT"));
		Assert.assertEquals("YES", configList.get("CPP_CLI_SUPPORT"));
	}
	@Test
	public void sipSupportTest() throws MavenReportException, IOException {
		dr.setSipSupport(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SIP_SUPPORT"));
		Assert.assertEquals("YES", configList.get("SIP_SUPPORT"));
	}
	@Test
	public void idlPropertySupportTest() throws MavenReportException, IOException {
		dr.setIdlPropertySupport(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("IDL_PROPERTY_SUPPORT"));
		Assert.assertEquals("NO", configList.get("IDL_PROPERTY_SUPPORT"));
	}
	@Test
	public void distributeGroupDocTest() throws MavenReportException, IOException {
		dr.setDistributeGroupDoc(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DISTRIBUTE_GROUP_DOC"));
		Assert.assertEquals("YES", configList.get("DISTRIBUTE_GROUP_DOC"));
	}
	@Test
	public void subgroupingTest() throws MavenReportException, IOException {
		dr.setSubgrouping(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SUBGROUPING"));
		Assert.assertEquals("NO", configList.get("SUBGROUPING"));
	}
	@Test
	public void typedefHidesStructTest() throws MavenReportException, IOException {
		dr.setTypedefHidesStruct(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("TYPEDEF_HIDES_STRUCT"));
		Assert.assertEquals("YES", configList.get("TYPEDEF_HIDES_STRUCT"));
	}
	@Test
	public void symbolCacheSizeTest() throws MavenReportException, IOException {
		dr.setSymbolCacheSize(1);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SYMBOL_CACHE_SIZE"));
		Assert.assertEquals(new Integer(1).toString(), configList.get("SYMBOL_CACHE_SIZE"));
	}
	@Test
	public void extractAllTest() throws MavenReportException, IOException {
		dr.setExtractAll(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXTRACT_ALL"));
		Assert.assertEquals("YES", configList.get("EXTRACT_ALL"));
	}
	@Test
	public void extractPrivateTest() throws MavenReportException, IOException {
		dr.setExtractPrivate(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXTRACT_PRIVATE"));
		Assert.assertEquals("YES", configList.get("EXTRACT_PRIVATE"));
	}
	@Test
	public void extractStaticTest() throws MavenReportException, IOException {
		dr.setExtractStatic(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXTRACT_STATIC"));
		Assert.assertEquals("YES", configList.get("EXTRACT_STATIC"));
	}
	@Test
	public void extractLocalClassesTest() throws MavenReportException, IOException {
		dr.setExtractLocalClasses(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXTRACT_LOCAL_CLASSES"));
		Assert.assertEquals("NO", configList.get("EXTRACT_LOCAL_CLASSES"));
	}
	@Test
	public void extractLocalMethodsTest() throws MavenReportException, IOException {
		dr.setExtractLocalMethods(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXTRACT_LOCAL_METHODS"));
		Assert.assertEquals("YES", configList.get("EXTRACT_LOCAL_METHODS"));
	}
	@Test
	public void extractAnonNspacesTest() throws MavenReportException, IOException {
		dr.setExtractAnonNspaces(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXTRACT_ANON_NSPACES"));
		Assert.assertEquals("YES", configList.get("EXTRACT_ANON_NSPACES"));
	}
	@Test
	public void hideUndocMembersTest() throws MavenReportException, IOException {
		dr.setHideUndocMembers(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HIDE_UNDOC_MEMBERS"));
		Assert.assertEquals("YES", configList.get("HIDE_UNDOC_MEMBERS"));
	}
	@Test
	public void hideUndocClassesTest() throws MavenReportException, IOException {
		dr.setHideUndocClasses(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HIDE_UNDOC_CLASSES"));
		Assert.assertEquals("YES", configList.get("HIDE_UNDOC_CLASSES"));
	}
	@Test
	public void hideFriendCompoundsTest() throws MavenReportException, IOException {
		dr.setHideFriendCompounds(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HIDE_FRIEND_COMPOUNDS"));
		Assert.assertEquals("YES", configList.get("HIDE_FRIEND_COMPOUNDS"));
	}
	@Test
	public void hideInBodyDocsTest() throws MavenReportException, IOException {
		dr.setHideInBodyDocs(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HIDE_IN_BODY_DOCS"));
		Assert.assertEquals("YES", configList.get("HIDE_IN_BODY_DOCS"));
	}
	@Test
	public void internalDocsTest() throws MavenReportException, IOException {
		dr.setInternalDocs(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INTERNAL_DOCS"));
		Assert.assertEquals("YES", configList.get("INTERNAL_DOCS"));
	}
	@Test
	public void caseSenseNamesTest() throws MavenReportException, IOException {
		dr.setCaseSenseNames(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("CASE_SENSE_NAMES"));
		Assert.assertEquals("NO", configList.get("CASE_SENSE_NAMES"));
	}
	@Test
	public void hideScopeNamesTest() throws MavenReportException, IOException {
		dr.setHideScopeNames(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HIDE_SCOPE_NAMES"));
		Assert.assertEquals("YES", configList.get("HIDE_SCOPE_NAMES"));
	}
	@Test
	public void showIncludeFilesTest() throws MavenReportException, IOException {
		dr.setShowIncludeFiles(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SHOW_INCLUDE_FILES"));
		Assert.assertEquals("NO", configList.get("SHOW_INCLUDE_FILES"));
	}
	@Test
	public void inlineInfoTest() throws MavenReportException, IOException {
		dr.setInlineInfo(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INLINE_INFO"));
		Assert.assertEquals("NO", configList.get("INLINE_INFO"));
	}
	@Test
	public void sortMemberDocsTest() throws MavenReportException, IOException {
		dr.setSortMemberDocs(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SORT_MEMBER_DOCS"));
		Assert.assertEquals("NO", configList.get("SORT_MEMBER_DOCS"));
	}
	@Test
	public void sortBriefDocsTest() throws MavenReportException, IOException {
		dr.setSortBriefDocs(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SORT_BRIEF_DOCS"));
		Assert.assertEquals("YES", configList.get("SORT_BRIEF_DOCS"));
	}
	@Test
	public void sortGroupNamesTest() throws MavenReportException, IOException {
		dr.setSortGroupNames(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SORT_GROUP_NAMES"));
		Assert.assertEquals("YES", configList.get("SORT_GROUP_NAMES"));
	}
	@Test
	public void sortByScopeNameTest() throws MavenReportException, IOException {
		dr.setSortByScopeName(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SORT_BY_SCOPE_NAME"));
		Assert.assertEquals("YES", configList.get("SORT_BY_SCOPE_NAME"));
	}
	@Test
	public void generateTodolistTest() throws MavenReportException, IOException {
		dr.setGenerateTodolist(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_TODOLIST"));
		Assert.assertEquals("NO", configList.get("GENERATE_TODOLIST"));
	}
	@Test
	public void generateTestlistTest() throws MavenReportException, IOException {
		dr.setGenerateTestlist(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_TESTLIST"));
		Assert.assertEquals("NO", configList.get("GENERATE_TESTLIST"));
	}
	@Test
	public void generateBuglistTest() throws MavenReportException, IOException {
		dr.setGenerateBuglist(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_BUGLIST"));
		Assert.assertEquals("NO", configList.get("GENERATE_BUGLIST"));
	}
	@Test
	public void generateDeprecatedlistTest() throws MavenReportException, IOException {
		dr.setGenerateDeprecatedlist(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_DEPRECATEDLIST"));
		Assert.assertEquals("NO", configList.get("GENERATE_DEPRECATEDLIST"));
	}
	@Test
	public void enabledSectionsTest() throws MavenReportException, IOException {
		dr.setEnabledSections("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("ENABLED_SECTIONS"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("ENABLED_SECTIONS"));
	}
	@Test
	public void maxInitializerLinesTest() throws MavenReportException, IOException {
		dr.setMaxInitializerLines(31);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("MAX_INITIALIZER_LINES"));
		Assert.assertEquals(new Integer(31).toString(), configList.get("MAX_INITIALIZER_LINES"));
	}
	@Test
	public void showUsedFilesTest() throws MavenReportException, IOException {
		dr.setShowUsedFiles(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SHOW_USED_FILES"));
		Assert.assertEquals("NO", configList.get("SHOW_USED_FILES"));
	}
	@Test
	public void showDirectoriesTest() throws MavenReportException, IOException {
		dr.setShowDirectories(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SHOW_DIRECTORIES"));
		Assert.assertEquals("NO", configList.get("SHOW_DIRECTORIES"));
	}
	@Test
	public void showFilesTest() throws MavenReportException, IOException {
		dr.setShowFiles(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SHOW_FILES"));
		Assert.assertEquals("NO", configList.get("SHOW_FILES"));
	}
	@Test
	public void showNamespacesTest() throws MavenReportException, IOException {
		dr.setShowNamespaces(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SHOW_NAMESPACES"));
		Assert.assertEquals("NO", configList.get("SHOW_NAMESPACES"));
	}
	@Test
	public void fileVersionFilterTest() throws MavenReportException, IOException {
		dr.setFileVersionFilter("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("FILE_VERSION_FILTER"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("FILE_VERSION_FILTER"));
	}
	@Test
	public void layoutFileTest() throws MavenReportException, IOException {
		dr.setLayoutFile("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("LAYOUT_FILE"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("LAYOUT_FILE"));
	}
	@Test
	public void quietTest() throws MavenReportException, IOException {
		dr.setQuiet(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("QUIET"));
		Assert.assertEquals("NO", configList.get("QUIET"));
	}
	@Test
	public void warningsTest() throws MavenReportException, IOException {
		dr.setWarnings(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("WARNINGS"));
		Assert.assertEquals("NO", configList.get("WARNINGS"));
	}
	@Test
	public void warnIfUndocumentedTest() throws MavenReportException, IOException {
		dr.setWarnIfUndocumented(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("WARN_IF_UNDOCUMENTED"));
		Assert.assertEquals("NO", configList.get("WARN_IF_UNDOCUMENTED"));
	}
	@Test
	public void warnIfDocErrorTest() throws MavenReportException, IOException {
		dr.setWarnIfDocError(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("WARN_IF_DOC_ERROR"));
		Assert.assertEquals("NO", configList.get("WARN_IF_DOC_ERROR"));
	}
	@Test
	public void warnNoParamdocTest() throws MavenReportException, IOException {
		dr.setWarnNoParamdoc(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("WARN_NO_PARAMDOC"));
		Assert.assertEquals("NO", configList.get("WARN_NO_PARAMDOC"));
	}
	@Test
	public void warnFormatTest() throws MavenReportException, IOException {
		dr.setWarnFormat("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("WARN_FORMAT"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("WARN_FORMAT"));
	}
	@Test
	public void warnLogfileTest() throws MavenReportException, IOException {
		dr.setWarnLogfile("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("WARN_LOGFILE"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("WARN_LOGFILE"));
	}
	@Test
	public void inputTest() throws MavenReportException, IOException {
		dr.setInput("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INPUT"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("INPUT"));
	}
	@Test
	public void inputEncodingTest() throws MavenReportException, IOException {
		dr.setInputEncoding("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INPUT_ENCODING"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("INPUT_ENCODING"));
	}
	@Test
	public void filePatternsTest() throws MavenReportException, IOException {
		dr.setFilePatterns("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("FILE_PATTERNS"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("FILE_PATTERNS"));
	}
	@Test
	public void recursiveTest() throws MavenReportException, IOException {
		dr.setRecursive(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("RECURSIVE"));
		Assert.assertEquals("YES", configList.get("RECURSIVE"));
	}
	@Test
	public void excludeTest() throws MavenReportException, IOException {
		dr.setExclude("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXCLUDE"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("EXCLUDE"));
	}
	@Test
	public void excludeSymlinksTest() throws MavenReportException, IOException {
		dr.setExcludeSymlinks(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXCLUDE_SYMLINKS"));
		Assert.assertEquals("YES", configList.get("EXCLUDE_SYMLINKS"));
	}
	@Test
	public void excludePatternsTest() throws MavenReportException, IOException {
		dr.setExcludePatterns("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXCLUDE_PATTERNS"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("EXCLUDE_PATTERNS"));
	}
	@Test
	public void excludeSymbolsTest() throws MavenReportException, IOException {
		dr.setExcludeSymbols("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXCLUDE_SYMBOLS"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("EXCLUDE_SYMBOLS"));
	}
	@Test
	public void examplePathTest() throws MavenReportException, IOException {
		dr.setExamplePath("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXAMPLE_PATH"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("EXAMPLE_PATH"));
	}
	@Test
	public void examplePatternsTest() throws MavenReportException, IOException {
		dr.setExamplePatterns("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXAMPLE_PATTERNS"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("EXAMPLE_PATTERNS"));
	}
	@Test
	public void exampleRecursiveTest() throws MavenReportException, IOException {
		dr.setExampleRecursive(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXAMPLE_RECURSIVE"));
		Assert.assertEquals("YES", configList.get("EXAMPLE_RECURSIVE"));
	}
	@Test
	public void imagePathTest() throws MavenReportException, IOException {
		dr.setImagePath("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("IMAGE_PATH"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("IMAGE_PATH"));
	}
	@Test
	public void inputFilterTest() throws MavenReportException, IOException {
		dr.setInputFilter("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INPUT_FILTER"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("INPUT_FILTER"));
	}
	@Test
	public void filterPatternsTest() throws MavenReportException, IOException {
		dr.setFilterPatterns("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("FILTER_PATTERNS"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("FILTER_PATTERNS"));
	}
	@Test
	public void filterSourceFilesTest() throws MavenReportException, IOException {
		dr.setFilterSourceFiles(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("FILTER_SOURCE_FILES"));
		Assert.assertEquals("YES", configList.get("FILTER_SOURCE_FILES"));
	}
	@Test
	public void sourceBrowserTest() throws MavenReportException, IOException {
		dr.setSourceBrowser(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SOURCE_BROWSER"));
		Assert.assertEquals("NO", configList.get("SOURCE_BROWSER"));
	}
	@Test
	public void inlineSourcesTest() throws MavenReportException, IOException {
		dr.setInlineSources(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INLINE_SOURCES"));
		Assert.assertEquals("YES", configList.get("INLINE_SOURCES"));
	}
	@Test
	public void stripCodeCommentsTest() throws MavenReportException, IOException {
		dr.setStripCodeComments(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("STRIP_CODE_COMMENTS"));
		Assert.assertEquals("NO", configList.get("STRIP_CODE_COMMENTS"));
	}
	@Test
	public void referencedByRelationTest() throws MavenReportException, IOException {
		dr.setReferencedByRelation(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("REFERENCED_BY_RELATION"));
		Assert.assertEquals("YES", configList.get("REFERENCED_BY_RELATION"));
	}
	@Test
	public void referencesRelationTest() throws MavenReportException, IOException {
		dr.setReferencesRelation(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("REFERENCES_RELATION"));
		Assert.assertEquals("YES", configList.get("REFERENCES_RELATION"));
	}
	@Test
	public void referencesLinkSourceTest() throws MavenReportException, IOException {
		dr.setReferencesLinkSource(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("REFERENCES_LINK_SOURCE"));
		Assert.assertEquals("NO", configList.get("REFERENCES_LINK_SOURCE"));
	}
	@Test
	public void useHtagsTest() throws MavenReportException, IOException {
		dr.setUseHtags(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("USE_HTAGS"));
		Assert.assertEquals("YES", configList.get("USE_HTAGS"));
	}
	@Test
	public void verbatimHeadersTest() throws MavenReportException, IOException {
		dr.setVerbatimHeaders(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("VERBATIM_HEADERS"));
		Assert.assertEquals("NO", configList.get("VERBATIM_HEADERS"));
	}
	@Test
	public void alphabeticalIndexTest() throws MavenReportException, IOException {
		dr.setAlphabeticalIndex(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("ALPHABETICAL_INDEX"));
		Assert.assertEquals("NO", configList.get("ALPHABETICAL_INDEX"));
	}
	@Test
	public void colsInAlphaIndexTest() throws MavenReportException, IOException {
		dr.setColsInAlphaIndex(6);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("COLS_IN_ALPHA_INDEX"));
		Assert.assertEquals(new Integer(6).toString(), configList.get("COLS_IN_ALPHA_INDEX"));
	}
	@Test
	public void ignorePrefixTest() throws MavenReportException, IOException {
		dr.setIgnorePrefix("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("IGNORE_PREFIX"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("IGNORE_PREFIX"));
	}
	@Test
	public void generateHtmlTest() throws MavenReportException, IOException {
		dr.setGenerateHtml(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_HTML"));
		Assert.assertEquals("NO", configList.get("GENERATE_HTML"));
	}
	@Test
	public void htmlOutputTest() throws MavenReportException, IOException {
		dr.setHtmlOutput("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HTML_OUTPUT"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("HTML_OUTPUT"));
	}
	@Test
	public void htmlFileExtensionTest() throws MavenReportException, IOException {
		dr.setHtmlFileExtension("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HTML_FILE_EXTENSION"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("HTML_FILE_EXTENSION"));
	}
	@Test
	public void htmlHeaderTest() throws MavenReportException, IOException {
		dr.setHtmlHeader("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HTML_HEADER"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("HTML_HEADER"));
	}
	@Test
	public void htmlFooterTest() throws MavenReportException, IOException {
		dr.setHtmlFooter("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HTML_FOOTER"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("HTML_FOOTER"));
	}
	@Test
	public void htmlStylesheetTest() throws MavenReportException, IOException {
		dr.setHtmlStylesheet("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HTML_STYLESHEET"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("HTML_STYLESHEET"));
	}
	@Test
	public void htmlAlignMembersTest() throws MavenReportException, IOException {
		dr.setHtmlAlignMembers(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HTML_ALIGN_MEMBERS"));
		Assert.assertEquals("NO", configList.get("HTML_ALIGN_MEMBERS"));
	}
	@Test
	public void htmlDynamicSectionsTest() throws MavenReportException, IOException {
		dr.setHtmlDynamicSections(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HTML_DYNAMIC_SECTIONS"));
		Assert.assertEquals("NO", configList.get("HTML_DYNAMIC_SECTIONS"));
	}
	@Test
	public void generateDocsetTest() throws MavenReportException, IOException {
		dr.setGenerateDocset(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_DOCSET"));
		Assert.assertEquals("YES", configList.get("GENERATE_DOCSET"));
	}
	@Test
	public void docsetFeednameTest() throws MavenReportException, IOException {
		dr.setDocsetFeedname("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOCSET_FEEDNAME"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("DOCSET_FEEDNAME"));
	}
	@Test
	public void docsetBundleIdTest() throws MavenReportException, IOException {
		dr.setDocsetBundleId("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOCSET_BUNDLE_ID"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("DOCSET_BUNDLE_ID"));
	}
	@Test
	public void generateHtmlhelpTest() throws MavenReportException, IOException {
		dr.setGenerateHtmlhelp(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_HTMLHELP"));
		Assert.assertEquals("YES", configList.get("GENERATE_HTMLHELP"));
	}
	@Test
	public void chmFileTest() throws MavenReportException, IOException {
		dr.setChmFile("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("CHM_FILE"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("CHM_FILE"));
	}
	@Test
	public void hhcLocationTest() throws MavenReportException, IOException {
		dr.setHhcLocation("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HHC_LOCATION"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("HHC_LOCATION"));
	}
	@Test
	public void generateChiTest() throws MavenReportException, IOException {
		dr.setGenerateChi(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_CHI"));
		Assert.assertEquals("YES", configList.get("GENERATE_CHI"));
	}
	@Test
	public void chmIndexEncodingTest() throws MavenReportException, IOException {
		dr.setChmIndexEncoding("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("CHM_INDEX_ENCODING"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("CHM_INDEX_ENCODING"));
	}
	@Test
	public void binaryTocTest() throws MavenReportException, IOException {
		dr.setBinaryToc(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("BINARY_TOC"));
		Assert.assertEquals("YES", configList.get("BINARY_TOC"));
	}
	@Test
	public void tocExpandTest() throws MavenReportException, IOException {
		dr.setTocExpand(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("TOC_EXPAND"));
		Assert.assertEquals("YES", configList.get("TOC_EXPAND"));
	}
	@Test
	public void generateQhpTest() throws MavenReportException, IOException {
		dr.setGenerateQhp(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_QHP"));
		Assert.assertEquals("YES", configList.get("GENERATE_QHP"));
	}
	@Test
	public void qchFileTest() throws MavenReportException, IOException {
		dr.setQchFile("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("QCH_FILE"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("QCH_FILE"));
	}
	@Test
	public void qhpNamespaceTest() throws MavenReportException, IOException {
		dr.setQhpNamespace("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("QHP_NAMESPACE"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("QHP_NAMESPACE"));
	}
	@Test
	public void qhpVirtualFolderTest() throws MavenReportException, IOException {
		dr.setQhpVirtualFolder("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("QHP_VIRTUAL_FOLDER"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("QHP_VIRTUAL_FOLDER"));
	}
	@Test
	public void qhgLocationTest() throws MavenReportException, IOException {
		dr.setQhgLocation("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("QHG_LOCATION"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("QHG_LOCATION"));
	}
	@Test
	public void disableIndexTest() throws MavenReportException, IOException {
		dr.setDisableIndex(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DISABLE_INDEX"));
		Assert.assertEquals("YES", configList.get("DISABLE_INDEX"));
	}
	@Test
	public void enumValuesPerLineTest() throws MavenReportException, IOException {
		dr.setEnumValuesPerLine(5);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("ENUM_VALUES_PER_LINE"));
		Assert.assertEquals(new Integer(5).toString(), configList.get("ENUM_VALUES_PER_LINE"));
	}
	@Test
	public void generateTreeviewTest() throws MavenReportException, IOException {
		dr.setGenerateTreeview("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_TREEVIEW"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("GENERATE_TREEVIEW"));
	}
	@Test
	public void treeviewWidthTest() throws MavenReportException, IOException {
		dr.setTreeviewWidth(251);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("TREEVIEW_WIDTH"));
		Assert.assertEquals(new Integer(251).toString(), configList.get("TREEVIEW_WIDTH"));
	}
	@Test
	public void formulaFontsizeTest() throws MavenReportException, IOException {
		dr.setFormulaFontsize(11);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("FORMULA_FONTSIZE"));
		Assert.assertEquals(new Integer(11).toString(), configList.get("FORMULA_FONTSIZE"));
	}
	@Test
	public void generateLatexTest() throws MavenReportException, IOException {
		dr.setGenerateLatex(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_LATEX"));
		Assert.assertEquals("YES", configList.get("GENERATE_LATEX"));
	}
	@Test
	public void latexOutputTest() throws MavenReportException, IOException {
		dr.setLatexOutput("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("LATEX_OUTPUT"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("LATEX_OUTPUT"));
	}
	@Test
	public void latexCmdNameTest() throws MavenReportException, IOException {
		dr.setLatexCmdName("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("LATEX_CMD_NAME"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("LATEX_CMD_NAME"));
	}
	@Test
	public void makeindexCmdNameTest() throws MavenReportException, IOException {
		dr.setMakeindexCmdName("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("MAKEINDEX_CMD_NAME"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("MAKEINDEX_CMD_NAME"));
	}
	@Test
	public void compactLatexTest() throws MavenReportException, IOException {
		dr.setCompactLatex(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("COMPACT_LATEX"));
		Assert.assertEquals("YES", configList.get("COMPACT_LATEX"));
	}
	@Test
	public void paperTypeTest() throws MavenReportException, IOException {
		dr.setPaperType("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("PAPER_TYPE"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("PAPER_TYPE"));
	}
	@Test
	public void extraPackagesTest() throws MavenReportException, IOException {
		dr.setExtraPackages("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXTRA_PACKAGES"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("EXTRA_PACKAGES"));
	}
	@Test
	public void latexHeaderTest() throws MavenReportException, IOException {
		dr.setLatexHeader("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("LATEX_HEADER"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("LATEX_HEADER"));
	}
	@Test
	public void pdfHyperlinksTest() throws MavenReportException, IOException {
		dr.setPdfHyperlinks(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("PDF_HYPERLINKS"));
		Assert.assertEquals("NO", configList.get("PDF_HYPERLINKS"));
	}
	@Test
	public void usePdflatexTest() throws MavenReportException, IOException {
		dr.setUsePdflatex(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("USE_PDFLATEX"));
		Assert.assertEquals("NO", configList.get("USE_PDFLATEX"));
	}
	@Test
	public void latexBatchmodeTest() throws MavenReportException, IOException {
		dr.setLatexBatchmode(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("LATEX_BATCHMODE"));
		Assert.assertEquals("YES", configList.get("LATEX_BATCHMODE"));
	}
	@Test
	public void latexHideIndicesTest() throws MavenReportException, IOException {
		dr.setLatexHideIndices(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("LATEX_HIDE_INDICES"));
		Assert.assertEquals("YES", configList.get("LATEX_HIDE_INDICES"));
	}
	@Test
	public void generateRtfTest() throws MavenReportException, IOException {
		dr.setGenerateRtf(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_RTF"));
		Assert.assertEquals("YES", configList.get("GENERATE_RTF"));
	}
	@Test
	public void rtfOutputTest() throws MavenReportException, IOException {
		dr.setRtfOutput("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("RTF_OUTPUT"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("RTF_OUTPUT"));
	}
	@Test
	public void compactRtfTest() throws MavenReportException, IOException {
		dr.setCompactRtf(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("COMPACT_RTF"));
		Assert.assertEquals("YES", configList.get("COMPACT_RTF"));
	}
	@Test
	public void rtfHyperlinksTest() throws MavenReportException, IOException {
		dr.setRtfHyperlinks(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("RTF_HYPERLINKS"));
		Assert.assertEquals("YES", configList.get("RTF_HYPERLINKS"));
	}
	@Test
	public void rtfStylesheetFileTest() throws MavenReportException, IOException {
		dr.setRtfStylesheetFile("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("RTF_STYLESHEET_FILE"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("RTF_STYLESHEET_FILE"));
	}
	@Test
	public void rtfExtensionsFileTest() throws MavenReportException, IOException {
		dr.setRtfExtensionsFile("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("RTF_EXTENSIONS_FILE"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("RTF_EXTENSIONS_FILE"));
	}
	@Test
	public void generateManTest() throws MavenReportException, IOException {
		dr.setGenerateMan(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_MAN"));
		Assert.assertEquals("YES", configList.get("GENERATE_MAN"));
	}
	@Test
	public void manOutputTest() throws MavenReportException, IOException {
		dr.setManOutput("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("MAN_OUTPUT"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("MAN_OUTPUT"));
	}
	@Test
	public void manExtensionTest() throws MavenReportException, IOException {
		dr.setManExtension("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("MAN_EXTENSION"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("MAN_EXTENSION"));
	}
	@Test
	public void manLinksTest() throws MavenReportException, IOException {
		dr.setManLinks(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("MAN_LINKS"));
		Assert.assertEquals("YES", configList.get("MAN_LINKS"));
	}
	@Test
	public void generateXmlTest() throws MavenReportException, IOException {
		dr.setGenerateXml(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_XML"));
		Assert.assertEquals("YES", configList.get("GENERATE_XML"));
	}
	@Test
	public void xmlOutputTest() throws MavenReportException, IOException {
		dr.setXmlOutput("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("XML_OUTPUT"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("XML_OUTPUT"));
	}
	@Test
	public void xmlSchemaTest() throws MavenReportException, IOException {
		dr.setXmlSchema("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("XML_SCHEMA"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("XML_SCHEMA"));
	}
	@Test
	public void xmlDtdTest() throws MavenReportException, IOException {
		dr.setXmlDtd("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("XML_DTD"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("XML_DTD"));
	}
	@Test
	public void xmlProgramlistingTest() throws MavenReportException, IOException {
		dr.setXmlProgramlisting(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("XML_PROGRAMLISTING"));
		Assert.assertEquals("NO", configList.get("XML_PROGRAMLISTING"));
	}
	@Test
	public void generateAutogenDefTest() throws MavenReportException, IOException {
		dr.setGenerateAutogenDef(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_AUTOGEN_DEF"));
		Assert.assertEquals("YES", configList.get("GENERATE_AUTOGEN_DEF"));
	}
	@Test
	public void generatePerlmodTest() throws MavenReportException, IOException {
		dr.setGeneratePerlmod(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_PERLMOD"));
		Assert.assertEquals("YES", configList.get("GENERATE_PERLMOD"));
	}
	@Test
	public void perlmodLatexTest() throws MavenReportException, IOException {
		dr.setPerlmodLatex(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("PERLMOD_LATEX"));
		Assert.assertEquals("YES", configList.get("PERLMOD_LATEX"));
	}
	@Test
	public void perlmodPrettyTest() throws MavenReportException, IOException {
		dr.setPerlmodPretty(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("PERLMOD_PRETTY"));
		Assert.assertEquals("NO", configList.get("PERLMOD_PRETTY"));
	}
	@Test
	public void perlmodMakevarPrefixTest() throws MavenReportException, IOException {
		dr.setPerlmodMakevarPrefix("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("PERLMOD_MAKEVAR_PREFIX"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("PERLMOD_MAKEVAR_PREFIX"));
	}
	@Test
	public void enablePreprocessingTest() throws MavenReportException, IOException {
		dr.setEnablePreprocessing(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("ENABLE_PREPROCESSING"));
		Assert.assertEquals("NO", configList.get("ENABLE_PREPROCESSING"));
	}
	@Test
	public void macroExpansionTest() throws MavenReportException, IOException {
		dr.setMacroExpansion(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("MACRO_EXPANSION"));
		Assert.assertEquals("YES", configList.get("MACRO_EXPANSION"));
	}
	@Test
	public void expandOnlyPredefTest() throws MavenReportException, IOException {
		dr.setExpandOnlyPredef(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXPAND_ONLY_PREDEF"));
		Assert.assertEquals("YES", configList.get("EXPAND_ONLY_PREDEF"));
	}
	@Test
	public void searchIncludesTest() throws MavenReportException, IOException {
		dr.setSearchIncludes(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SEARCH_INCLUDES"));
		Assert.assertEquals("NO", configList.get("SEARCH_INCLUDES"));
	}
	@Test
	public void includePathTest() throws MavenReportException, IOException {
		dr.setIncludePath("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INCLUDE_PATH"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("INCLUDE_PATH"));
	}
	@Test
	public void includeFilePatternsTest() throws MavenReportException, IOException {
		dr.setIncludeFilePatterns("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INCLUDE_FILE_PATTERNS"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("INCLUDE_FILE_PATTERNS"));
	}
	@Test
	public void predefinedTest() throws MavenReportException, IOException {
		dr.setPredefined("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("PREDEFINED"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("PREDEFINED"));
	}
	@Test
	public void expandAsDefinedTest() throws MavenReportException, IOException {
		dr.setExpandAsDefined("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXPAND_AS_DEFINED"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("EXPAND_AS_DEFINED"));
	}
	@Test
	public void skipFunctionMacrosTest() throws MavenReportException, IOException {
		dr.setSkipFunctionMacros(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SKIP_FUNCTION_MACROS"));
		Assert.assertEquals("NO", configList.get("SKIP_FUNCTION_MACROS"));
	}
	@Test
	public void tagfilesTest() throws MavenReportException, IOException {
		dr.setTagfiles("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("TAGFILES"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("TAGFILES"));
	}
	@Test
	public void generateTagfileTest() throws MavenReportException, IOException {
		dr.setGenerateTagfile("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_TAGFILE"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("GENERATE_TAGFILE"));
	}
	@Test
	public void allexternalsTest() throws MavenReportException, IOException {
		dr.setAllexternals(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("ALLEXTERNALS"));
		Assert.assertEquals("YES", configList.get("ALLEXTERNALS"));
	}
	@Test
	public void externalGroupsTest() throws MavenReportException, IOException {
		dr.setExternalGroups(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("EXTERNAL_GROUPS"));
		Assert.assertEquals("NO", configList.get("EXTERNAL_GROUPS"));
	}
	@Test
	public void perlPathTest() throws MavenReportException, IOException {
		dr.setPerlPath("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("PERL_PATH"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("PERL_PATH"));
	}
	@Test
	public void classDiagramsTest() throws MavenReportException, IOException {
		dr.setClassDiagrams(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("CLASS_DIAGRAMS"));
		Assert.assertEquals("NO", configList.get("CLASS_DIAGRAMS"));
	}
	@Test
	public void mscgenPathTest() throws MavenReportException, IOException {
		dr.setMscgenPath("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("MSCGEN_PATH"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("MSCGEN_PATH"));
	}
	@Test
	public void hideUndocRelationsTest() throws MavenReportException, IOException {
		dr.setHideUndocRelations(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HIDE_UNDOC_RELATIONS"));
		Assert.assertEquals("NO", configList.get("HIDE_UNDOC_RELATIONS"));
	}
	@Test
	public void haveDotTest() throws MavenReportException, IOException {
		dr.setHaveDot(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("HAVE_DOT"));
		Assert.assertEquals("NO", configList.get("HAVE_DOT"));
	}
	@Test
	public void dotFontnameTest() throws MavenReportException, IOException {
		dr.setDotFontname("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOT_FONTNAME"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("DOT_FONTNAME"));
	}
	@Test
	public void dotFontsizeTest() throws MavenReportException, IOException {
		dr.setDotFontsize(11);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOT_FONTSIZE"));
		Assert.assertEquals(new Integer(11).toString(), configList.get("DOT_FONTSIZE"));
	}
	@Test
	public void dotFontpathTest() throws MavenReportException, IOException {
		dr.setDotFontpath("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOT_FONTPATH"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("DOT_FONTPATH"));
	}
	@Test
	public void classGraphTest() throws MavenReportException, IOException {
		dr.setClassGraph(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("CLASS_GRAPH"));
		Assert.assertEquals("NO", configList.get("CLASS_GRAPH"));
	}
	@Test
	public void collaborationGraphTest() throws MavenReportException, IOException {
		dr.setCollaborationGraph(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("COLLABORATION_GRAPH"));
		Assert.assertEquals("NO", configList.get("COLLABORATION_GRAPH"));
	}
	@Test
	public void groupGraphsTest() throws MavenReportException, IOException {
		dr.setGroupGraphs(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GROUP_GRAPHS"));
		Assert.assertEquals("NO", configList.get("GROUP_GRAPHS"));
	}
	@Test
	public void umlLookTest() throws MavenReportException, IOException {
		dr.setUmlLook(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("UML_LOOK"));
		Assert.assertEquals("YES", configList.get("UML_LOOK"));
	}
	@Test
	public void templateRelationsTest() throws MavenReportException, IOException {
		dr.setTemplateRelations(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("TEMPLATE_RELATIONS"));
		Assert.assertEquals("YES", configList.get("TEMPLATE_RELATIONS"));
	}
	@Test
	public void includeGraphTest() throws MavenReportException, IOException {
		dr.setIncludeGraph(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INCLUDE_GRAPH"));
		Assert.assertEquals("NO", configList.get("INCLUDE_GRAPH"));
	}
	@Test
	public void includedByGraphTest() throws MavenReportException, IOException {
		dr.setIncludedByGraph(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("INCLUDED_BY_GRAPH"));
		Assert.assertEquals("NO", configList.get("INCLUDED_BY_GRAPH"));
	}
	@Test
	public void callGraphTest() throws MavenReportException, IOException {
		dr.setCallGraph(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("CALL_GRAPH"));
		Assert.assertEquals("YES", configList.get("CALL_GRAPH"));
	}
	@Test
	public void callerGraphTest() throws MavenReportException, IOException {
		dr.setCallerGraph(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("CALLER_GRAPH"));
		Assert.assertEquals("YES", configList.get("CALLER_GRAPH"));
	}
	@Test
	public void graphicalHierarchyTest() throws MavenReportException, IOException {
		dr.setGraphicalHierarchy(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GRAPHICAL_HIERARCHY"));
		Assert.assertEquals("NO", configList.get("GRAPHICAL_HIERARCHY"));
	}
	@Test
	public void directoryGraphTest() throws MavenReportException, IOException {
		dr.setDirectoryGraph(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DIRECTORY_GRAPH"));
		Assert.assertEquals("NO", configList.get("DIRECTORY_GRAPH"));
	}
	@Test
	public void dotImageFormatTest() throws MavenReportException, IOException {
		dr.setDotImageFormat("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOT_IMAGE_FORMAT"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("DOT_IMAGE_FORMAT"));
	}
	@Test
	public void dotPathTest() throws MavenReportException, IOException {
		dr.setDotPath("ThisIsNonNullValue");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOT_PATH"));
		Assert.assertEquals("ThisIsNonNullValue", configList.get("DOT_PATH"));
	}
	@Test
	public void dotfileDirsTest() throws MavenReportException, IOException {
		dr.setDotfileDirs("ThisIsMoreThanEmpty");
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOTFILE_DIRS"));
		Assert.assertEquals("ThisIsMoreThanEmpty", configList.get("DOTFILE_DIRS"));
	}
	@Test
	public void dotGraphMaxNodesTest() throws MavenReportException, IOException {
		dr.setDotGraphMaxNodes(51);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOT_GRAPH_MAX_NODES"));
		Assert.assertEquals(new Integer(51).toString(), configList.get("DOT_GRAPH_MAX_NODES"));
	}
	@Test
	public void maxDotGraphDepthTest() throws MavenReportException, IOException {
		dr.setMaxDotGraphDepth(1);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("MAX_DOT_GRAPH_DEPTH"));
		Assert.assertEquals(new Integer(1).toString(), configList.get("MAX_DOT_GRAPH_DEPTH"));
	}
	@Test
	public void dotTransparentTest() throws MavenReportException, IOException {
		dr.setDotTransparent(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOT_TRANSPARENT"));
		Assert.assertEquals("NO", configList.get("DOT_TRANSPARENT"));
	}
	@Test
	public void dotMultiTargetsTest() throws MavenReportException, IOException {
		dr.setDotMultiTargets(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOT_MULTI_TARGETS"));
		Assert.assertEquals("NO", configList.get("DOT_MULTI_TARGETS"));
	}
	@Test
	public void generateLegendTest() throws MavenReportException, IOException {
		dr.setGenerateLegend(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("GENERATE_LEGEND"));
		Assert.assertEquals("NO", configList.get("GENERATE_LEGEND"));
	}
	@Test
	public void dotCleanupTest() throws MavenReportException, IOException {
		dr.setDotCleanup(false);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("DOT_CLEANUP"));
		Assert.assertEquals("NO", configList.get("DOT_CLEANUP"));
	}
	@Test
	public void searchengineTest() throws MavenReportException, IOException {
		dr.setSearchengine(true);
		dr.buildConfigurationFile(resultConfigFile);
		Assert.assertEquals(true, resultConfigFile.exists());
		HashMap<String, String> configList = readConfigFile(resultConfigFile);
		Assert.assertEquals(true, configList.containsKey("SEARCHENGINE"));
		Assert.assertEquals("YES", configList.get("SEARCHENGINE"));
	}
	

}
