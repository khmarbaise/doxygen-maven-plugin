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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDoxygenBase extends TestBase {

	protected HashMap<String, String> readConfigFile(File configurationFile) throws IOException {
		HashMap<String, String> result = new HashMap<String, String>();
		Pattern pattern = Pattern.compile("^(\\w+)\\s*\\=\\s+(.*)$");
		BufferedReader in = new BufferedReader(
				new FileReader(configurationFile));
		String str;
		while ((str = in.readLine()) != null) {
			if (str.trim().length() == 0) {
				// Ignore empty lines
				continue;
			}
			if (str.matches("^#")) {
				// Comment lines
				continue;
			}
		
			Matcher m = pattern.matcher(str);
			if (m.matches()) {
				String name = m.group(1).trim();
				String value = m.group(2).trim();
				result.put(name, value);
			}
		}
		in.close();
		return result;
	}

}
